import tf.yaml.Terraform

def call() {
    stage('Create Resource Group') {
        def input = input message: "Resource Group Details",
            parameters: [
                string(name: 'Name'),
                string(name: 'Location')
            ]
        def resource = [:]
        resource['type'] = 'resource group'
        for (key in input.keySet()) {
            resource[key.toLowerCase()] = input[key]
        }
        Terraform.addResource(resource)
    }
}