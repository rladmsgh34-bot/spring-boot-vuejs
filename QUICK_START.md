# 🚀 빠른 시작 가이드

## 실행 방법

### 옵션 1: 스크립트 사용 (권장)

#### Windows에서 실행:

1. **프로젝트 빌드** (최초 1회 또는 코드 변경 시)
   ```powershell
   .\build.ps1
   ```

2. **애플리케이션 실행**
   ```powershell
   .\start.ps1
   ```
   또는
   ```cmd
   start.bat
   ```

3. **브라우저에서 확인**
   - http://localhost:8088/ - 메인 페이지
   - http://localhost:8088/n8n - n8n 통합 페이지

#### n8n 실행 (선택사항):

```powershell
.\start-n8n.ps1
```

n8n 접속: http://localhost:5678

---

### 옵션 2: 수동 실행

1. **빌드**
   ```bash
   mvn clean install -DskipTests
   ```

2. **실행**
   ```bash
   cd backend
   mvn spring-boot:run
   ```

---

## 📋 주요 URL

| 서비스 | URL | 설명 |
|--------|-----|------|
| 메인 페이지 | http://localhost:8088/ | Vue.js 프론트엔드 |
| n8n 통합 | http://localhost:8088/n8n | n8n 워크플로우 관리 UI |
| REST API | http://localhost:8088/api/* | Spring Boot REST API |
| n8n (선택) | http://localhost:5678 | n8n 워크플로우 편집기 |

---

## 🧪 API 테스트

### 헬스체크
```bash
curl http://localhost:8088/api/n8n/health
```

### 사용자 생성
```bash
curl -X POST http://localhost:8088/api/user \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe"}'
```

### n8n 워크플로우 트리거
```bash
curl -X POST http://localhost:8088/api/n8n/trigger \
  -H "Content-Type: application/json" \
  -d '{"event":"test","message":"Hello"}'
```

---

## 🛠️ 문제 해결

### 포트가 이미 사용 중인 경우

**8088 포트 확인:**
```powershell
netstat -ano | findstr :8088
```

**프로세스 종료:**
```powershell
# PID 찾기
Get-Process | Where-Object {$_.Id -eq <PID>}
# 프로세스 종료
Stop-Process -Id <PID> -Force
```

### 빌드 오류 해결

1. Java 버전 확인 (Java 8 이상 필요):
   ```bash
   java -version
   ```

2. Maven 버전 확인:
   ```bash
   mvn -version
   ```

3. 클린 빌드:
   ```bash
   mvn clean install -DskipTests
   ```

---

## 📚 추가 문서

- **N8N_SETUP_COMPLETE.md** - n8n 통합 완료 요약
- **N8N_INTEGRATION_GUIDE.md** - n8n 상세 통합 가이드
- **README.md** - 전체 프로젝트 문서

---

## 🎯 다음 단계

1. ✅ 애플리케이션 실행
2. ✅ 브라우저에서 http://localhost:8088 접속
3. 🔄 n8n 설치 및 워크플로우 생성 (선택사항)
4. 🔄 n8n과 연동하여 자동화 테스트

---

**즐거운 개발 되세요!** 🎉

