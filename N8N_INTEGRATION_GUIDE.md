# n8n 통합 가이드

## 개요

이 프로젝트는 n8n 워크플로우 자동화 도구와 완전히 통합되어 있습니다.

## 구현된 기능

### 1. Backend (Spring Boot)

#### 새로운 컴포넌트
- **N8nConfig.java** - n8n 설정 관리
- **N8nService.java / N8nServiceImpl.java** - n8n 워크플로우 트리거 및 웹훅 처리
- **N8nWebhookController.java** - n8n REST API 엔드포인트
- **N8nWebhookRequest/Response.java** - DTO 클래스

#### REST API 엔드포인트

1. **웹훅 수신**: `POST /api/n8n/webhook`
   ```json
   {
     "workflowId": "workflow-123",
     "eventType": "user.created",
     "data": { "key": "value" },
     "timestamp": 1234567890
   }
   ```

2. **워크플로우 트리거**: `POST /api/n8n/trigger`
   ```json
   {
     "event": "custom_event",
     "data": { "key": "value" }
   }
   ```

3. **커스텀 워크플로우**: `POST /api/n8n/trigger-custom?webhookUrl=<url>`
   
4. **헬스체크**: `GET /api/n8n/health`

#### 자동 통합
- 사용자 생성 시 자동으로 n8n 워크플로우 트리거
- UserController에 통합됨

### 2. Frontend (Vue.js)

#### 새로운 컴포넌트
- **N8nIntegration.vue** - n8n 워크플로우 관리 UI

#### 기능
- 워크플로우 수동 트리거
- 이벤트 타입 선택 (user.created, user.updated, notification, custom)
- JSON 데이터 입력
- n8n 연동 상태 확인
- 실시간 결과 표시

## 설정 방법

### 1. n8n 설치 및 실행

```bash
# n8n 설치
npm install -g n8n

# n8n 실행
n8n start
```

### 2. n8n 워크플로우 생성

1. 브라우저에서 http://localhost:5678 접속
2. 새 워크플로우 생성
3. **Webhook** 노드 추가:
   - HTTP Method: POST
   - Path: `user-events` (또는 원하는 이름)
4. 워크플로우 활성화
5. Webhook URL 복사 (예: `http://localhost:5678/webhook/abc123xyz`)

### 3. Spring Boot 설정

`backend/src/main/resources/application.properties` 파일 수정:

```properties
n8n.enabled=true
n8n.webhook-url=http://localhost:5678/webhook/abc123xyz
n8n.api-url=http://localhost:5678/api/v1
n8n.api-key=your-api-key-here  # (선택사항)
```

### 4. 애플리케이션 실행

```bash
# 빌드
mvn clean install -DskipTests

# 실행
mvn --projects backend spring-boot:run
```

## 사용 예제

### 예제 1: 사용자 생성 시 자동 알림

1. n8n에서 워크플로우 생성:
   - Webhook 노드 (트리거)
   - Set 노드 (데이터 가공)
   - Email/Slack/Discord 노드 (알림 전송)

2. Spring Boot에서 사용자 생성:
   ```bash
   curl -X POST http://localhost:8088/api/user \
     -H "Content-Type: application/json" \
     -d '{"firstName":"John","lastName":"Doe"}'
   ```

3. 자동으로 n8n 워크플로우가 트리거되고 알림이 전송됩니다.

### 예제 2: 수동 워크플로우 트리거

1. 브라우저에서 http://localhost:8088/n8n 접속
2. 이벤트 타입 선택
3. JSON 데이터 입력
4. "워크플로우 실행" 버튼 클릭

### 예제 3: API를 통한 커스텀 워크플로우

```bash
curl -X POST 'http://localhost:8088/api/n8n/trigger-custom?webhookUrl=http://localhost:5678/webhook/custom123' \
  -H "Content-Type: application/json" \
  -d '{
    "event": "custom_event",
    "data": {
      "message": "Hello from API"
    }
  }'
```

## 워크플로우 예제

### 1. 사용자 환영 이메일

```
Webhook → Filter (event=user.created) → Gmail (Send Email)
```

### 2. Slack 알림

```
Webhook → Set (데이터 포맷) → Slack (Post Message)
```

### 3. 데이터베이스 동기화

```
Webhook → HTTP Request → PostgreSQL (Insert)
```

### 4. 복잡한 비즈니스 로직

```
Webhook → IF (조건 분기) → Function (JS 로직) → Multiple Actions
```

## 보안 고려사항

1. **API Key 사용**: 프로덕션 환경에서는 반드시 API Key 설정
2. **HTTPS 사용**: 실제 환경에서는 HTTPS 프로토콜 사용
3. **Webhook 검증**: 웹훅 요청의 출처 확인
4. **Rate Limiting**: 과도한 요청 방지

## 트러블슈팅

### n8n이 응답하지 않는 경우
```bash
# n8n 상태 확인
curl http://localhost:5678/healthz

# n8n 재시작
n8n stop
n8n start
```

### 워크플로우가 트리거되지 않는 경우
1. n8n 워크플로우가 활성화되어 있는지 확인
2. Webhook URL이 정확한지 확인
3. application.properties의 설정 확인
4. 백엔드 로그 확인

### CORS 오류가 발생하는 경우
- WebMvcConfig.java에 CORS 설정이 이미 추가되어 있습니다.
- 필요시 allowedOrigins 수정

## 추가 리소스

- [n8n 공식 문서](https://docs.n8n.io/)
- [n8n Webhook 가이드](https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.webhook/)
- [Spring Boot RestTemplate](https://spring.io/guides/gs/consuming-rest/)

## 라이센스

이 프로젝트는 원본 프로젝트의 라이센스를 따릅니다.

