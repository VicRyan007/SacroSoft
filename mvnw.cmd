@echo off
set MAVEN_HOME=%~dp0.tools\apache-maven-3.9.16
set PATH=%MAVEN_HOME%\bin;%PATH%
call "%MAVEN_HOME%\bin\mvn.cmd" %*
