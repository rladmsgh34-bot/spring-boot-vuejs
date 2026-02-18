# Kubernetes 설정 가이드

## 📋 목차
- [개요](#개요)
- [사전 요구사항](#사전-요구사항)
- [로컬 개발 환경 설정](#로컬-개발-환경-설정)
- [Docker 이미지 빌드](#docker-이미지-빌드)
- [Kubernetes 배포](#kubernetes-배포)
- [설정 커스터마이징](#설정-커스터마이징)
- [문제 해결](#문제-해결)

## 개요

이 프로젝트는 Spring Boot 백엔드와 Vue.js 프론트엔드를 포함한 풀스택 애플리케이션을 Kubernetes에 배포하기 위한 완전한 설정을 제공합니다.

### 포함된 Kubernetes 리소스

- **Namespace**: 애플리케이션 격리
- **ConfigMap**: 환경 설정
- **Secret**: 민감한 정보 관리
- **Deployment**: 애플리케이션 배포 및 관리
- **Service**: 내부 네트워크 통신
- **Ingress**: 외부 접근 라우팅
- **HorizontalPodAutoscaler**: 자동 스케일링

## 사전 요구사항

### 필수 도구 설치

1. **Docker Desktop** (Windows)
   ```powershell
   # Chocolatey를 사용하여 설치
   choco install docker-desktop
   ```
   또는 [Docker Desktop 공식 사이트](https://www.docker.com/products/docker-desktop)에서 다운로드

2. **kubectl** (Kubernetes CLI)
   ```powershell
   # Chocolatey를 사용하여 설치
   choco install kubernetes-cli
   ```

3. **로컬 Kubernetes 클러스터** (다음 중 하나 선택)
   - **Docker Desktop Kubernetes** (권장 - Windows)
     - Docker Desktop 설정에서 Kubernetes 활성화
   - **Minikube**
     ```powershell
     choco install minikube
     minikube start
     ```
   - **Kind** (Kubernetes in Docker)
     ```powershell
     choco install kind
     kind create cluster --name spring-boot-vuejs
     ```

4. **선택사항: Helm** (패키지 관리자)
   ```powershell
   choco install kubernetes-helm
   ```

## 로컬 개발 환경 설정

### 1. Docker Desktop Kubernetes 활성화

1. Docker Desktop 실행
2. 설정(Settings) → Kubernetes
3. "Enable Kubernetes" 체크
4. "Apply & Restart"

### 2. kubectl 설정 확인

```powershell
# 클러스터 정보 확인
kubectl cluster-info

# 현재 컨텍스트 확인
kubectl config current-context

# 노드 확인
kubectl get nodes
```

## Docker 이미지 빌드

### 방법 1: 로컬 빌드 (Docker Desktop 사용)

```powershell
# 1. 프로젝트 루트 디렉토리로 이동
cd C:\Users\eunho\IdeaProjects\spring-boot-vuejs

# 2. Maven으로 애플리케이션 빌드
mvn clean package -DskipTests

# 3. Docker 이미지 빌드
docker build -t spring-boot-vuejs:latest .

# 4. 이미지 확인
docker images | Select-String "spring-boot-vuejs"

# 5. 로컬 테스트 (선택사항)
docker run -p 8088:8088 spring-boot-vuejs:latest
```

### 방법 2: Docker Compose로 테스트

```powershell
# Docker Compose로 빌드 및 실행
docker-compose -f docker-compose.k8s.yaml up --build

# 백그라운드 실행
docker-compose -f docker-compose.k8s.yaml up -d

# 로그 확인
docker-compose -f docker-compose.k8s.yaml logs -f

# 중지
docker-compose -f docker-compose.k8s.yaml down
```

### 방법 3: 레지스트리에 푸시 (프로덕션)

```powershell
# Docker Hub 예시
docker tag spring-boot-vuejs:latest yourusername/spring-boot-vuejs:latest
docker push yourusername/spring-boot-vuejs:latest

# 또는 프라이빗 레지스트리
docker tag spring-boot-vuejs:latest registry.example.com/spring-boot-vuejs:latest
docker push registry.example.com/spring-boot-vuejs:latest
```

## Kubernetes 배포

### 빠른 배포 (PowerShell 스크립트 사용)

```powershell
# 1. 스크립트 실행 권한 설정
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser

# 2. 애플리케이션 배포
.\k8s-deploy.ps1 -Action install

# 3. 상태 확인
.\k8s-deploy.ps1 -Action status

# 4. 로그 확인
.\k8s-deploy.ps1 -Action logs

# 5. 재시작
.\k8s-deploy.ps1 -Action restart

# 6. 삭제
.\k8s-deploy.ps1 -Action uninstall
```

### 수동 배포

```powershell
# 1. Namespace 생성
kubectl apply -f k8s/namespace.yaml

# 2. ConfigMap과 Secret 생성
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secret.yaml

# 3. Deployment 생성
kubectl apply -f k8s/deployment.yaml

# 4. Service 생성
kubectl apply -f k8s/service.yaml

# 5. Ingress 생성
kubectl apply -f k8s/ingress.yaml

# 6. HPA 생성 (선택사항)
kubectl apply -f k8s/hpa.yaml

# 또는 한 번에 모두 적용
kubectl apply -f k8s/
```

### Kustomize 사용 (권장)

```powershell
# Kustomize로 배포
kubectl apply -k k8s/

# 특정 환경 오버레이 사용 (추가 설정 필요)
kubectl apply -k k8s/overlays/production/
```

## 애플리케이션 접근

### 로컬 개발 환경

#### 방법 1: Port Forward

```powershell
# Service를 로컬 포트로 포워딩
kubectl port-forward svc/spring-boot-vuejs-service -n spring-boot-vuejs 8088:8088

# 브라우저에서 접근
# http://localhost:8088
```

#### 방법 2: Ingress 사용

1. Ingress Controller 설치 (NGINX)
   ```powershell
   # NGINX Ingress Controller 설치
   kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.8.2/deploy/static/provider/cloud/deploy.yaml
   
   # 설치 확인
   kubectl get pods -n ingress-nginx
   ```

2. hosts 파일 수정
   ```powershell
   # 관리자 권한으로 PowerShell 실행
   notepad C:\Windows\System32\drivers\etc\hosts
   
   # 다음 줄 추가
   127.0.0.1 spring-boot-vuejs.local
   ```

3. Ingress IP 확인 및 접근
   ```powershell
   kubectl get ingress -n spring-boot-vuejs
   
   # 브라우저에서 접근
   # http://spring-boot-vuejs.local
   ```

## 모니터링 및 관리

### 상태 확인

```powershell
# 모든 리소스 확인
kubectl get all -n spring-boot-vuejs

# Pod 상태 확인
kubectl get pods -n spring-boot-vuejs

# Pod 상세 정보
kubectl describe pod <pod-name> -n spring-boot-vuejs

# 로그 확인
kubectl logs -f <pod-name> -n spring-boot-vuejs

# 실시간 리소스 모니터링
kubectl top pods -n spring-boot-vuejs
kubectl top nodes
```

### 스케일링

```powershell
# 수동 스케일링
kubectl scale deployment/spring-boot-vuejs -n spring-boot-vuejs --replicas=3

# HPA 상태 확인
kubectl get hpa -n spring-boot-vuejs

# HPA 상세 정보
kubectl describe hpa spring-boot-vuejs-hpa -n spring-boot-vuejs
```

### 업데이트 및 롤백

```powershell
# 새 이미지로 업데이트
kubectl set image deployment/spring-boot-vuejs -n spring-boot-vuejs spring-boot-vuejs=your-registry/spring-boot-vuejs:v2

# 롤아웃 상태 확인
kubectl rollout status deployment/spring-boot-vuejs -n spring-boot-vuejs

# 롤아웃 이력 확인
kubectl rollout history deployment/spring-boot-vuejs -n spring-boot-vuejs

# 이전 버전으로 롤백
kubectl rollout undo deployment/spring-boot-vuejs -n spring-boot-vuejs

# 특정 리비전으로 롤백
kubectl rollout undo deployment/spring-boot-vuejs -n spring-boot-vuejs --to-revision=2
```

## 설정 커스터마이징

### 1. 이미지 레지스트리 변경

**k8s/deployment.yaml** 편집:
```yaml
spec:
  containers:
  - name: spring-boot-vuejs
    image: your-registry/spring-boot-vuejs:latest  # 여기를 수정
```

### 2. 도메인 변경

**k8s/ingress.yaml** 편집:
```yaml
spec:
  rules:
  - host: your-domain.com  # 여기를 수정
```

### 3. 환경 변수 추가

**k8s/configmap.yaml** 편집:
```yaml
data:
  YOUR_NEW_CONFIG: "value"
```

### 4. 리소스 제한 조정

**k8s/deployment.yaml** 편집:
```yaml
resources:
  requests:
    memory: "512Mi"
    cpu: "250m"
  limits:
    memory: "1Gi"
    cpu: "1000m"
```

### 5. 레플리카 수 조정

**k8s/deployment.yaml** 편집:
```yaml
spec:
  replicas: 3  # 원하는 수로 변경
```

## 프로덕션 배포 체크리스트

- [ ] Docker 이미지를 레지스트리에 푸시
- [ ] k8s/deployment.yaml에서 이미지 이름 업데이트
- [ ] k8s/secret.yaml에서 실제 시크릿 값 설정
- [ ] k8s/configmap.yaml에서 프로덕션 설정 확인
- [ ] k8s/ingress.yaml에서 실제 도메인 설정
- [ ] TLS/SSL 인증서 설정 (HTTPS)
- [ ] 리소스 제한 및 요청 값 조정
- [ ] HPA 메트릭 및 임계값 조정
- [ ] 모니터링 및 로깅 설정 (Prometheus, Grafana)
- [ ] 백업 및 재해 복구 계획 수립

## 문제 해결

### 이미지 Pull 오류

```powershell
# Private 레지스트리 시크릿 생성
kubectl create secret docker-registry regcred `
  --docker-server=<your-registry-server> `
  --docker-username=<your-username> `
  --docker-password=<your-password> `
  --docker-email=<your-email> `
  -n spring-boot-vuejs

# deployment.yaml에 imagePullSecrets 추가 필요
```

### Pod가 시작되지 않음

```powershell
# Pod 상태 확인
kubectl get pods -n spring-boot-vuejs

# Pod 이벤트 확인
kubectl describe pod <pod-name> -n spring-boot-vuejs

# Pod 로그 확인
kubectl logs <pod-name> -n spring-boot-vuejs

# 이전 컨테이너 로그 확인 (CrashLoopBackOff)
kubectl logs <pod-name> -n spring-boot-vuejs --previous
```

### Ingress가 작동하지 않음

```powershell
# Ingress Controller 확인
kubectl get pods -n ingress-nginx

# Ingress 이벤트 확인
kubectl describe ingress spring-boot-vuejs-ingress -n spring-boot-vuejs

# Ingress Controller 로그 확인
kubectl logs -n ingress-nginx -l app.kubernetes.io/name=ingress-nginx
```

### 메트릭 서버 설치 (HPA 작동 안 함)

```powershell
# Metrics Server 설치
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

# 로컬 개발 환경에서는 TLS 비활성화 필요할 수 있음
kubectl patch deployment metrics-server -n kube-system --type='json' -p='[{"op":"add","path":"/spec/template/spec/containers/0/args/-","value":"--kubelet-insecure-tls"}]'

# 메트릭 확인
kubectl top nodes
kubectl top pods -n spring-boot-vuejs
```

## 유용한 명령어

```powershell
# Namespace의 모든 리소스 삭제
kubectl delete namespace spring-boot-vuejs

# 특정 리소스 강제 삭제
kubectl delete pod <pod-name> -n spring-boot-vuejs --force --grace-period=0

# ConfigMap 및 Secret 업데이트 후 Pod 재시작
kubectl rollout restart deployment/spring-boot-vuejs -n spring-boot-vuejs

# Shell 접근 (디버깅)
kubectl exec -it <pod-name> -n spring-boot-vuejs -- /bin/sh

# 리소스 사용량 확인
kubectl top pods -n spring-boot-vuejs
kubectl top nodes

# 이벤트 확인
kubectl get events -n spring-boot-vuejs --sort-by='.lastTimestamp'
```

## 추가 리소스

- [Kubernetes 공식 문서](https://kubernetes.io/docs/)
- [kubectl 치트 시트](https://kubernetes.io/docs/reference/kubectl/cheatsheet/)
- [Docker 공식 문서](https://docs.docker.com/)
- [Helm 차트](https://helm.sh/)
- [Skaffold](https://skaffold.dev/)

## 지원

문제가 발생하면 다음을 확인하세요:
1. Pod 로그: `kubectl logs -f <pod-name> -n spring-boot-vuejs`
2. Pod 이벤트: `kubectl describe pod <pod-name> -n spring-boot-vuejs`
3. Deployment 상태: `kubectl get deployment -n spring-boot-vuejs`
4. Service 엔드포인트: `kubectl get endpoints -n spring-boot-vuejs`

