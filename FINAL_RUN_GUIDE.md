# 🎯 프로젝트 실행 최종 가이드

## ⚠️ 발생한 오류 및 해결

### 오류: 포트 8088이 이미 사용 중
```
java.net.BindException: Address already in use: bind
```

**원인:** 이전에 실행한 프로세스가 포트를 점유하고 있음

**해결:** 포트 정리 후 재실행

---

## ✅ 확실한 실행 방법 (3가지)

### 🥇 방법 1: IntelliJ IDEA 사용 (가장 권장!)

#### 단계별 실행:

1. **IntelliJ IDEA 실행**

2. **프로젝트 열기**
   - File → Open
   - `C:\Users\eh584\IdeaProjects\spring-boot-vuejs` 선택

3. **Run Configuration 확인**
   - 상단 Run 메뉴 옆 드롭다운에서 "SpringBootVuejsApplication" 확인
   - 없으면: Run → Edit Configurations → + → Spring Boot
     - Name: SpringBootVuejsApplication
     - Main class: com.jeonguk.vuejs.SpringBootVuejsApplication
     - Module: backend

4. **실행**
   - ▶️ 녹색 실행 버튼 클릭
   - 또는 `Shift + F10`

5. **성공 확인**
   - 콘솔에서 "Started SpringBootVuejsApplication" 메시지 확인
   - 브라우저에서 http://localhost:8088 접속

---

### 🥈 방법 2: PowerShell 스크립트 사용

#### 옵션 A: 자동 포트 정리 스크립트

```powershell
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs
.\start-clean.ps1
```

이 스크립트는:
1. 포트 8088 사용 프로세스 자동 종료
2. Maven으로 Spring Boot 실행

#### 옵션 B: 수동 실행

```powershell
# 1. 포트 정리
Get-NetTCPConnection -LocalPort 8088 -ErrorAction SilentlyContinue | 
    ForEach-Object { Stop-Process -Id $_.OwningProcess -Force }

# 2. 대기
Start-Sleep -Seconds 3

# 3. 실행
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs\backend
mvn spring-boot:run
```

---

### 🥉 방법 3: 다른 포트 사용

`backend/src/main/resources/application.properties` 수정:

```properties
server.port=9090
```

실행:

```bash
cd backend
mvn spring-boot:run
```

접속: http://localhost:9090

---

## 🔍 실행 상태 확인

### 1. 콘솔 로그 확인

**성공 메시지:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.1.RELEASE)

...

Started SpringBootVuejsApplication in X.XXX seconds
```

### 2. PowerShell에서 테스트

```powershell
# API 테스트
Invoke-WebRequest http://localhost:8088/api/hello

# 예상 응답
StatusCode        : 200
StatusDescription : OK
Content           : {"data":"Hello from Spring Boot Backend!"}
```

### 3. 브라우저 테스트

다음 URL들을 열어보세요:

- ✅ http://localhost:8088/ - 메인 페이지
- ✅ http://localhost:8088/n8n - n8n 통합 UI
- ✅ http://localhost:8088/user - 사용자 관리
- ✅ http://localhost:8088/api/hello - API 테스트

---

## 🛠️ 포트 정리 명령어 모음

### Windows PowerShell

```powershell
# 방법 1: Get-NetTCPConnection 사용
Get-NetTCPConnection -LocalPort 8088 -ErrorAction SilentlyContinue | 
    ForEach-Object { Stop-Process -Id $_.OwningProcess -Force }

# 방법 2: netstat 사용
$port = netstat -ano | findstr :8088
if ($port) {
    $pid = ($port -split '\s+')[-1]
    taskkill /PID $pid /F
}

# 방법 3: 모든 Java 프로세스 종료 (주의!)
Get-Process -Name java -ErrorAction SilentlyContinue | Stop-Process -Force
```

### CMD

```cmd
netstat -ano | findstr :8088
taskkill /PID <PID번호> /F
```

---

## 📊 프로젝트 구조

```
spring-boot-vuejs/
├── backend/                    ← Spring Boot 애플리케이션
│   ├── src/main/java/
│   │   └── com/jeonguk/vuejs/
│   │       ├── SpringBootVuejsApplication.java  ← 메인 클래스
│   │       ├── config/         ← 설정 (N8nConfig, WebMvcConfig)
│   │       ├── controller/     ← REST API (UserController, N8nWebhookController)
│   │       ├── service/        ← 비즈니스 로직 (N8nService)
│   │       ├── repository/     ← 데이터 접근
│   │       └── entity/         ← 엔티티
│   ├── src/main/resources/
│   │   └── application.properties  ← 설정 파일
│   └── pom.xml
├── frontend/                   ← Vue.js 애플리케이션
│   ├── src/
│   │   ├── components/
│   │   │   ├── N8nIntegration.vue  ← n8n UI (NEW!)
│   │   │   ├── User.vue
│   │   │   └── ...
│   │   ├── App.vue
│   │   └── router.js
│   └── pom.xml
├── start-clean.ps1             ← 포트 정리 후 실행 스크립트
├── start.ps1                   ← 간단 실행 스크립트
├── build.ps1                   ← 빌드 스크립트
├── TROUBLESHOOTING.md          ← 문제 해결 가이드
└── README.md
```

---

## 🎓 실행 체크리스트

### 실행 전:
- [ ] Java 설치 확인 (`java -version`)
- [ ] Maven 설치 확인 (`mvn -version`)
- [ ] 포트 8088이 사용 가능한지 확인
- [ ] 프로젝트 빌드 완료 (`mvn clean install -DskipTests`)

### 실행:
- [ ] IntelliJ에서 실행 또는 PowerShell 스크립트 실행
- [ ] 콘솔에서 "Started SpringBootVuejsApplication" 확인
- [ ] 포트 8088 리스닝 확인

### 실행 후:
- [ ] http://localhost:8088/ 접속 테스트
- [ ] http://localhost:8088/api/hello API 테스트
- [ ] http://localhost:8088/n8n n8n UI 확인

---

## 🚨 자주 발생하는 문제

### 1. "Address already in use"
**해결:** 포트 8088 정리 후 재실행

### 2. "Unable to make field accessible"
**원인:** Java 버전 불일치 (Java 18 vs Java 8)
**해결:** 무시하거나 Java 8/11 사용

### 3. Maven 빌드 실패
**해결:** 
```bash
mvn clean install -DskipTests -U
```

### 4. 브라우저에서 404 에러
**원인:** 애플리케이션이 아직 시작 중
**해결:** 콘솔에서 "Started" 메시지 확인 후 재시도

---

## 🎉 성공 화면

### API 응답
```json
{
  "data": "Hello from Spring Boot Backend!"
}
```

### 메인 페이지
- Vue.js SPA 로딩
- 네비게이션: Hello | Service | Bootstrap | User | **n8n Integration**

### n8n 통합 UI
- 워크플로우 트리거 폼
- 이벤트 타입 선택
- JSON 데이터 입력
- 실시간 결과 표시

---

## 📞 도움말

### 문서
- `TROUBLESHOOTING.md` - 상세 문제 해결
- `HOW_TO_RUN.md` - 실행 가이드
- `N8N_INTEGRATION_GUIDE.md` - n8n 사용법

### 로그 확인
- IntelliJ: Run 탭에서 콘솔 출력 확인
- PowerShell: 터미널 출력 확인
- 파일: `backend/target/` 디렉토리

---

**가장 확실한 방법: IntelliJ IDEA에서 실행하세요!** ✨

Run Configuration이 이미 생성되어 있습니다:
- `.idea/runConfigurations/SpringBootVuejsApplication.xml`

IntelliJ에서 ▶️ 버튼만 누르면 됩니다! 🚀

