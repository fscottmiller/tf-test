package tf.yaml

@Grab(group='org.yaml', module='snakeyaml', version='1.13') 
import groovy.json.*
import org.yaml.snakeyaml.Yaml
import hudson.model.*
import java.util.*

class Terraform implements Serializable {
    static HashMap resourceGroups = [:]
    static HashMap tags = [:]
    static HashMap data = [
        'virtual machine' : [
            'Size' : [
                'alias': 'vm_size',
                'choices': ['D2_v2', 'D4_v2'],
                ], 
            'Name' : [
                'alias': 'vm_name',
            ],
            'Data Disks': [
                'alias': 'data_disk',
            ],
            'Resource Group': [
                'alias': 'resource group'
            ]
        ],
        'resource group' : [
            'Name' : [ 'alias': 'name'],
            'Location' : [ 'alias': 'location']
        ]
    ]

    public static void setConfig(artifactory, file, j) {
        j.echo "in function set config"
        def text = ['curl', '-u', 'admin:password', "${artifactory}/Terraform-YAML/${file}"].execute().text
        j.echo "text: ${text}"
        def yaml = new Yaml().load(text)
        resourceGroups = yaml['resource groups']
        tags = yaml['tags']
    }

    public static ArrayList getTypes() {
        return data.keySet().toList()
    }

    public static ArrayList getResourceParameters(resourceGroup, type) {
        def parameters = []
        for (resource in resourceGroup[resourceGroup]['resources']) {
            if (resource['type'] == type) {
                parameters += new StringParameterDefinition(resource.key, "")
            }
        }
        return parameters
    }

    public static ArrayList getParameters(resource,j) {
        j.echo "----------------------------"
        j.echo "${resourceGroups.keySet()}"
        def parameters = []
        String[] groups = resourceGroups.keySet().toList()
        j.echo " ---------- a ----------------"
        data[resource].each { key, value ->
            j.echo "in loop"
            j.echo "${key} : ${value}"
            if (key.toLowerCase() != 'name' && key.toLowerCase() != 'location'  && key.toLowerCase() != 'resource group') {
                if (value.keySet().contains('choices')) {
                    parameters += new ChoiceParameterDefinition(key, (String[]) value['choices'], "")
                } else {
                    if (value.keySet().contains('defaultValue')) {
                        parameters += new StringParameterDefinition(key, value['defaultValue'], "")
                    } else {
                        parameters += new StringParameterDefinition(key, "")
                    }
                }
            }
            if (key.toLowerCase() == 'resource group') {
                j.echo "setting rg..."
                
                j.echo "${groups}"
                parameters += new ChoiceParameterDefinition("Resource Group", groups, "")
            }
        }
        j.echo " ------------ z -----------------"
        return parameters
    }
    
    public static ArrayList getResourceGroups() {
        return new ArrayList<>(resourceGroups.keySet())
    }

    public static String getJson() {
        def config = [:]
        config['resource groups'] = resourceGroups
        config['tags'] = tags
        return JsonOutput.toJson(resourceGroups)
    }

    public static String getYaml() {
        def config = [:]
        config['resource groups'] = resourceGroups
        config['tags'] = tags
        return new Yaml().dump(config)
    }

    public static void addResource(resource) {
        resource = cleanKeys(resource)
        if (resource['type'] == 'resource group') {
            addResourceGroup(resource)
        } else {
            def name = resource.remove('resource group')
            if (!resourceGroups[name].keySet().contains('resources')) {
                resourceGroups[name]['resources'] = []
            }
            resourceGroups[name]['resources'] += resource
        }
    }

    private static HashMap cleanKeys(data) {
        def cleaned = [:]
        for (key in data.keySet().toList()) {
            cleaned[key.toLowerCase()] = data[key]
        }
        return cleaned
    }

    private static void addResourceGroup(resourceGroup) {
        resourceGroup.remove('type')
        def name = resourceGroup.remove('name')
        // resourceGroup['resources'] = []
        resourceGroups[name] = resourceGroup
    }

    public static void removeResource(resource) {
        if (resource['type'] == 'resource group') {
            // removeResourceGroup(resource)
            resourceGroups.remove(resource['resource group'])
        } else {
            for (element in resourceGroups[resource['resource group']]['resources']){
                if (element['name'] == resource['name'] && element['type'] == resource['type']) {
                    resourceGroups[resource['resource group']]['resources'].remove(element)
                    break
                }
            }
        }
    }

    private static void removeResourceGroup(resourceGroup) {
        resourceGroups.remove(resourceGroup['name'])
    }
}