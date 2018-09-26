@echo off
javac -classpath ".\melissadata\personatorsearch\org.apache.sling.commons.json-2.0.20.jar;" .\melissadata\personatorsearch\*.java .\melissadata\personatorsearch\view\*.java ./melissadata\personatorsearch\model\*.java
java -classpath ".\melissadata\personatorsearch\org.apache.sling.commons.json-2.0.20.jar;"; melissadata.personatorsearch.Main melissadata.personatorsearch.view.PersonatorSearchController melissadata.personatorsearch.view.PersonatorSearchTransactionController melissadata.personatorsearch.view.RootLayoutController melissadata.personatorsearch.model.PersonatorSearchOption
del .\melissadata\personatorsearch\*.class 
del .\melissadata\personatorsearch\view\*.class 
del .\melissadata\personatorsearch\model\*.class