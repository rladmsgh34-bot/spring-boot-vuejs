# 🚨 404 오류 완전 해결 가이드

## ❌ 현재 상황

**404 오류 발생 원인:**
```
Whitelabel Error Page
This application has no explicit mapping for /error
There was an unexpected error (type=Not Found, status=404)
```

## 🔍 원인 파악

### n8n 설치와는 무관합니다!

**404 오류의 실제 원인:**

1. **백엔드가 실행되지 않음** 또는
2. **프론트엔드 빌드 파일이 없음**

n8n은 선택사항이며, 설치하지 않아도 애플리케이션은 정상 작동합니다.

---

## ✅ 해결 방법

### 방법 1: IntelliJ IDEA 사용 (가장 확실)

#### 1단계: 프로젝트 빌드
1. IntelliJ IDEA에서 프로젝트 열기
2. 터미널 탭 열기 (Alt + F12)
3. 다음 명령 실행:
   ```bash
   mvn clean install -DskipTests
   ```
4. "BUILD SUCCESS" 메시지 확인

#### 2단계: 백엔드 실행
1. `backend/src/main/java/com/jeonguk/vuejs/SpringBootVuejsApplication.java` 파일 열기
2. `main` 메서드 옆의 ▶️ 버튼 클릭
3. Run 탭에서 다음 메시지 확인:
   ```
   Started SpringBootVuejsApplication in X.XXX seconds
   ```

#### 3단계: 브라우저 테스트
- http://localhost:8088/
- http://localhost:8088/user
- http://localhost:8088/callservice

---

### 방법 2: 터미널 사용

#### Windows PowerShell에서:

```powershell
# 1. 프로젝트 디렉토리로 이동
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs

# 2. 빌드
mvn clean install -DskipTests

# 3. 백엔드 실행
cd backend
mvn spring-boot:run

# 4. 새 터미널에서 브라우저 열기
start http://localhost:8088/user
```

---

## 🎯 404 오류 해결 체크리스트

### ✅ 확인 사항:

- [ ] **1. 빌드 완료 확인**
  ```bash
  mvn clean install -DskipTests
  # "BUILD SUCCESS" 메시지 확인
  ```

- [ ] **2. 백엔드 실행 확인**
  ```bash
  # 콘솔에서 다음 메시지 확인:
  # "Started SpringBootVuejsApplication"
  ```

- [ ] **3. 포트 8088 사용 중 확인**
  ```powershell
  Test-NetConnection localhost -Port 8088
  # TcpTestSucceeded : True
  ```

- [ ] **4. API 테스트**
  ```powershell
  Invoke-WebRequest http://localhost:8088/api/hello
  # StatusCode: 200
  ```

- [ ] **5. 브라우저 테스트**
  - http://localhost:8088/user
  - 404 오류 없이 페이지 로드

---

## 🔧 문제별 해결 방법

### 문제 1: "mvn 명령을 찾을 수 없습니다"

**해결:**
```powershell
# Maven 설치 확인
mvn -version

# Maven이 없다면 IntelliJ IDEA 사용
```

### 문제 2: 빌드 실패

**해결:**
```bash
# 클린 빌드
mvn clean

# 다시 빌드
mvn install -DskipTests

# 또는
mvn clean install -DskipTests -U
```

### 문제 3: 포트 8088이 이미 사용 중

**해결:**
```powershell
# 포트 사용 프로세스 찾기
netstat -ano | findstr :8088

# 프로세스 종료 (PID 확인 후)
taskkill /PID <PID> /F

# 또는 모두 종료
Get-NetTCPConnection -LocalPort 8088 -ErrorAction SilentlyContinue | 
    ForEach-Object { Stop-Process -Id $_.OwningProcess -Force }
```

### 문제 4: 백엔드가 시작되지 않음

**증상:**
```
Invoke-WebRequest http://localhost:8088/api/hello
# 연결 실패
```

**해결:**
1. IntelliJ IDEA에서 직접 실행
2. 터미널에서 `mvn spring-boot:run` 실행
3. 콘솔에서 오류 메시지 확인

### 문제 5: 여전히 404 오류

**원인:** 프론트엔드 빌드 파일이 백엔드로 복사되지 않음

**확인:**
```powershell
# 파일 존재 확인
Test-Path "C:\Users\eh584\IdeaProjects\spring-boot-vuejs\backend\src\main\resources\public\index.html"
# False면 문제!
```

**해결:**
```bash
# 완전 클린 빌드
mvn clean install -DskipTests
```

---

## 📝 빠른 실행 명령어

### 한 번에 실행:

```powershell
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs

# 빌드 + 백엔드 실행
mvn clean install -DskipTests && cd backend && mvn spring-boot:run
```

### 백그라운드 실행:

```powershell
# 1. 빌드
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs
mvn clean install -DskipTests

# 2. 새 창에서 백엔드 실행
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs\backend; mvn spring-boot:run"

# 3. 브라우저 열기
Start-Sleep -Seconds 30
start http://localhost:8088/user
```

---

## 🎉 성공 확인

### 다음과 같이 표시되면 성공:

#### 콘솔:
```
Started SpringBootVuejsApplication in 12.345 seconds (JVM running for 13.456)
```

#### 브라우저:
- http://localhost:8088/user ✅
- http://localhost:8088/callservice ✅
- http://localhost:8088/n8n ✅

#### API 테스트:
```json
{
  "data": "Hello from Spring Boot Backend!"
}
```

---

## 💡 n8n 관련 참고사항

### n8n은 선택사항입니다

- ✅ n8n이 없어도 애플리케이션은 정상 작동
- ✅ 404 오류와는 무관
- ✅ n8n 기능만 작동하지 않을 뿐

### n8n을 사용하려면:

```bash
# 설치
npm install -g n8n

# 실행
n8n start

# 접속
http://localhost:5678
```

설정:
```properties
# backend/src/main/resources/application.properties
n8n.webhook-url=http://localhost:5678/webhook/your-webhook-id
```

---

## 🚨 여전히 안 된다면

### IntelliJ IDEA 사용을 강력 권장합니다!

1. IntelliJ IDEA 실행
2. Open Project → `C:\Users\eh584\IdeaProjects\spring-boot-vuejs`
3. Maven 프로젝트로 인식될 때까지 대기
4. `SpringBootVuejsApplication.java` 찾기
5. `main` 메서드 옆 ▶️ 버튼 클릭
6. Run 탭에서 "Started" 메시지 확인
7. 브라우저에서 http://localhost:8088/user 접속

---

## 📞 요약

**404 오류 원인:**
- ❌ n8n 미설치 (무관)
- ✅ 백엔드 미실행 (실제 원인)
- ✅ 프론트엔드 빌드 누락 (가능한 원인)

**해결:**
1. `mvn clean install -DskipTests` (빌드)
2. `mvn spring-boot:run` (실행)
3. http://localhost:8088/user (확인)

**가장 확실한 방법:**
- IntelliJ IDEA에서 직접 실행 ▶️

---

**n8n이 문제가 아닙니다! 백엔드를 실행하세요!** 🚀

