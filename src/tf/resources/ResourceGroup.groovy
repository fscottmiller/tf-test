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
        return virtualMachines
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void addVirtualMachine(VirtualMachine vm) {
        virtualMachines += vm
    }

}