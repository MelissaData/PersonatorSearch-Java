@echo off
cd target
java -classpath "lib\gson-2.8.9.jar;" com.melissadata.personatorworld.Main com.melissadata.personatorworld.view.PersonatorWorldController com.melissadata.personatorworld.view.RootLayoutController com.melissadata.personatorworld.model.PersonatorWorldOptions com.melissadata.personatorworld.model.PersonatorWorldTransaction
cd ..