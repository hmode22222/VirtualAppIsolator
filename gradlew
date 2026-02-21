#!/bin/sh
DIR="$(cd "$(dirname "$0")" && pwd)"
if [ -f "$DIR/gradle/wrapper/gradle-wrapper.jar" ]; then
  java -jar "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
else
  gradle "$@"
fi
