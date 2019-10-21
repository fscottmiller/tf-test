import tf.yaml.Terraform
import hudson.model.StringParameterDefinition

// known bug: if only one param in additional params request, things will break
def call() {
    stage('Create Resource') {
        // get basic details on element to create
        def vals = input message: "Resource Details",
            parameters: [
                string(name: 'Name'),
                string(name: 'Location', defaultValue: 'East US 2'),
                choice(name: 'Type', choices: Terraform.getTypes()),
            ]
        
        // get any additional parameters needed
        echo "getting params"
        echo "${vals}"
        def parameters = Terraform.getParameters(vals['Type'], this)
        
        // prompt user with additional parameters
        echo "prompting"
        if (!parameters.isEmpty()) {
            vals.putAll(input( message: "Additional Details", parameters: parameters))
        }

        // add resource
        echo "adding"

        Terraform.addResource(vals)
    }
}