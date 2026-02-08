## 🚀 프로젝트 실행 방법

### ✅ 추천 방법: IntelliJ IDEA에서 실행

1. **IntelliJ IDEA에서 프로젝트 열기**
   - File → Open → `C:\Users\eh584\IdeaProjects\spring-boot-vuejs` 선택

2. **Spring Boot 애플리케이션 실행**
   - 방법 1: `SpringBootVuejsApplication.java` 파일을 열고 `main` 메서드 옆의 ▶️ 버튼 클릭
   - 방법 2: Maven 탭에서 `backend → Plugins → spring-boot → spring-boot:run` 더블클릭
   - 방법 3: 터미널에서 실행 (아래 참조)

3. **실행 확인**
   - 콘솔에 "Started SpringBootVuejsApplication" 메시지가 나타나면 성공!
   - 브라우저에서 http://localhost:8088 접속

---

### 💻 터미널에서 실행

#### PowerShell 또는 CMD에서:

```bash
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs\backend
mvn spring-boot:run
```

**실행 화면:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.1.RELEASE)

... 애플리케이션 시작 로그 ...
Started SpringBootVuejsApplication in X.XXX seconds
```

---

### 🌐 접속 URL

애플리케이션이 시작되면:

| 페이지 | URL | 설명 |
|--------|-----|------|
| 🏠 메인 | http://localhost:8088/ | Vue.js 프론트엔드 홈페이지 |
| 🤖 n8n 통합 | http://localhost:8088/n8n | n8n 워크플로우 관리 UI |
| 👤 사용자 관리 | http://localhost:8088/user | 사용자 CRUD UI |
| 🎨 Bootstrap | http://localhost:8088/bootstrap | Bootstrap 예제 |
| 🔧 Service | http://localhost:8088/callservice | Service 호출 예제 |

---

### 🧪 API 테스트

새 터미널 창을 열고 다음 명령어로 테스트:

```powershell
# Hello API 테스트
Invoke-WebRequest http://localhost:8088/api/hello

# n8n Health Check
Invoke-WebRequest http://localhost:8088/api/n8n/health

# 사용자 생성 (n8n 자동 트리거)
Invoke-RestMethod -Uri http://localhost:8088/api/user -Method POST -ContentType "application/json" -Body '{"firstName":"홍","lastName":"길동"}'
```

또는 curl 사용:

```bash
curl http://localhost:8088/api/hello
curl http://localhost:8088/api/n8n/health
curl -X POST http://localhost:8088/api/user -H "Content-Type: application/json" -d "{\"firstName\":\"홍\",\"lastName\":\"길동\"}"
```

---

### 🛑 애플리케이션 중지

- IntelliJ에서: 빨간 정지 버튼 ⬛ 클릭
- 터미널에서: `Ctrl + C`

---

### 🔍 실행 중 확인

```powershell
# 8088 포트 사용 중인지 확인
netstat -ano | findstr :8088

# 브라우저에서 바로 열기
Start-Process "http://localhost:8088"
```

---

### ⚠️ 문제 해결

#### 포트 8088이 이미 사용 중인 경우:

```powershell
# 포트 사용 프로세스 찾기
netstat -ano | findstr :8088

# PID로 프로세스 종료 (예: PID가 1234인 경우)
taskkill /PID 1234 /F
```

#### 빌드가 필요한 경우:

```bash
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs
mvn clean install -DskipTests
```

---

### 🎯 빠른 실행 체크리스트

- [ ] Java 설치 확인 (`java -version`)
- [ ] Maven 설치 확인 (`mvn -version`)
- [ ] 프로젝트 디렉토리로 이동
- [ ] `mvn spring-boot:run` 실행
- [ ] "Started SpringBootVuejsApplication" 메시지 확인
- [ ] 브라우저에서 http://localhost:8088 접속

---

**실행이 완료되면 브라우저를 열고 위의 URL로 접속하세요!** 🎉

