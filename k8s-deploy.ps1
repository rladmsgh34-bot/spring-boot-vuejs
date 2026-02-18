# Kubernetes Deployment Script for Spring Boot Vue.js Application
# PowerShell script for Windows

param(
    [Parameter(Mandatory=$false)]
    [ValidateSet('install', 'uninstall', 'restart', 'status', 'logs')]
    [string]$Action = 'install',

    [Parameter(Mandatory=$false)]
    [string]$Namespace = 'spring-boot-vuejs',

    [Parameter(Mandatory=$false)]
    [string]$ImageTag = 'latest'
)

$ErrorActionPreference = "Stop"

# Colors for output
function Write-ColorOutput {
    param(
        [string]$Message,
        [string]$Color = "White"
    )
    Write-Host $Message -ForegroundColor $Color
}

# Check if kubectl is installed
function Test-Kubectl {
    try {
        kubectl version --client --short 2>&1 | Out-Null
        return $true
    } catch {
        return $false
    }
}

# Check if cluster is accessible
function Test-KubernetesCluster {
    try {
        kubectl cluster-info 2>&1 | Out-Null
        return $true
    } catch {
        return $false
    }
}

# Install application to Kubernetes
function Install-Application {
    Write-ColorOutput "🚀 Installing Spring Boot Vue.js application to Kubernetes..." "Cyan"

    # Apply namespace first
    Write-ColorOutput "Creating namespace..." "Yellow"
    kubectl apply -f k8s/namespace.yaml

    # Apply configurations
    Write-ColorOutput "Applying ConfigMaps and Secrets..." "Yellow"
    kubectl apply -f k8s/configmap.yaml
    kubectl apply -f k8s/secret.yaml

    # Apply deployment and service
    Write-ColorOutput "Deploying application..." "Yellow"
    kubectl apply -f k8s/deployment.yaml
    kubectl apply -f k8s/service.yaml

    # Apply ingress
    Write-ColorOutput "Setting up Ingress..." "Yellow"
    kubectl apply -f k8s/ingress.yaml

    # Apply HPA
    Write-ColorOutput "Setting up Horizontal Pod Autoscaler..." "Yellow"
    kubectl apply -f k8s/hpa.yaml

    Write-ColorOutput "`n✅ Application installed successfully!" "Green"
    Write-ColorOutput "`nWaiting for pods to be ready..." "Yellow"
    kubectl wait --for=condition=ready pod -l app=spring-boot-vuejs -n $Namespace --timeout=300s

    Write-ColorOutput "`n📊 Deployment Status:" "Cyan"
    kubectl get all -n $Namespace
}

# Uninstall application from Kubernetes
function Uninstall-Application {
    Write-ColorOutput "🗑️  Uninstalling Spring Boot Vue.js application..." "Cyan"

    kubectl delete -f k8s/ --ignore-not-found=true

    Write-ColorOutput "`n✅ Application uninstalled successfully!" "Green"
}

# Restart application
function Restart-Application {
    Write-ColorOutput "🔄 Restarting application..." "Cyan"

    kubectl rollout restart deployment/spring-boot-vuejs -n $Namespace
    kubectl rollout status deployment/spring-boot-vuejs -n $Namespace

    Write-ColorOutput "`n✅ Application restarted successfully!" "Green"
}

# Show application status
function Show-Status {
    Write-ColorOutput "📊 Application Status:" "Cyan"

    Write-ColorOutput "`nPods:" "Yellow"
    kubectl get pods -n $Namespace -l app=spring-boot-vuejs

    Write-ColorOutput "`nServices:" "Yellow"
    kubectl get svc -n $Namespace

    Write-ColorOutput "`nIngress:" "Yellow"
    kubectl get ingress -n $Namespace

    Write-ColorOutput "`nHPA:" "Yellow"
    kubectl get hpa -n $Namespace

    Write-ColorOutput "`nDeployment:" "Yellow"
    kubectl describe deployment spring-boot-vuejs -n $Namespace
}

# Show application logs
function Show-Logs {
    Write-ColorOutput "📜 Application Logs:" "Cyan"

    $pods = kubectl get pods -n $Namespace -l app=spring-boot-vuejs -o jsonpath='{.items[*].metadata.name}'

    if ($pods) {
        $podArray = $pods -split ' '
        Write-ColorOutput "`nShowing logs from pod: $($podArray[0])" "Yellow"
        kubectl logs -f $podArray[0] -n $Namespace
    } else {
        Write-ColorOutput "No pods found!" "Red"
    }
}

# Main script
Write-ColorOutput "==================================" "Cyan"
Write-ColorOutput "Kubernetes Deployment Manager" "Cyan"
Write-ColorOutput "==================================" "Cyan"

# Check prerequisites
if (-not (Test-Kubectl)) {
    Write-ColorOutput "❌ kubectl is not installed or not in PATH!" "Red"
    Write-ColorOutput "Please install kubectl: https://kubernetes.io/docs/tasks/tools/" "Yellow"
    exit 1
}

if (-not (Test-KubernetesCluster)) {
    Write-ColorOutput "❌ Cannot connect to Kubernetes cluster!" "Red"
    Write-ColorOutput "Please check your kubeconfig and cluster connection." "Yellow"
    exit 1
}

Write-ColorOutput "✅ kubectl is installed" "Green"
Write-ColorOutput "✅ Kubernetes cluster is accessible" "Green"
Write-ColorOutput ""

# Execute action
switch ($Action.ToLower()) {
    'install' {
        Install-Application
    }
    'uninstall' {
        Uninstall-Application
    }
    'restart' {
        Restart-Application
    }
    'status' {
        Show-Status
    }
    'logs' {
        Show-Logs
    }
    default {
        Write-ColorOutput "Unknown action: $Action" "Red"
        Write-ColorOutput "Valid actions: install, uninstall, restart, status, logs" "Yellow"
        exit 1
    }
}

Write-ColorOutput "`n==================================" "Cyan"
Write-ColorOutput "Operation completed!" "Cyan"
Write-ColorOutput "==================================" "Cyan"

