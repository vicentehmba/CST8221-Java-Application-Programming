@echo off
cd %~dp0
javac -cp .;swing.jar CSModel.java CA.java
java -cp .;swing.jar cs.CSModel
