# Spring Boot 애플리케이션 실행 스크립트 (포트 정리 포함)

Write-Host ""
Write-Host "=====================================================================" -ForegroundColor Cyan
Write-Host "  🚀 Spring Boot + Vue.js + n8n 애플리케이션" -ForegroundColor Green
Write-Host "=====================================================================" -ForegroundColor Cyan
Write-Host ""

# 1. 포트 8088 정리
Write-Host "🔍 1단계: 포트 8088 확인 및 정리..." -ForegroundColor Yellow
Write-Host ""

$connections = Get-NetTCPConnection -LocalPort 8088 -ErrorAction SilentlyContinue

if ($connections) {
    Write-Host "포트 8088을 사용 중인 프로세스를 발견했습니다:" -ForegroundColor Yellow
    $connections | ForEach-Object {
        $process = Get-Process -Id $_.OwningProcess -ErrorAction SilentlyContinue
        if ($process) {
            Write-Host "  - PID $($_.OwningProcess): $($process.ProcessName)" -ForegroundColor White
        }
    }

    Write-Host ""
    Write-Host "프로세스를 종료합니다..." -ForegroundColor Yellow

    $connections | ForEach-Object {
        try {
            Stop-Process -Id $_.OwningProcess -Force -ErrorAction Stop
            Write-Host "  ✓ PID $($_.OwningProcess) 종료 완료" -ForegroundColor Green
        } catch {
            Write-Host "  ✗ PID $($_.OwningProcess) 종료 실패: $_" -ForegroundColor Red
        }
    }

    Write-Host ""
    Write-Host "⏳ 포트 정리를 위해 3초 대기..." -ForegroundColor Yellow
    Start-Sleep -Seconds 3
} else {
    Write-Host "✅ 포트 8088이 사용 가능합니다." -ForegroundColor Green
}

Write-Host ""

# 2. 백엔드 디렉토리로 이동
Write-Host "📂 2단계: 백엔드 디렉토리로 이동..." -ForegroundColor Yellow
Set-Location -Path "$PSScriptRoot\backend"
Write-Host "  ✓ 현재 위치: $(Get-Location)" -ForegroundColor Green
Write-Host ""

# 3. 애플리케이션 실행
Write-Host "🚀 3단계: Spring Boot 애플리케이션 시작..." -ForegroundColor Yellow
Write-Host ""
Write-Host "=====================================================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "애플리케이션 실행 중..." -ForegroundColor White
Write-Host ""
Write-Host "완료되면 다음 URL로 접속하세요:" -ForegroundColor Cyan
Write-Host "  📱 메인:     http://localhost:8088/" -ForegroundColor Green
Write-Host "  🤖 n8n UI:  http://localhost:8088/n8n" -ForegroundColor Green
Write-Host ""
Write-Host "중지하려면 Ctrl+C를 누르세요." -ForegroundColor Yellow
Write-Host ""
Write-Host "=====================================================================" -ForegroundColor Cyan
Write-Host ""

# Maven 실행
mvn spring-boot:run

