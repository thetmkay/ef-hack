import speech_recognition as sr
import re
import glob
r = sr.Recognizer()

def translateClip(filename):
	with sr.WavFile(filename) as source: # use "test.wav" as the audio source
		audio = r.record(source) # extract audio data from the file

		try:
			list = r.recognize(audio,True) # generate a list of possible transcriptions
			best_pred = list[0]
			for prediction in list:
				# print(" " + prediction["text"] + " (" + str(prediction["confidence"]*100) + "%)")
				if best_pred["confidence"] < prediction["confidence"]:
					best_pred = prediction
			return best_pred["text"]	
		except LookupError: # speech is unintelligible
			print("Could not understand audio")
			return "==================="

files = glob.glob('../AudioSplitter/output/*.wav')
with open("output.txt", "a") as myfile:
	for file in files:
		reg = re.compile("^.*S(\d+)-.wav")
		person = reg.match(file).group(1)
		translation = translateClip(file)
		
		print("Person " + person + ": " + translation)	
		myfile.write("Person " + person + ": " + translation + "\n")
