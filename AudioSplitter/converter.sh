#!/bin/bash
java -Xmx2024m -jar LIUM_SpkDiarization-8.4.1.jar --saveAllStep --fInputMask=$1 --sOutputMask=output.seg --doCEClustering output
java AudioSplitter output.seg $1

