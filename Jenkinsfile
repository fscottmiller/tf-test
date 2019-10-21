@Library('tf@stage') _
import tf.yaml.Terraform

pipeline {
    agent any

    parameters {
        choice name: 'Action', choices: ['Create Resource', 'Remove Resource']
    }

    environment {
        FILE = "tf.yml"
        repository = 'https://github.com/fscottmiller/tf-test'
        ARTIFACTORY_URL = 'http://192.168.0.27:8081/artifactory'
    }

    stages {
        stage('Initialization') {
            steps {
                cleanWs()
                git env.repository
                echo "${env.ARTIFACTORY_URL}/${env.FILE}"
                script {
                    Terraform.setConfig(env.ARTIFACTORY_URL, env.FILE, this)
                }
                selectStage(params.Action)
            }
        }
        stage('Confirmation') {
            steps {
                input message: "${Terraform.getYaml()}\nAre you sure you want to continue?"
                script {
                    sh "curl -u admin:password -T <(echo '${Terraform.getYaml()}') http://${env.ARTIFACTORY_URL}/Terraform-YAML/${env.FILE}"
                }
            }
        }
    }
}