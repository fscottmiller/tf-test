import tf.yaml.Terraform

def call(action) {
    switch(action) {
        case 'Create Resource':
            createResourceStage()
            break
        case 'Remove Resource':
            removeResourceStage()
            break
    }
}