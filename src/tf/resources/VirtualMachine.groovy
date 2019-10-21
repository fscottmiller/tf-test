package tf.resources

class VirtualMachine implements Serializable {
    String name;
    ResourceGroup resourceGroup;

    VirtualMachine(name, resourceGroup) {
        this.name = name;
        this.resourceGroup = resourceGroup;
    }
}