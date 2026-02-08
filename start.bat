@echo off
echo ================================
echo Spring Boot + Vue.js + n8n
echo ================================
echo.
echo Starting Spring Boot application...
echo.
cd backend
call mvn spring-boot:run
pause

