# 🚀 GitHub 연결 완료 가이드

## ✅ 현재 상태

- ✅ 프로젝트 준비 완료
- ✅ 패키지 변경 완료 (com.jeonguk → com.eunho)
- ⚠️ Git 사용자 정보 설정 필요

---

## 📝 GitHub에 올리는 3단계

### 1단계: Git 사용자 정보 설정 (1회만)

PowerShell에서 실행:

```powershell
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs

# 사용자 이름 설정 (실제 이름으로 변경)
git config --global user.name "Your Name"

# 이메일 설정 (GitHub 이메일로 변경)
git config --global user.email "your.email@example.com"

# 확인
git config user.name
git config user.email
```

### 2단계: Git 저장소 초기화 및 커밋

```powershell
# Git 초기화
git init

# 모든 파일 추가
git add .

# 커밋
git commit -m "Initial commit: Spring Boot + Vue.js + n8n Integration"
```

### 3단계: GitHub 저장소 생성 및 연결

#### A. GitHub에서 새 저장소 생성

1. **https://github.com/new** 접속
2. 정보 입력:
   - **Repository name**: `spring-boot-vuejs`
   - **Description**: `Spring Boot + Vue.js + n8n Integration`
   - **Public** 선택
   - ⚠️ **"Initialize this repository with a README" 체크 해제!**
3. **Create repository** 클릭

#### B. 생성된 저장소와 연결

저장소 생성 후 표시되는 URL 복사 (예: `https://github.com/eunho/spring-boot-vuejs.git`)

PowerShell에서 실행:

```powershell
# 원격 저장소 추가 (YOUR_USERNAME을 실제 사용자명으로 변경!)
git remote add origin https://github.com/YOUR_USERNAME/spring-boot-vuejs.git

# 브랜치 이름을 main으로 설정
git branch -M main

# GitHub에 푸시
git push -u origin main
```

#### C. 인증

푸시 시 인증 창이 뜨면:
- **GitHub 계정 로그인**
- 또는 **Personal Access Token** 입력

---

## 🔑 Personal Access Token 만들기 (인증 필요 시)

1. GitHub 로그인
2. **Settings** (우측 상단 프로필 → Settings)
3. 좌측 하단 **Developer settings**
4. **Personal access tokens** → **Tokens (classic)**
5. **Generate new token** → **Generate new token (classic)**
6. 설정:
   ```
   Note: spring-boot-vuejs
   Expiration: 90 days
   Select scopes:
     ✅ repo (전체 체크)
   ```
7. **Generate token**
8. ⚠️ **토큰 복사** (한 번만 표시됨!)
9. Git push 시 비밀번호로 이 토큰 사용

---

## 🎯 한 번에 실행하기

```powershell
# 1. 프로젝트 디렉토리로 이동
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs

# 2. Git 사용자 설정 (실제 정보로 변경!)
git config --global user.name "Eunho"
git config --global user.email "your.email@gmail.com"

# 3. Git 초기화
git init

# 4. 파일 추가
git add .

# 5. 커밋
git commit -m "Initial commit: Spring Boot + Vue.js + n8n Integration"

# 6. GitHub 저장소 연결 (YOUR_USERNAME 변경!)
git remote add origin https://github.com/YOUR_USERNAME/spring-boot-vuejs.git

# 7. 브랜치 설정
git branch -M main

# 8. 푸시
git push -u origin main
```

---

## ✅ 성공 확인

푸시 성공 후:

### 1. GitHub 웹사이트에서 확인
```
https://github.com/YOUR_USERNAME/spring-boot-vuejs
```

### 2. 로컬에서 확인
```powershell
# 원격 저장소 확인
git remote -v

# 커밋 로그 확인
git log --oneline

# 브랜치 확인
git branch -a
```

---

## 📊 프로젝트 정보

### 업로드될 내용:

- ✅ **Backend**: Spring Boot 2.1.1 (com.eunho.vuejs)
- ✅ **Frontend**: Vue.js 2.x
- ✅ **Integration**: n8n 워크플로우
- ✅ **Database**: H2 인메모리
- ✅ **Build**: Maven 설정
- ✅ **Documentation**: 모든 가이드 문서

### 파일 구조:
```
spring-boot-vuejs/
├── backend/          # Spring Boot 소스
├── frontend/         # Vue.js 소스
├── pom.xml          # Maven 설정
├── README.md        # 프로젝트 설명
└── *.md             # 가이드 문서들
```

---

## 🆘 문제 해결

### "remote origin already exists"

```powershell
git remote remove origin
git remote add origin https://github.com/YOUR_USERNAME/spring-boot-vuejs.git
```

### "failed to push some refs"

```powershell
# 강제 푸시 (주의!)
git push -u origin main --force
```

### 인증 실패

```powershell
# Credential Manager 재설정
git config --global credential.helper wincred

# 다시 푸시
git push -u origin main
```

---

## 🎊 완료!

GitHub에 성공적으로 업로드되면:

1. ✅ 저장소 URL에서 코드 확인
2. ✅ README.md 자동 표시
3. ✅ 다른 PC에서 clone 가능
4. ✅ 협업 및 공유 가능

---

## 📞 다음 단계

1. **GitHub 저장소 확인**
2. **README.md 수정** (필요 시)
3. **Issue/Project 설정** (필요 시)
4. **Branch 전략 수립** (개발 시)

---

**지금 시작하세요!** 🚀

1. GitHub에서 새 저장소 생성: https://github.com/new
2. 위의 명령어 실행
3. 푸시 완료!

