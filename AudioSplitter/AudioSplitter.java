import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class AudioSplitter {

	static double[] unusedBuffer = new double[10000000]; 
	
	static float sampleRate = 16000; // Assumed for all our files.
	
	public static void createWavFileSubset(String inWavFilePath, String outPath, float startTime, float length) throws Exception {
		WavFile inWavFile = WavFile.openWavFile(new File(inWavFilePath));

		File outFile = new File(outPath);
		int startFrames = (int)(Math.floor(startTime * sampleRate));
		int lengthFrames = (int)(length * sampleRate);
		System.out.println("Segment: " + outPath + " " + startFrames + " " + lengthFrames + " " + inWavFile.getNumFrames());
		
		double[] buffer = new double[lengthFrames + 1000];
		inWavFile.readFrames(unusedBuffer, startFrames);
		int framesRead = inWavFile.readFrames(buffer, lengthFrames);
		if(framesRead != lengthFrames) {
			System.out.println("warning didn't read enough frames...");
		}
		
		WavFile outWavFile = WavFile.newWavFile(outFile, 1, lengthFrames, 16, (int)sampleRate);
		outWavFile.writeFrames(buffer, framesRead);
		outWavFile.close();
	}
	
	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("Args: Seg-file-path wav-file-path");
			return;
		}
		
		String segFilePath = args[0];
		String wavFilePath = args[1];		
		
		try {			
			int part = 0;
			
			Scanner scanner = new Scanner(new File(segFilePath));
		    while (scanner.hasNextLine()) {
		    	String line = scanner.nextLine();
		    	if(line.startsWith(";"))
		    		continue;
		    	
		    	String lineValues[] = line.split(" ");
		    	String speaker = lineValues[7];
		    	String startTimeString = lineValues[2];
		    	String lengthString = lineValues[3];
		    	
		    	int startTime100 = Integer.parseInt(startTimeString);
		    	int length100 = Integer.parseInt(lengthString);
		    	
		    	float startTime = (float)startTime100 / 100.0f;
		    	float length = (float)length100 / 100.0f;
		    	
		    	createWavFileSubset(wavFilePath, "" + part + "-" + speaker + "-" + startTimeString + "-" + lengthString + ".wav", startTime, length);
		    	
		    	part++;
		    }
		} catch(Exception e) { // Hackathon only :-)
			e.printStackTrace();
		}
	}
}
