# GitHub 연결 가이드

## 🚀 현재 상태

- ✅ Git 저장소 초기화 완료
- ✅ 모든 파일 커밋 완료
- ✅ 패키지 이름 변경 완료 (com.jeonguk → com.eunho)

---

## 📝 GitHub에 연결하는 3가지 방법

### 방법 1: GitHub 웹사이트 사용 (가장 쉬움) ⭐

#### 1단계: GitHub에서 새 저장소 생성

1. **https://github.com** 접속 및 로그인
2. 우측 상단 **+** 버튼 → **New repository** 클릭
3. 저장소 정보 입력:
   ```
   Repository name: spring-boot-vuejs
   Description: Spring Boot + Vue.js + n8n Integration
   Visibility: Public (또는 Private)
   ⚠️ "Initialize this repository with a README" 체크 해제!
   ```
4. **Create repository** 클릭

#### 2단계: 생성된 저장소 URL 확인

새 저장소 페이지에 표시되는 URL을 복사:
```
https://github.com/YOUR_USERNAME/spring-boot-vuejs.git
```

#### 3단계: PowerShell에서 실행

```powershell
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs

# 원격 저장소 추가 (YOUR_USERNAME을 실제 GitHub 사용자명으로 변경!)
git remote add origin https://github.com/YOUR_USERNAME/spring-boot-vuejs.git

# 브랜치 이름을 main으로 설정
git branch -M main

# GitHub에 푸시
git push -u origin main
```

#### 4단계: 인증

푸시 시 GitHub 인증 창이 뜨면:
- GitHub 계정으로 로그인
- 또는 Personal Access Token 입력

---

### 방법 2: GitHub CLI 사용

#### GitHub CLI 설치 (아직 설치 안 된 경우)

```powershell
# Winget 사용
winget install --id GitHub.cli

# 또는 다운로드
# https://cli.github.com
```

#### 인증 및 저장소 생성

```powershell
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs

# GitHub 로그인
gh auth login

# 저장소 생성 및 푸시
gh repo create spring-boot-vuejs --public --source=. --push
```

---

### 방법 3: SSH 사용

#### SSH 키 설정 (처음 한 번만)

```powershell
# SSH 키 생성
ssh-keygen -t ed25519 -C "your_email@example.com"

# SSH 키 복사
Get-Content ~/.ssh/id_ed25519.pub | clip

# GitHub에 SSH 키 등록
# Settings → SSH and GPG keys → New SSH key
```

#### 저장소 연결

```powershell
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs

# SSH URL로 원격 저장소 추가
git remote add origin git@github.com:YOUR_USERNAME/spring-boot-vuejs.git

git branch -M main
git push -u origin main
```

---

## 🎯 빠른 실행 (방법 1 권장)

### 1. GitHub에서 저장소 생성
→ https://github.com/new

### 2. PowerShell 명령 복사 후 실행

```powershell
cd C:\Users\eh584\IdeaProjects\spring-boot-vuejs

# ⚠️ YOUR_USERNAME을 실제 GitHub 사용자명으로 변경!
git remote add origin https://github.com/YOUR_USERNAME/spring-boot-vuejs.git

git branch -M main

git push -u origin main
```

### 3. 인증
- 브라우저 창이 열리면 GitHub 로그인
- 또는 Personal Access Token 입력

---

## 🔑 Personal Access Token 만들기 (필요 시)

1. GitHub → **Settings** (프로필 메뉴)
2. 좌측 하단 **Developer settings**
3. **Personal access tokens** → **Tokens (classic)**
4. **Generate new token** → **Generate new token (classic)**
5. 설정:
   ```
   Note: spring-boot-vuejs
   Expiration: 90 days (또는 원하는 기간)
   Scopes: ✅ repo (전체 선택)
   ```
6. **Generate token** 클릭
7. 토큰 복사 (⚠️ 한 번만 표시됨!)
8. Git push 시 비밀번호로 이 토큰 사용

---

## ✅ 확인 방법

푸시 성공 후:

1. **GitHub 저장소 확인**
   ```
   https://github.com/YOUR_USERNAME/spring-boot-vuejs
   ```

2. **로컬에서 확인**
   ```powershell
   git remote -v
   git log --oneline
   ```

---

## 🆘 문제 해결

### "remote origin already exists" 오류

```powershell
# 기존 원격 저장소 제거
git remote remove origin

# 다시 추가
git remote add origin https://github.com/YOUR_USERNAME/spring-boot-vuejs.git
```

### 인증 실패

```powershell
# Git Credential Manager 재설정
git config --global credential.helper wincred

# 다시 푸시
git push -u origin main
```

### "failed to push some refs" 오류

```powershell
# 강제 푸시 (⚠️ 주의: 원격 저장소 내용 덮어쓰기)
git push -u origin main --force
```

---

## 📊 현재 프로젝트 정보

- **프로젝트명**: spring-boot-vuejs
- **패키지**: com.eunho.vuejs
- **기술스택**: Spring Boot 2.1.1, Vue.js 2.x, n8n
- **Git 상태**: 로컬 커밋 완료 ✅
- **다음 단계**: GitHub 연결 및 푸시

---

## 🎊 완료 후

GitHub에 성공적으로 푸시되면:

1. **저장소 확인**: https://github.com/YOUR_USERNAME/spring-boot-vuejs
2. **README.md 확인**: 프로젝트 설명 표시됨
3. **코드 브라우징**: 모든 소스 코드 확인 가능
4. **Clone 가능**: 다른 PC에서도 받을 수 있음

---

**지금 GitHub에서 새 저장소를 만들고 위의 명령어를 실행하세요!** 🚀

