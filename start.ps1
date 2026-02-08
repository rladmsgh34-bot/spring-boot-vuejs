# Spring Boot + Vue.js + n8n 애플리케이션 실행 스크립트

Write-Host "================================" -ForegroundColor Cyan
Write-Host "Spring Boot + Vue.js + n8n" -ForegroundColor Green
Write-Host "================================" -ForegroundColor Cyan
Write-Host ""

# 백엔드 디렉토리로 이동
Set-Location -Path "$PSScriptRoot\backend"

Write-Host "Starting Spring Boot application..." -ForegroundColor Yellow
Write-Host "Application will be available at: http://localhost:8088" -ForegroundColor Green
Write-Host "n8n Integration UI: http://localhost:8088/n8n" -ForegroundColor Green
Write-Host ""
Write-Host "Press Ctrl+C to stop the application" -ForegroundColor Yellow
Write-Host ""

# Maven으로 Spring Boot 실행
mvn spring-boot:run

