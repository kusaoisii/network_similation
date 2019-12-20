#!/bin/bash

if [ $# -ne 2 ]; then
  echo "Usage: ./UDPServer.sh \" broadcast address \" \" number of clients\" "
  exit 1
fi

COUNT=0

while true
do
  (( COUNT++ ))
  javac UDPServer.java
  wc_result=`java UDPServer $1 $2 | wc -l`

  if [ ${wc_result} = $2 ]; then
    echo "The number of trials:" $COUNT
    exit 1
  fi
done

exit 0
