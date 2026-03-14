@echo off
@setlocal enableextensions
@cd /d "%~dp0"

echo Running Application
java -jar petreminder-app/target/petreminder-app-1.0-SNAPSHOT.jar --gui

echo Operation Completed!
pause