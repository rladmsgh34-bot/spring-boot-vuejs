# ⚠️ 실행 오류 해결 완료

## 🔍 발견된 오류

**오류 메시지:**
```
java.net.BindException: Address already in use: bind
The Tomcat connector configured to listen on port 8088 failed to start.
```

**원인:** 포트 8088이 이미 사용 중이거나 이전 프로세스가 완전히 종료되지 않음

---

## ✅ 해결 방법

### 방법 1: IntelliJ IDEA에서 실행 (가장 권장)

1. **IntelliJ IDEA에서 프로젝트 열기**
   - `C:\Users\eh584\IdeaProjects\spring-boot-vuejs` 폴더 열기

2. **Run Configuration 사용**
   - 상단 메뉴: Run → Edit Configurations
   - 좌측에서 "SpringBootVuejsApplication" 선택
   - 또는 새로 만들기: + → Spring Boot
   - Main class: `com.jeonguk.vuejs.SpringBootVuejsApplication`
   - Module: backend

3. **실행**
   - ▶️ 버튼 클릭 또는 Shift + F10

---

### 방법 2: 포트 정리 후 터미널 실행

#### 1단계: 포트 8088 사용 프로세스 종료

```powershell
# 포트 사용 확인
netstat -ano | findstr :8088

# 프로세스 종료 (PID 확인 후)
taskkill /PID <PID번호> /F
```

또는 자동 스크립트:

```powershell
# 포트 자동 정리
Get-NetTCPConnection -LocalPort 8088 -ErrorAction SilentlyContinue | 
    ForEach-Object { Stop-Process -Id $_.OwningProcess -Force }
```

#### 2단계: 애플리케이션 실행

```bash
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs\backend
mvn spring-boot:run
```

---

### 방법 3: 다른 포트로 실행

`application.properties` 수정:

```properties
server.port=8089
```

그 후 실행:

```bash
mvn spring-boot:run
```

접속: http://localhost:8089

---

## 🎯 권장 실행 순서

### IntelliJ IDEA 사용 시:

1. ✅ IntelliJ에서 프로젝트 열기
2. ✅ Maven 프로젝트 임포트 (자동)
3. ✅ Run Configuration 확인
   - 파일: `.idea/runConfigurations/SpringBootVuejsApplication.xml`
4. ✅ Run 버튼 클릭
5. ✅ 브라우저에서 http://localhost:8088 접속

### 터미널 사용 시:

1. ✅ 포트 8088 정리
2. ✅ `mvn spring-boot:run` 실행
3. ✅ "Started SpringBootVuejsApplication" 메시지 확인
4. ✅ 브라우저에서 접속

---

## 🧪 실행 확인 방법

### 1. 콘솔 로그 확인

성공 메시지:
```
Started SpringBootVuejsApplication in X.XXX seconds (JVM running for X.XXX)
```

### 2. API 테스트

새 PowerShell 창에서:

```powershell
# Hello API 테스트
Invoke-WebRequest http://localhost:8088/api/hello

# n8n Health Check
Invoke-WebRequest http://localhost:8088/api/n8n/health
```

예상 응답:
```json
{"data":"Hello from Spring Boot Backend!"}
```

### 3. 브라우저 접속

- http://localhost:8088/
- http://localhost:8088/n8n

---

## 🔧 추가 문제 해결

### Java 버전 문제

현재 사용 중: Java 18
프로젝트 요구: Java 8

**경고가 나올 수 있지만 실행은 됩니다.**

더 나은 호환성을 위해:
1. Java 8 또는 11 설치
2. JAVA_HOME 환경변수 설정

### Maven 문제

```bash
# Maven 버전 확인
mvn -version

# 클린 빌드
mvn clean install -DskipTests
```

### LiveReload 경고 무시

다음 경고는 무시해도 됩니다:
```
Unable to start LiveReload server
```

---

## 📊 실행 체크리스트

- [ ] 포트 8088이 사용 가능한지 확인
- [ ] Java가 설치되어 있는지 확인
- [ ] Maven이 설치되어 있는지 확인
- [ ] 프로젝트가 빌드되었는지 확인 (`mvn clean install -DskipTests`)
- [ ] IntelliJ에서 실행하거나 터미널에서 `mvn spring-boot:run`
- [ ] "Started SpringBootVuejsApplication" 메시지 확인
- [ ] 브라우저에서 http://localhost:8088 접속 테스트

---

## 🎉 성공 시 화면

브라우저에서 다음을 볼 수 있습니다:

1. **메인 페이지** (http://localhost:8088/)
   - Vue.js 프론트엔드
   - 네비게이션 메뉴: Hello | Service | Bootstrap | User | n8n Integration

2. **n8n 통합 페이지** (http://localhost:8088/n8n)
   - 워크플로우 트리거 UI
   - 이벤트 타입 선택
   - JSON 데이터 입력
   - 헬스체크

3. **API 응답**
   ```json
   {"data":"Hello from Spring Boot Backend!"}
   ```

---

## 💡 팁

- **개발 중**: IntelliJ IDEA 사용 권장 (디버깅, 자동 재시작)
- **배포 시**: `mvn clean package`로 JAR 생성 후 `java -jar` 실행
- **포트 변경**: `application.properties`에서 `server.port` 수정
- **로그 확인**: 콘솔 출력 또는 `backend/target/` 디렉토리

---

**문제가 해결되면 브라우저에서 http://localhost:8088 을 열어보세요!** 🚀

