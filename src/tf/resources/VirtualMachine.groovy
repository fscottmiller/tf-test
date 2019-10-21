package tf.resources

class VirtualMachine implements Serializable {
    String name;
    ResourceGroup resourceGroup;

    VirtualMachine(name, resourceGroup) {
        this.name = name;
        this.resourceGroup = resourceGroup;
    }

    public String getName() {
        return this.name;
    }

    public String getResourceGroup() {
        return this.resourceGroup
    }
}