@echo off
cd /D %~dp0
echo Mengaktifkan MySQL
echo Tunggu Sebentar  ...
echo Mengeksekusi file C:\xampp\mysql\bin\my.ini 

C:\xampp\mysql\bin\mysqld --defaults-file=C:\xampp\mysql\bin\my.ini --standalone

if errorlevel 1 goto error
goto finish

:error
echo.
echo MySQL gagal diaktifkan
pause

:finish
