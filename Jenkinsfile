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
        ARTIFACTORY_URL = 'http://localhost:8081/artifactory'
    }

    stages {
        stage('Initialization') {
            steps {
                cleanWs()
                git env.repository
                script {
                    Terraform.setConfig(env.ARTIFACTORY_URL, env.FILE)
                }
                selectStage(params.Action)
            }
        }
        stage('Confirmation') {
            steps {
                input message: "${Terraform.getYaml()}\nAre you sure you want to continue?"
                script {
                    println Terraform.publish(env.ARTIFACTORY_URL, env.FILE)
                    // sh "curl -u admin:password -T <(echo \"${Terraform.getYaml()}\") http://localhost:8081/artifactory/Terraform-YAML/tf.yml"
                }
            }
        }
    }
}