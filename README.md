# spring-boot-vuejs

## Setup Vue.js & Spring Boot

### Prerequisites

#### MacOSX

```
brew install node
npm install -g @vue/cli
```

#### Linux

```
sudo apt update
sudo apt install node
npm install -g @vue/cli
```

#### Windows

```
choco install npm
npm install -g @vue/cli
```

## Project setup

```
spring-boot-vuejs
├─┬ backend     → backend module with Spring Boot code
│ ├── src
│ └── pom.xml
├─┬ frontend    → frontend module with Vue.js code
│ ├── src
│ └── pom.xml
└── pom.xml     → Maven parent pom managing both modules
```

## Backend

Spring Boot backend with REST API endpoints and n8n integration.

### n8n Integration

This project includes n8n workflow automation integration.

#### Configuration

Edit `backend/src/main/resources/application.properties`:

```properties
# n8n Integration Configuration
n8n.enabled=true
n8n.webhook-url=http://localhost:5678/webhook/your-webhook-id
n8n.api-url=http://localhost:5678/api/v1
n8n.api-key=your-api-key-here
```

#### Available Endpoints

- `POST /api/n8n/webhook` - Receive webhooks from n8n
- `POST /api/n8n/trigger` - Trigger default n8n workflow
- `POST /api/n8n/trigger-custom?webhookUrl=<url>` - Trigger custom n8n workflow
- `GET /api/n8n/health` - Check n8n integration status

#### How to Setup n8n

1. Install n8n locally:
```bash
npm install -g n8n
```

2. Run n8n:
```bash
n8n start
```

3. Access n8n at http://localhost:5678

4. Create a workflow with a Webhook node:
   - Add a Webhook trigger node
   - Set the Webhook URL path (e.g., `/webhook/user-events`)
   - Copy the webhook URL and update `application.properties`

5. Test the integration:
   - Go to http://localhost:8088/n8n in your browser
   - Or create a user via the API - it will automatically trigger n8n workflow


## Frontend

Vue.js frontend with n8n integration UI.


## First App run

Inside the root directory, do a: 

```
mvn clean install
```

Run our complete Spring Boot App:

```
mvn --projects backend spring-boot:run
```

Now go to http://localhost:8088/ and have a look at your first Vue.js Spring Boot App.



## Faster feedback with webpack-dev-server

```
npm run serve
```

