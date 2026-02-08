# 프로젝트 빌드 스크립트

Write-Host "================================" -ForegroundColor Cyan
Write-Host "Building Spring Boot + Vue.js" -ForegroundColor Green
Write-Host "================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "Running Maven clean install..." -ForegroundColor Yellow
mvn clean install -DskipTests

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "================================" -ForegroundColor Cyan
    Write-Host "Build Successful!" -ForegroundColor Green
    Write-Host "================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "You can now run the application using:" -ForegroundColor Yellow
    Write-Host "  .\start.ps1" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Or run it manually:" -ForegroundColor Yellow
    Write-Host "  cd backend" -ForegroundColor Cyan
    Write-Host "  mvn spring-boot:run" -ForegroundColor Cyan
} else {
    Write-Host ""
    Write-Host "Build failed. Please check the errors above." -ForegroundColor Red
}

Write-Host ""
Read-Host "Press Enter to exit"

