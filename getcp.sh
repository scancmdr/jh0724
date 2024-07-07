#!/bin/bash
TMP=/tmp/x-classpath.txt
RC=classpath.rc
mvn dependency:build-classpath -Dmdep.outputFile=$TMP
rm -rf $RC
START="export CLASSPATH=target/classes:"
printf "%s%s\necho " "$START" "$(cat $TMP)" >$RC
rm -rf $TMP
echo "classpath written to resource file"
echo "bring into your current shell with:"
echo "  . $RC"


