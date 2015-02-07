#!/bin/bash
java -Xmx2024m -jar LIUM_SpkDiarization-8.4.1.jar --saveAllStep "--fInputMask=$1" --sOutputMask=/tmp/output.seg --doCEClustering output
mkdir "$2"
java AudioSplitter /tmp/output.seg "$1" "$2"

