package tf.resources

class VirtualMachine implements Serializable {
    String name;
    String location;
    String size = 'D4s_v3';
    ResourceGroup resourceGroup;

    // VirtualMachine(Map data) {
    //     def keys = data.keySet()
    //     for (key in keys) { key = key.toLowerCase() }

    //     this.name = data['name']
    //     this.resourceGroup = data['resourceGroup']

    //     if (keys.contains('location')) {
    //         this.location = data['location']
    //     }
    // }

    VirtualMachine(name, resourceGroup) {
        this.name = name;
        this.resourceGroup = resourceGroup;
        this.location = resourceGroup.getLocation()
    }

    VirtualMachine(name, resourceGroup, size) {
        this.name = name;
        this.resourceGroup = resourceGroup;
        this.location = resourceGroup.getLocation()
        this.size = size
    }

    VirtualMachine(name, resourceGroup, size, location) {
        this.name = name;
        this.resourceGroup = resourceGroup;
        this.location = location
        this.size = size
    }

    public String getName() {
        return name;
    }

    public String getResourceGroup() {
        return resourceGroup
    }
}