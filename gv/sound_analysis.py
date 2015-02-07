from scipy.io import wavfile
import math
import matplotlib.pyplot as plot
import numpy.fft as fft
import numpy

sample_freq, sound = wavfile.read('./audio/test3.wav')
sound = sound/ (2.**15)
print(sound.shape)

s1 = sound
n = len(s1)
p = fft.fft(s1)
nu = math.ceil((n + 1)/2.0)
p = p[0:nu]
p = abs(p)
p = p / float(n)
p = p**2

if n % 2 > 0:
	p[1:len(p)] = p[1:len(p)] * 2
else:
	p[1:len(p) - 1] = p[1:len(p) - 1] * 2

fA = numpy.arange(0, nu, 1.0) * (sample_freq/n);
plot.plot(fA/1000, 10*math.log10(p), color='k')
xlabel('Frequence (kHZ)')
ylabel('Power (dB)')
