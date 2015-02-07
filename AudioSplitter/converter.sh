#!/bin/bash
rm test.wav > /dev/null
python ../gv/collectAudio.py
java -Xmx2024m -jar LIUM_SpkDiarization-8.4.1.jar --saveAllStep "--fInputMask=test.wav" --sOutputMask=/tmp/output.seg --doCEClustering output > /dev/null
rm -rf "output" > /dev/null
mkdir "output"
java AudioSplitter /tmp/output.seg "test.wav" "output" > /dev/null
rm output.txt
touch output.txt
python ../gv/test.py >/dev/null
