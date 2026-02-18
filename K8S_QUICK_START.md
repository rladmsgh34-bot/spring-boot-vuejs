# 🚀 Kubernetes 빠른 시작 가이드

## 1단계: Docker Desktop Kubernetes 활성화

1. Docker Desktop 실행
2. 설정 → Kubernetes → "Enable Kubernetes" 체크
3. Apply & Restart

## 2단계: Docker 이미지 빌드

```powershell
# 프로젝트 루트에서 실행
cd C:\Users\eunho\IdeaProjects\spring-boot-vuejs

# Maven 빌드
mvn clean package -DskipTests

# Docker 이미지 빌드
docker build -t spring-boot-vuejs:latest .
```

## 3단계: Kubernetes 배포

```powershell
# PowerShell 스크립트로 간단 배포
.\k8s-deploy.ps1 -Action install
```

또는 수동으로:

```powershell
# 모든 리소스 한 번에 배포
kubectl apply -f k8s/
```

## 4단계: 애플리케이션 접근

```powershell
# Port Forward로 접근
kubectl port-forward svc/spring-boot-vuejs-service -n spring-boot-vuejs 8088:8088

# 브라우저에서 http://localhost:8088 접근
```

## 유용한 명령어

```powershell
# 상태 확인
.\k8s-deploy.ps1 -Action status

# 로그 확인
.\k8s-deploy.ps1 -Action logs

# 재시작
.\k8s-deploy.ps1 -Action restart

# 삭제
.\k8s-deploy.ps1 -Action uninstall
```

## 자세한 내용

전체 가이드는 [KUBERNETES_SETUP.md](KUBERNETES_SETUP.md)를 참조하세요.

