# 🎨 프론트엔드 실행 가이드

## ✅ 현재 상태: 프론트엔드는 이미 실행 중입니다!

### 📊 프로젝트 구조 이해

이 프로젝트는 **통합 빌드 방식**을 사용합니다:

```
Maven 빌드 시:
1. frontend/ 에서 Vue.js 빌드 (npm run build)
2. 빌드된 파일(target/dist/)을 backend/src/main/resources/public/으로 복사
3. Spring Boot가 정적 파일로 서빙

결과: Spring Boot만 실행하면 프론트엔드도 함께 실행됩니다!
```

---

## 🌐 프론트엔드 접속 방법

### Spring Boot 백엔드를 통한 접속 (현재 방식)

Spring Boot가 실행 중이면 프론트엔드도 이미 실행되고 있습니다!

**접속 URL:**
- 🏠 메인 페이지: http://localhost:8088/
- 🤖 n8n 통합: http://localhost:8088/n8n
- 👤 사용자 관리: http://localhost:8088/user
- 🎨 Bootstrap: http://localhost:8088/bootstrap
- 🔧 Service: http://localhost:8088/callservice

**장점:**
- ✅ 하나의 서버만 실행 (포트 8088)
- ✅ CORS 문제 없음
- ✅ 프로덕션 환경과 동일
- ✅ 설정 간단

---

## 🛠️ Vue.js 개발 서버 실행 (선택사항)

개발 중 Hot Reload가 필요하다면 별도의 개발 서버를 실행할 수 있습니다.

### ⚠️ 문제: Node.js 버전 호환성

**오류:**
```
Error: No such module: http_parser
```

**원인:** 
- 프로젝트는 Node.js v11.6.0을 요구 (2019년 버전)
- 현재 시스템의 Node.js는 최신 버전 (v18+)
- `http_parser` 모듈이 최신 Node.js에서 제거됨

### 해결 방법 1: nvm으로 Node.js v11 사용

```powershell
# nvm 설치 (없다면)
# https://github.com/coreybutler/nvm-windows/releases

# Node.js v11 설치
nvm install 11.6.0

# Node.js v11 사용
nvm use 11.6.0

# 프론트엔드 실행
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs\frontend
npm install
npm run serve
```

**접속:** http://localhost:8080 (기본 포트)

### 해결 방법 2: package.json 업데이트 (권장)

dependencies를 최신 버전으로 업데이트:

```json
{
  "dependencies": {
    "vue": "^2.7.14",
    "vue-router": "^3.6.5",
    "axios": "^1.6.0",
    "bootstrap": "^4.6.2"
  },
  "devDependencies": {
    "@vue/cli-service": "^5.0.8",
    "@vue/cli-plugin-babel": "^5.0.8",
    // ... 기타
  }
}
```

그 후:
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
npm run serve
```

---

## 🎯 권장 개발 방식

### 옵션 1: 통합 실행 (현재 방식) ⭐

**사용 시기:** 
- 일반적인 개발
- 테스트
- 데모

**실행 방법:**
```bash
# 1. 빌드 (코드 변경 시)
mvn clean install -DskipTests

# 2. 백엔드 실행
mvn --projects backend spring-boot:run

# 3. 브라우저에서 접속
http://localhost:8088
```

**장점:**
- 간단함
- CORS 문제 없음
- 프로덕션과 동일한 환경

**단점:**
- Vue 코드 변경 시 재빌드 필요
- Hot Reload 없음

---

### 옵션 2: 분리 실행 (개발 서버)

**사용 시기:**
- Vue.js 코드 집중 개발
- 빠른 피드백 필요
- Hot Reload 원할 때

**실행 방법:**
```bash
# 터미널 1: 백엔드
cd backend
mvn spring-boot:run
# → http://localhost:8088

# 터미널 2: 프론트엔드
cd frontend
npm run serve
# → http://localhost:8080
```

**필요한 설정:**
```javascript
// frontend/src/components/http-common.js
export const AXIOS = axios.create({
  baseURL: 'http://localhost:8088',  // 백엔드 URL
  headers: {
    'Content-Type': 'application/json'
  }
});
```

**장점:**
- Hot Reload
- 빠른 개발

**단점:**
- 두 개의 서버 실행 필요
- CORS 설정 필요 (이미 완료됨)
- Node.js 버전 관리 필요

---

## 🔧 현재 프로젝트 설정

### Backend (Spring Boot)

**포트:** 8088

**정적 파일 위치:**
```
backend/src/main/resources/public/
├── index.html
├── favicon.ico
└── static/
    ├── css/
    └── js/
```

**CORS 설정:** ✅ 이미 완료
```java
// WebMvcConfig.java
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
}
```

### Frontend (Vue.js)

**개발 서버 포트:** 8080 (기본)

**빌드 출력:** `frontend/target/dist/`

**Maven 통합:** ✅
- `frontend-maven-plugin`이 Node.js v11.6.0 설치
- 빌드 시 자동으로 `npm install` 및 `npm run build` 실행
- 빌드된 파일을 backend로 복사

---

## 📝 개발 워크플로우

### 백엔드 코드 수정 시:

1. Java 코드 수정
2. Spring Boot 재시작 (IntelliJ에서 자동 또는 수동)
3. 브라우저 새로고침

### 프론트엔드 코드 수정 시:

#### 방법 A: 통합 빌드 (느림)
```bash
# 1. 코드 수정
# 2. 재빌드
mvn clean install -DskipTests
# 3. 백엔드 재시작
# 4. 브라우저 새로고침
```

#### 방법 B: 개발 서버 (빠름)
```bash
# 1. 개발 서버 실행 (한 번만)
cd frontend
npm run serve

# 2. 코드 수정
# 3. 자동 Hot Reload
```

---

## 🎉 현재 상태 확인

### 프론트엔드가 실행 중인지 확인:

```powershell
# 브라우저에서 또는
Invoke-WebRequest http://localhost:8088/

# 응답이 오면 → 프론트엔드 실행 중! ✅
```

### 브라우저 개발자 도구에서 확인:

1. F12 → Console 탭
2. 에러 없이 Vue 앱이 로드되면 OK
3. Network 탭에서 `/static/js/app.*.js` 파일 확인

---

## 🚀 빠른 시작

### 지금 바로 확인:

```powershell
# 1. 브라우저 열기
Start-Process "http://localhost:8088"
Start-Process "http://localhost:8088/n8n"

# 2. API 테스트
Invoke-WebRequest http://localhost:8088/api/hello
```

---

## 📚 요약

| 항목 | 통합 실행 | 분리 실행 |
|------|----------|----------|
| **백엔드** | http://localhost:8088 | http://localhost:8088 |
| **프론트엔드** | http://localhost:8088 (동일) | http://localhost:8080 |
| **실행 명령** | `mvn spring-boot:run` (1개) | 백엔드 + 프론트엔드 (2개) |
| **Hot Reload** | ❌ | ✅ |
| **CORS** | 불필요 | 필요 (이미 설정됨) |
| **권장 용도** | 일반 개발, 테스트, 데모 | Vue 집중 개발 |

---

## 💡 결론

**✅ 프론트엔드는 이미 실행 중입니다!**

Spring Boot 백엔드(http://localhost:8088)를 통해 Vue.js 프론트엔드가 서빙되고 있습니다.

**브라우저에서 확인하세요:**
- http://localhost:8088/ ← 지금 열어보세요!
- http://localhost:8088/n8n ← n8n 통합 UI

개발 서버가 필요하면 위의 "해결 방법"을 참고하세요. 하지만 대부분의 경우 현재 방식으로 충분합니다!

---

**프론트엔드가 이미 실행되고 있습니다! 브라우저에서 확인하세요!** 🎨✨

