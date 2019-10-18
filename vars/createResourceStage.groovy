import tf.yaml.Terraform
import hudson.model.StringParameterDefinition

// known bug: if only one param in additional params request, things will break
def call() {
    stage('Create Resource') {
        def vals = input message: "Resource Details",
            parameters: [
                string(name: 'Name'),
                string(name: 'Location', defaultValue: 'East US 2'),
                choice(name: 'Type', choices: Terraform.getTypes()),
            ]
        
        def parameters = Terraform.getParameters(vals['Type'])
        
        if (!parameters.isEmpty()) {
            vals.putAll(input( message: "Additional Details", parameters: parameters))
        }

        Terraform.addResource(vals)
    }
}