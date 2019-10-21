import tf.yaml.Terraform

def call() {
    stage('Remove Resource') {
        // get resource group and type of element to be deleted
        def vals = input message: "Resource Details",
            parameters: [
                choice(name: 'Type', choices: Terraform.getTypes()),
                choice(name: 'Resource Group', choices: Terraform.getResourceGroups())
            ]

        // if element is not resource group
        if (vals['Type'] != 'Resource Group') {
            // get list of elements that could be deleted
            def parameters = Terraform.getResourceParameters(vals['Resource Group'], vals['Type'])
        
            // prompt user for which element to remove
            if (!parameters.getChoices().isEmpty()) {
                vals.putAll(input( message: "Which resource do you want to remove?", parameters: parameters))
            } 
        }
        
        // make keys lowercase for matching
        def resource = [:]
        for (key in vals.keySet()) {
            resource[key.toLowerCase()] = vals[key]
        }

        // remove resource
        Terraform.removeResource(resource)
    }
}