@echo off
echo [INFO] Please make sure CXF_HOME.
echo [INFO] Please make sure JAVA_HOME.
echo [INFO] Please make sure Local WebService Started.

if exist "%CXF_HOME%/bin/" goto begin
echo [ERROR] Can not find CXF_HOME
goto end

:begin
call "%CXF_HOME%/bin/wsdl2java.bat" -p org.workin.test.ws.soap.service.image.small -d E:/works/Workin/workin-test/ -client -b build-client-binding.xml -exsh true http://localhost:8080/workin/soap/image/smallImageService?wsdl

echo [INFO] All Codes Done!
:end
pause
