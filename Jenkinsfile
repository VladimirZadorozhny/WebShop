pipeline {
  agent any

  environment {
    SPRING_DATASOURCE_URL      = "jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}"
    SPRING_DATASOURCE_USERNAME = "${DB_USER}"
    SPRING_DATASOURCE_PASSWORD = "${DB_PASSWORD}"
  }

  parameters {
    string(name: 'DB_HOST',     defaultValue: 'webshopdb', description: 'DB hostname')
    string(name: 'DB_PORT',     defaultValue: '3306',      description: 'DB port')
    string(name: 'DB_NAME',     defaultValue: 'webshop',   description: 'DB name')
    string(name: 'DB_USER',     defaultValue: 'root',      description: 'DB user')
    password(name: 'DB_PASSWORD', defaultValue: 'vdab',    description: 'DB password')
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }
    stage('Build & Test') {
      steps {
        withMaven(maven: 'M3', jdk: 'JDK21') {
          sh './mvnw clean verify'
        }
      }
    }
  }

  post {
    always { junit '**/target/surefire-reports/*.xml' }
    success { echo '✅ Build & tests passed!' }
    failure { echo '❌ Build failed – debug logs above!' }
  }
}
