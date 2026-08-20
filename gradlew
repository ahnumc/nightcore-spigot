#!/bin/sh

set -e

APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd -P)
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ]; then
    echo "Missing $WRAPPER_JAR" >&2
    exit 1
fi

exec java ${JAVA_OPTS:-} ${GRADLE_OPTS:-} \
    -classpath "$WRAPPER_JAR" \
    org.gradle.wrapper.GradleWrapperMain "$@"
