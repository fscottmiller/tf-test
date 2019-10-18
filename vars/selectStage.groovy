import tf.yaml.Terraform

def call(action) {
    echo "${action}"
    echo "${action.getClass()}"
    switch(action) {
        // case 'Create Resource Group':
        //     createResourceGroupStage()
        //     break
        case 'Create Resource':
            createResourceStage()
            break
        case 'Remove Resource':
            removeResourceStage()
            break
    }
}