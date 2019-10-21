package tf.resources

class ResourceGroup implements Serializable {
    VirtualMachine[] virtualMachines;
    String name;
    String location;

    ResourceGroup(name, location) {
        this.name = name
        this.location = location
        this.virtualMachines = []
    }

    public VirtualMachine[] getVirtualMachines() {
        return this.virtualMachines
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public void addVirtualMachine(VirtualMachine vm) {
        this.virtualMachines += vm
    }
    
}