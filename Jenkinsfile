@Library('tf@classes') _
import tf.yaml.Terraform
import tf.resources.*

pipeline {
    agent any

    // parameters {
    //     choice name: 'Action', choices: ['Create Resource', 'Remove Resource']
    // }

    // environment {
    //     FILE = "tf.yml"
    //     repository = 'https://github.com/fscottmiller/tf-test'
    //     ARTIFACTORY_URL = 'http://192.168.0.27:8081/artifactory'
    // }

    stages {
        stage('test') {
            steps {
                script {
                    def rg = new ResourceGroup("rg", "east us")
                    def vm = new VirtualMachine("vm", rg)
                    def vm2 = new VirtualMachine("vm2", rg, "D2_v2")
                    def vm3 = new VirtualMachine("vm3", rg, "D4s_v3", "East US 4")

                    println rg.getName()
                    println vm.getName()
                    println rg.getLocation()
                    println vm3.getLocation()
                }
            }
        }
        // stage('Initialization') {
        //     steps {
        //         cleanWs()
        //         git env.repository
        //         echo "${env.ARTIFACTORY_URL}/${env.FILE}"
        //         script {
        //             Terraform.setConfig(env.ARTIFACTORY_URL, env.FILE, this)
        //         }
        //         selectStage(params.Action)
        //     }
        // }
        // stage('Confirmation') {
        //     steps {
        //         input message: "${Terraform.getYaml()}\nAre you sure you want to continue?"
        //         script {
        //             sh "curl --version"
        //             sh "rm ${env.FILE}"
        //             echo "${Terraform.getYaml()}"
        //             writeYaml file: "${env.FILE}", data: Terraform.getConfig()
        //             sh "curl -u admin:password -T tf.yml ${env.ARTIFACTORY_URL}/Terraform-YAML/${env.FILE}"
        //         }
        //     }
        // }
    }
}