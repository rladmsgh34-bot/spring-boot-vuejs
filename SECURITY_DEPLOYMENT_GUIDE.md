# 보안 및 배포 설정 가이드

## 개요
이 문서는 프로젝트의 보안 및 배포를 위한 필수 설정 사항을 설명합니다.

---

## 1. 민감 정보 관리 (Kubernetes Secrets)

### 문제점
- Kubernetes Secret 파일 (`k8s/secret.yaml`)에는 API 키, 데이터베이스 비밀번호 등 민감한 정보가 포함됩니다.
- 이러한 정보는 Git에 커밋되어서는 안 됩니다.

### 해결 방법

#### Step 1: Secret 파일 생성
템플릿 파일을 복사하여 실제 Secret 파일을 생성합니다:

```bash
# Windows PowerShell
Copy-Item k8s\secret.yaml.template k8s\secret.yaml
```

#### Step 2: 실제 값으로 교체
`k8s/secret.yaml` 파일을 열어 다음 플레이스홀더를 실제 값으로 교체합니다:
- `YOUR_N8N_API_KEY_HERE` → 실제 N8N API 키
- `YOUR_DB_USERNAME_HERE` → 실제 데이터베이스 사용자명
- `YOUR_SECURE_DB_PASSWORD_HERE` → 강력한 데이터베이스 비밀번호

#### Step 3: Secret 배포
```bash
kubectl apply -f k8s/secret.yaml
```

### 중요 사항
- ✅ `k8s/secret.yaml`은 `.gitignore`에 추가되어 있어 Git에 커밋되지 않습니다.
- ✅ `k8s/secret.yaml.template`만 Git에 커밋됩니다.
- ⚠️ **절대 실제 Secret 값을 Git에 커밋하지 마세요!**

### 고급 보안 옵션
더 높은 보안이 필요한 경우 다음 도구를 고려하세요:
- **HashiCorp Vault**: 엔터프라이즈급 Secret 관리
- **AWS Secrets Manager**: AWS 환경에서 사용
- **Azure Key Vault**: Azure 환경에서 사용
- **Google Secret Manager**: GCP 환경에서 사용
- **Sealed Secrets**: Git에 안전하게 암호화된 Secret 저장

---

## 2. Docker 이미지 레지스트리 설정

### 문제점
`k8s/deployment.yaml`과 `k8s/kustomization.yaml`에 `your-registry` 플레이스홀더가 포함되어 있습니다.

### 해결 방법

#### 수정 대상 파일
1. **k8s/deployment.yaml**
   ```yaml
   # 변경 전
   image: your-registry/spring-boot-vuejs:latest
   
   # 변경 후 (Docker Hub 예시)
   image: docker.io/yourusername/spring-boot-vuejs:latest
   ```

2. **k8s/kustomization.yaml**
   ```yaml
   # 변경 전
   images:
     - name: your-registry/spring-boot-vuejs
       newName: your-registry/spring-boot-vuejs
   
   # 변경 후 (Docker Hub 예시)
   images:
     - name: your-registry/spring-boot-vuejs
       newName: docker.io/yourusername/spring-boot-vuejs
   ```

#### 주요 레지스트리 형식
- **Docker Hub**: `docker.io/username/image-name`
- **AWS ECR**: `123456789012.dkr.ecr.region.amazonaws.com/image-name`
- **Google GCR**: `gcr.io/project-id/image-name`
- **Azure ACR**: `yourregistry.azurecr.io/image-name`

#### 이미지 빌드 및 푸시
```powershell
# 1. Docker 이미지 빌드
docker build -t yourusername/spring-boot-vuejs:latest .

# 2. 레지스트리에 푸시
docker push yourusername/spring-boot-vuejs:latest
```

---

## 3. 환경별 설정 분리

### 설정 우선순위
```
Kubernetes ConfigMap/Secret (최우선)
  ↓
application-production.properties (프로덕션)
  ↓
application.properties (개발 환경 기본값)
```

### 개발 환경
- 파일: `backend/src/main/resources/application.properties`
- 용도: 로컬 개발 시 기본 설정
- N8N 기본값: `n8n.enabled=false`

### 프로덕션 환경 (Kubernetes)
- 파일: `k8s/configmap.yaml`, `k8s/secret.yaml`
- 용도: Kubernetes 클러스터에서 실행 시 적용
- 설정: 환경 변수로 주입되어 애플리케이션 설정을 덮어씀

### N8N 통합 설정 예시
N8N을 활성화하려면 `k8s/configmap.yaml`을 수정합니다:

```yaml
# N8N Integration
N8N_ENABLED: "true"  # false에서 true로 변경
N8N_WEBHOOK_URL: "http://n8n-service:5678/webhook/your-actual-webhook-id"
N8N_API_URL: "http://n8n-service:5678/api/v1"
```

그리고 `k8s/secret.yaml`에 실제 API 키를 설정합니다:
```yaml
N8N_API_KEY: "your-actual-n8n-api-key"
```

---

## 4. 로깅 레벨 설정

### 프로덕션 환경 권장 사항
`k8s/configmap.yaml`에서 로깅 레벨을 설정합니다:

```yaml
# 일반 운영 시
LOGGING_LEVEL_ROOT: "INFO"
LOGGING_LEVEL_COM_EUNHO: "INFO"

# 문제 발생 시 일시적으로 변경
LOGGING_LEVEL_COM_EUNHO: "DEBUG"  # 문제 해결 후 다시 INFO로 변경
```

### 주의 사항
- ⚠️ DEBUG 레벨은 성능 저하, 스토리지 비용 증가, 민감 정보 노출 위험이 있습니다.
- ✅ 프로덕션에서는 기본적으로 INFO 또는 WARN을 사용하세요.
- ✅ 필요 시에만 일시적으로 DEBUG로 변경하고, 문제 해결 후 즉시 복원하세요.

---

## 5. Actuator 헬스체크 설정

### Kubernetes 프로브 설정
`k8s/configmap.yaml`에서 Actuator 엔드포인트를 설정합니다:

```yaml
MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE: "health,info,metrics,prometheus"
MANAGEMENT_ENDPOINT_HEALTH_PROBES_ENABLED: "true"
MANAGEMENT_HEALTH_LIVENESSSTATE_ENABLED: "true"
MANAGEMENT_HEALTH_READINESSSTATE_ENABLED: "true"
```

### Deployment 프로브
`k8s/deployment.yaml`에는 다음 프로브가 설정되어 있습니다:

- **Liveness Probe**: `/actuator/health/liveness` (Pod 재시작 여부 결정)
- **Readiness Probe**: `/actuator/health/readiness` (트래픽 라우팅 여부 결정)

---

## 6. 배포 전 체크리스트

배포하기 전에 다음 사항을 확인하세요:

- [ ] `k8s/secret.yaml` 파일 생성 및 실제 값 설정
- [ ] `k8s/deployment.yaml`의 이미지 레지스트리 경로 수정
- [ ] `k8s/kustomization.yaml`의 이미지 레지스트리 경로 수정
- [ ] Docker 이미지 빌드 및 레지스트리에 푸시
- [ ] N8N 사용 시 ConfigMap에서 `N8N_ENABLED: "true"` 설정
- [ ] N8N Webhook URL 및 API URL 실제 값으로 수정
- [ ] 로깅 레벨이 프로덕션에 적합한지 확인 (INFO 또는 WARN)
- [ ] Ingress 호스트명 설정 (`k8s/ingress.yaml`)
- [ ] TLS 인증서 설정 (필요한 경우)

---

## 7. 배포 명령어

### Secret 먼저 배포 (한 번만)
```powershell
kubectl apply -f k8s/secret.yaml
```

### 전체 애플리케이션 배포
```powershell
# Kustomize를 사용한 배포
kubectl apply -k k8s/

# 또는 개별 파일 배포
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
kubectl apply -f k8s/ingress.yaml
kubectl apply -f k8s/hpa.yaml
```

### 배포 확인
```powershell
# Pod 상태 확인
kubectl get pods -n spring-boot-vuejs

# 서비스 확인
kubectl get svc -n spring-boot-vuejs

# 로그 확인
kubectl logs -n spring-boot-vuejs -l app=spring-boot-vuejs --tail=50
```

---

## 8. 문제 해결

### ImagePullBackOff 오류
- **원인**: 이미지 레지스트리 경로가 잘못되었거나 이미지가 존재하지 않음
- **해결**: 이미지 경로 확인 및 레지스트리에 이미지가 푸시되었는지 확인

### CrashLoopBackOff 오류
- **원인**: 애플리케이션이 시작 중 오류 발생
- **해결**: `kubectl logs` 명령으로 로그 확인

### Secret 관련 오류
- **원인**: Secret이 생성되지 않았거나 잘못된 키 사용
- **해결**: `kubectl get secret -n spring-boot-vuejs`로 Secret 확인

---

## 9. CI/CD 통합 (선택 사항)

### GitHub Actions 예시
Secret을 CI/CD 파이프라인에서 주입하는 예시:

```yaml
- name: Deploy to Kubernetes
  env:
    N8N_API_KEY: ${{ secrets.N8N_API_KEY }}
    DB_PASSWORD: ${{ secrets.DB_PASSWORD }}
  run: |
    kubectl create secret generic spring-boot-vuejs-secret \
      --from-literal=N8N_API_KEY=$N8N_API_KEY \
      --from-literal=DB_PASSWORD=$DB_PASSWORD \
      --namespace=spring-boot-vuejs \
      --dry-run=client -o yaml | kubectl apply -f -
```

---

## 참고 자료
- [Kubernetes Secrets 문서](https://kubernetes.io/docs/concepts/configuration/secret/)
- [Spring Boot Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config)
- [Kubernetes Best Practices](https://kubernetes.io/docs/concepts/configuration/overview/)

