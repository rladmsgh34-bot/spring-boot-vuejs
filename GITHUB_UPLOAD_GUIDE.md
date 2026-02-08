# GitHub 업로드 가이드

## 📝 프로젝트 변경 완료

### ✅ 완료된 작업

1. **패키지 이름 변경**: `com.jeonguk` → `com.eunho`
   - 모든 Java 파일의 package 선언 변경
   - 모든 import 문 변경
   - 디렉토리 구조 변경
   - pom.xml의 groupId 변경

2. **Git 저장소 초기화**
   - `.git` 디렉토리 생성
   - 모든 파일 커밋 완료

---

## 🚀 GitHub에 올리는 방법

### 1단계: GitHub에서 새 저장소 생성

1. https://github.com 접속 및 로그인
2. 우측 상단 **+** 버튼 클릭 → **New repository**
3. Repository 정보 입력:
   - Repository name: `spring-boot-vuejs` (또는 원하는 이름)
   - Description: `Spring Boot + Vue.js + n8n Integration`
   - Public 또는 Private 선택
   - **중요**: "Initialize this repository with a README" 체크 **해제**
4. **Create repository** 클릭

### 2단계: GitHub 저장소와 연결

새 저장소 페이지에서 표시되는 URL을 복사한 후:

```bash
# PowerShell에서 실행
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs

# GitHub 저장소 URL로 변경 (여기서 YOUR_USERNAME을 실제 GitHub 사용자명으로 변경)
git remote add origin https://github.com/YOUR_USERNAME/spring-boot-vuejs.git

# 또는 SSH 사용 시
# git remote add origin git@github.com:YOUR_USERNAME/spring-boot-vuejs.git

# 메인 브랜치 설정
git branch -M main

# GitHub에 푸시
git push -u origin main
```

### 3단계: 인증

Git push 시 GitHub 인증 요청:

#### Option A: Personal Access Token 사용 (권장)

1. GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic)
2. **Generate new token** 클릭
3. Note: "spring-boot-vuejs"
4. Expiration: 원하는 기간 선택
5. Scopes: `repo` 체크
6. **Generate token** 클릭
7. 생성된 토큰 복사 (한 번만 표시됨!)
8. Git push 시 비밀번호로 토큰 사용

#### Option B: GitHub CLI 사용

```bash
# GitHub CLI 설치 후
gh auth login

# 푸시
git push -u origin main
```

---

## 📋 빠른 실행 명령어

```powershell
# 1. 프로젝트 디렉토리로 이동
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs

# 2. GitHub 저장소 연결 (YOUR_USERNAME 변경 필요!)
git remote add origin https://github.com/YOUR_USERNAME/spring-boot-vuejs.git

# 3. 브랜치 이름 설정
git branch -M main

# 4. GitHub에 푸시
git push -u origin main
```

---

## 🔍 변경 사항 확인

### 변경된 파일들:

1. **Java 소스 파일** (모든 .java 파일)
   - Package: `com.jeonguk.vuejs.*` → `com.eunho.vuejs.*`
   - Import: 모든 `com.jeonguk.*` → `com.eunho.*`

2. **디렉토리 구조**
   - `backend/src/main/java/com/jeonguk/` → `backend/src/main/java/com/eunho/`
   - `backend/src/test/java/com/jeonguk/` → `backend/src/test/java/com/eunho/`

3. **POM 파일**
   - `pom.xml`: groupId 변경
   - `backend/pom.xml`: groupId 변경
   - `frontend/pom.xml`: groupId 변경

### 변경된 클래스 목록:

```
com.eunho.vuejs.SpringBootVuejsApplication
com.eunho.vuejs.config.N8nConfig
com.eunho.vuejs.config.WebMvcConfig
com.eunho.vuejs.controller.rest.UserController
com.eunho.vuejs.controller.rest.N8nWebhookController
com.eunho.vuejs.dto.ResponseUser
com.eunho.vuejs.dto.UserDTO
com.eunho.vuejs.dto.N8nWebhookRequest
com.eunho.vuejs.dto.N8nWebhookResponse
com.eunho.vuejs.entity.User
com.eunho.vuejs.mapper.UserMapper
com.eunho.vuejs.repository.UserRepository
com.eunho.vuejs.service.UserService
com.eunho.vuejs.service.UserServiceImpl
com.eunho.vuejs.service.N8nService
com.eunho.vuejs.service.N8nServiceImpl
```

---

## ✅ 확인 사항

빌드 및 실행 테스트:

```bash
# 빌드 테스트
mvn clean install -DskipTests

# 실행 테스트
cd backend
mvn spring-boot:run
```

모든 것이 정상 작동하면 GitHub에 푸시하세요!

---

## 📚 프로젝트 정보

### 기술 스택:
- **Backend**: Spring Boot 2.1.1
- **Frontend**: Vue.js 2.x
- **Integration**: n8n workflow automation
- **Build**: Maven
- **Package**: com.eunho.vuejs

### 주요 기능:
- ✅ Spring Boot REST API
- ✅ Vue.js SPA
- ✅ n8n 워크플로우 통합
- ✅ 사용자 관리 (CRUD)
- ✅ H2 인메모리 데이터베이스

---

## 🎯 다음 단계

1. GitHub에 푸시 완료
2. README.md 업데이트
3. 프로젝트 설명 추가
4. 필요시 이슈/프로젝트 설정

---

**이제 GitHub에 업로드할 준비가 완료되었습니다!** 🚀

