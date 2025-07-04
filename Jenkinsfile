pipeline {
  agent any

  environment {
    DB_HOST = 'webshopdb'
    DB_PORT = '3306'
    DB_NAME = 'webshop'
    DB_USER = 'root'
    DB_PASSWORD = 'vdab'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }
    stage('Build & Test') {
      steps {
        sh './mvnw clean verify -Dspring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME} ' +
          '-Dspring.datasource.username=${DB_USER} ' +
           '-Dspring.datasource.password=${DB_PASSWORD}'
      }
    }
  }
  post {
    always {
      junit '**/target/surefire-reports/*.xml'
    }
    success {
      echo 'Build & tests passed!'
    }
  }
}
