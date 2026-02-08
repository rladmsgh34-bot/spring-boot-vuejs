# n8n 설치 및 실행 스크립트

Write-Host "================================" -ForegroundColor Cyan
Write-Host "n8n Installation & Setup" -ForegroundColor Green
Write-Host "================================" -ForegroundColor Cyan
Write-Host ""

# n8n이 설치되어 있는지 확인
$n8nInstalled = Get-Command n8n -ErrorAction SilentlyContinue

if (-not $n8nInstalled) {
    Write-Host "n8n is not installed. Installing n8n..." -ForegroundColor Yellow
    npm install -g n8n
    Write-Host "n8n installed successfully!" -ForegroundColor Green
    Write-Host ""
} else {
    Write-Host "n8n is already installed." -ForegroundColor Green
    Write-Host ""
}

Write-Host "Starting n8n..." -ForegroundColor Yellow
Write-Host "n8n will be available at: http://localhost:5678" -ForegroundColor Green
Write-Host ""
Write-Host "Press Ctrl+C to stop n8n" -ForegroundColor Yellow
Write-Host ""

# n8n 실행
n8n start

