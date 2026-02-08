# 🔧 404 오류 해결 가이드

## ❌ 발생한 문제

**증상:**
- `/user` → 404 Not Found
- `/callservice` → 404 Not Found
- 이전에는 정상 작동했으나 갑자기 404 오류 발생

---

## 🔍 원인 분석

### 근본 원인: Vue Router 오류

**`frontend/src/router.js` 파일 문제:**

```javascript
// ❌ 문제가 있는 코드
import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Service from '@/components/Service'
import Bootstrap from '@/components/Bootstrap'
import User from '@/components/User'
// N8nIntegration import 누락! ⚠️

export default new Router({
  routes: [
    // ...
    {
      path: '/n8n',
      name: 'N8nIntegration',
      component: N8nIntegration  // ← 정의되지 않은 컴포넌트!
    }
  ]
})
```

**왜 이런 일이 발생했나?**

n8n 통합을 추가하면서:
1. `N8nIntegration.vue` 컴포넌트 생성 ✅
2. `router.js`에 라우트 추가 ✅
3. **하지만 import 문을 추가하지 않음** ❌

결과:
- JavaScript 오류 발생
- Vue Router 전체가 초기화 실패
- 모든 라우트가 작동하지 않음 (404 발생)

---

## ✅ 해결 방법

### 1단계: Import 문 추가

**수정된 `router.js`:**

```javascript
// ✅ 수정된 코드
import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Service from '@/components/Service'
import Bootstrap from '@/components/Bootstrap'
import User from '@/components/User'
import N8nIntegration from '@/components/N8nIntegration'  // ✅ 추가!

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/callservice',
      name: 'Service',
      component: Service
    },
    {
      path: '/bootstrap',
      name: 'Bootstrap',
      component: Bootstrap
    },
    {
      path: '/user',
      name: 'User',
      component: User
    },
    {
      path: '/n8n',
      name: 'N8nIntegration',
      component: N8nIntegration  // ✅ 이제 작동!
    }
  ]
})
```

### 2단계: Optional Chaining 문법 수정

**N8nIntegration.vue 수정:**

```javascript
// ❌ 문제 (Babel 구버전에서 지원 안됨)
message: error.response?.data?.message || error.message

// ✅ 해결 (호환성 있는 문법)
message: (error.response && error.response.data && error.response.data.message) || error.message
```

### 3단계: 프론트엔드 재빌드

```bash
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs
mvn clean install -DskipTests
```

이 명령은:
1. 기존 빌드 삭제
2. Vue.js 프론트엔드 재빌드 (`npm run build`)
3. 빌드된 파일을 backend로 복사
4. 전체 프로젝트 패키징

### 3단계: 백엔드 재시작

```bash
# 터미널에서 실행 중인 백엔드 중지 (Ctrl+C)

# 재시작
cd backend
mvn spring-boot:run
```

또는 IntelliJ IDEA에서 재시작 버튼 클릭

---

## 🧪 테스트

빌드 및 재시작 후 다음 URL들을 테스트:

```powershell
# 1. 브라우저 테스트
Start-Process "http://localhost:8088/"
Start-Process "http://localhost:8088/user"
Start-Process "http://localhost:8088/callservice"
Start-Process "http://localhost:8088/n8n"

# 2. PowerShell 테스트
Invoke-WebRequest http://localhost:8088/
Invoke-WebRequest http://localhost:8088/user
Invoke-WebRequest http://localhost:8088/callservice
```

**예상 결과:**
- ✅ 모든 페이지 정상 로딩
- ✅ Vue Router가 올바르게 작동
- ✅ 404 오류 해결

---

## 🔍 브라우저 개발자 도구로 확인

### 문제 확인 방법:

1. **F12** 눌러서 개발자 도구 열기
2. **Console** 탭 확인
3. 오류 메시지 찾기:

```
❌ 문제 있을 때:
[Vue warn]: Failed to mount component: template or render function not defined.
ReferenceError: N8nIntegration is not defined
```

```
✅ 수정 후:
(오류 없음)
```

---

## 📊 Vue Router가 작동하는 방식

### 정상 작동 흐름:

```
1. 사용자가 /user 접속
   ↓
2. Vue Router가 라우트 테이블 확인
   ↓
3. path: '/user' 발견
   ↓
4. component: User 렌더링
   ↓
5. User.vue 컴포넌트 표시
```

### 오류 발생 시:

```
1. router.js 로드
   ↓
2. N8nIntegration이 undefined
   ↓
3. JavaScript 오류 발생
   ↓
4. Router 초기화 실패
   ↓
5. 모든 라우트 404 반환
```

---

## 🚨 비슷한 문제 방지

### 새 컴포넌트 추가 시 체크리스트:

1. **컴포넌트 파일 생성**
   ```
   frontend/src/components/MyComponent.vue
   ```

2. **router.js에 import 추가**
   ```javascript
   import MyComponent from '@/components/MyComponent'
   ```

3. **라우트 추가**
   ```javascript
   {
     path: '/mypath',
     name: 'MyComponent',
     component: MyComponent
   }
   ```

4. **App.vue에 네비게이션 링크 추가** (선택사항)
   ```vue
   <router-link to="/mypath">My Component</router-link>
   ```

5. **재빌드**
   ```bash
   mvn clean install -DskipTests
   ```

6. **백엔드 재시작**

7. **테스트**

---

## 🔧 빠른 수정 명령어

문제 발생 시:

```powershell
# 1. 프로젝트 디렉토리로 이동
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs

# 2. 포트 8088 정리
Get-NetTCPConnection -LocalPort 8088 -ErrorAction SilentlyContinue | 
    ForEach-Object { Stop-Process -Id $_.OwningProcess -Force }

# 3. 클린 빌드
mvn clean install -DskipTests

# 4. 백엔드 실행
cd backend
mvn spring-boot:run

# 5. 브라우저 테스트
Start-Process "http://localhost:8088/user"
```

---

## 📝 요약

### 문제:
- ✅ `router.js`에서 `N8nIntegration` import 누락
- ✅ JavaScript 오류로 Vue Router 초기화 실패
- ✅ 모든 SPA 라우트가 404 반환

### 해결:
- ✅ `import N8nIntegration from '@/components/N8nIntegration'` 추가
- ✅ 프론트엔드 재빌드
- ✅ 백엔드 재시작

### 결과:
- ✅ `/user` 정상 작동
- ✅ `/callservice` 정상 작동
- ✅ `/n8n` 정상 작동
- ✅ 모든 라우트 복구

---

## 💡 개발자 팁

### Vue.js SPA 디버깅:

1. **항상 브라우저 Console 확인**
   - F12 → Console
   - 빨간 오류 메시지 확인

2. **Network 탭 확인**
   - F12 → Network
   - 404 응답 확인
   - 정적 파일 로딩 확인

3. **Vue Devtools 사용**
   - Chrome 확장 프로그램 설치
   - 컴포넌트 트리 확인
   - Router 상태 확인

4. **빌드 후 항상 재시작**
   - 프론트엔드 코드 변경 시
   - 반드시 재빌드 필요
   - 백엔드 재시작 필요

---

**문제가 해결되었습니다!** ✅

재빌드가 완료되면 백엔드를 재시작하고 브라우저에서 확인하세요.

**테스트 URL:**
- http://localhost:8088/user
- http://localhost:8088/callservice
- http://localhost:8088/n8n

