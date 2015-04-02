#!/bin/bash 
mvn install:install-file -Dfile=classes.jar \
  -DpomFile=support-v4-21.0.3.pom -Dpackaging=jar

mvn install:install-file -Dfile=support-annotations-21.0.3.jar \
  -DgroupId=com.android.support -DartifactId=support-annotations \
  -Dversion=21.0.3 -Dpackaging=jar
