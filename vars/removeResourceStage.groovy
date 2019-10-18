import tf.yaml.Terraform

def call() {
    stage('Remove Resource') {
        // def types = Terraform.getTypes()
        def vals = input message: "Resource Details",
            parameters: [
                choice(name: 'Type', choices: Terraform.getTypes()),
                choice(name: 'Resource Group', choices: Terraform.getResourceGroups())
            ]

        print "${vals}"
        if (vals['Type'] != 'resource group') {
            def parameters = Terraform.getResourceParameters(vals['Resource Group'], vals['Type'])
        
            println "checking params..."
            if (!parameters.isEmpty()) {
                vals.putAll(input( message: "Which resource do you want to remove?", parameters: parameters))
            } 
        }
        
        
        println "lowercasing everything..."
        def resource = [:]
        for (key in vals.keySet()) {
            resource[key.toLowerCase()] = vals[key]
        }

        println "removing..."
        println "${vals}"
        Terraform.removeResource(resource)
    }
}