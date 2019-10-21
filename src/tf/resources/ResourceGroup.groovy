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

    public static void addVirtualMachine(VirtualMachine vm) {
        this.virtualMachines += vm
    }

    public static VirtualMachine[] getVirtualMachines() {
        return this.virtualMachines
    }
}