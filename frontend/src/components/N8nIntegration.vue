<template>
  <div class="n8n-integration">
    <h2>n8n 워크플로우 통합</h2>

    <div class="card mt-4">
      <div class="card-header">
        <h4>워크플로우 트리거</h4>
      </div>
      <div class="card-body">
        <div class="form-group">
          <label for="eventType">이벤트 타입:</label>
          <select v-model="eventType" class="form-control" id="eventType">
            <option value="user.created">사용자 생성</option>
            <option value="user.updated">사용자 업데이트</option>
            <option value="notification">알림</option>
            <option value="custom">커스텀</option>
          </select>
        </div>

        <div class="form-group mt-3">
          <label for="workflowData">데이터 (JSON):</label>
          <textarea
            v-model="workflowData"
            class="form-control"
            id="workflowData"
            rows="5"
            placeholder='{"key": "value"}'
          ></textarea>
        </div>

        <button @click="triggerWorkflow" class="btn btn-primary mt-3">
          워크플로우 실행
        </button>

        <div v-if="loading" class="mt-3">
          <div class="spinner-border text-primary" role="status">
            <span class="sr-only">Loading...</span>
          </div>
        </div>

        <div v-if="result" class="alert mt-3" :class="resultClass">
          <h5>결과:</h5>
          <pre>{{ JSON.stringify(result, null, 2) }}</pre>
        </div>
      </div>
    </div>

    <div class="card mt-4">
      <div class="card-header">
        <h4>n8n 연동 상태</h4>
      </div>
      <div class="card-body">
        <button @click="checkHealth" class="btn btn-info">
          상태 확인
        </button>

        <div v-if="healthStatus" class="mt-3">
          <span class="badge" :class="healthBadgeClass">
            {{ healthStatus.status }}
          </span>
          <p class="mt-2">서비스: {{ healthStatus.service }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

const API_URL = 'http://localhost:8088/api/n8n'

export default {
  name: 'N8nIntegration',
  data() {
    return {
      eventType: 'user.created',
      workflowData: '{\n  "message": "Hello from Vue.js",\n  "timestamp": ' + Date.now() + '\n}',
      result: null,
      loading: false,
      healthStatus: null
    }
  },
  computed: {
    resultClass() {
      if (!this.result) return ''
      return this.result.success ? 'alert-success' : 'alert-danger'
    },
    healthBadgeClass() {
      if (!this.healthStatus) return ''
      return this.healthStatus.status === 'UP' ? 'badge-success' : 'badge-danger'
    }
  },
  methods: {
    async triggerWorkflow() {
      this.loading = true
      this.result = null

      try {
        let data
        try {
          data = JSON.parse(this.workflowData)
        } catch (e) {
          this.result = {
            success: false,
            message: 'Invalid JSON format: ' + e.message
          }
          this.loading = false
          return
        }

        data.eventType = this.eventType

        const response = await axios.post(`${API_URL}/trigger`, data)
        this.result = response.data

      } catch (error) {
        console.error('Error triggering workflow:', error)
        this.result = {
          success: false,
          message: (error.response && error.response.data && error.response.data.message) || error.message
        }
      } finally {
        this.loading = false
      }
    },

    async checkHealth() {
      try {
        const response = await axios.get(`${API_URL}/health`)
        this.healthStatus = response.data
      } catch (error) {
        console.error('Error checking health:', error)
        this.healthStatus = {
          status: 'DOWN',
          service: 'n8n-integration',
          error: error.message
        }
      }
    }
  },
  mounted() {
    this.checkHealth()
  }
}
</script>

<style scoped>
.n8n-integration {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.card {
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.card-header {
  background-color: #f8f9fa;
  border-bottom: 2px solid #dee2e6;
}

pre {
  background-color: #f5f5f5;
  padding: 10px;
  border-radius: 4px;
  overflow-x: auto;
}

.badge {
  font-size: 1rem;
  padding: 0.5rem 1rem;
}

.badge-success {
  background-color: #28a745;
}

.badge-danger {
  background-color: #dc3545;
}
</style>

