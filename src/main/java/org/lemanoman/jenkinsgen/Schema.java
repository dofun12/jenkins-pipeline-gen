package org.lemanoman.jenkinsgen;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Schema {
    private Map<String, String> variables;
    private File templateFile;
    private String name;

    public Schema(File schemaYMLFile){
        variables = new HashMap<>();
        Yaml yaml = new Yaml();
        try {
            FileInputStream fis = new FileInputStream(schemaYMLFile);
            Map<String, Object> obj = yaml.load(fis);
            List<Map<String, Object>> baseVariables = (List<Map<String, Object>>)obj.get("variables");
            for(Map<String, Object> var: baseVariables){
                for(Map.Entry<String, Object> entry: var.entrySet()){
                    String varName = entry.getKey();
                    Map<String,String> varValueMap = (Map<String, String>)entry.getValue();
                    String defaultValue = varValueMap.get("default");
                    if(defaultValue.equals("none")){
                        defaultValue = "";
                    }
                    variables.put(varName, defaultValue);
                }
            }
            String mainTemplate = (String) obj.get("main-template");
            this.templateFile = new File(schemaYMLFile.getParentFile(), mainTemplate);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void fillTemplate(Map<String,String> appVariables){
        List<String> lines = FileUtils.readFile(this.templateFile.getAbsolutePath());
        StringBuilder builder = new StringBuilder();
        for(String line: lines){
            if(line==null || "".equals(line)){
                builder.append(line);
                builder.append("\n");
                continue;
            }
            VariableParser parser = VariableParser.parse(line);
            if(parser.isHasParameter()){
                String key = parser.getParameter();
                String value = "";
                if(variables.containsKey(key)){
                    value = variables.get(key);
                    System.out.println("${"+key+"} -> "+value);
                }
                if(appVariables.containsKey(key)){
                    value = appVariables.get(key);
                    System.out.println("${"+key+"} -> "+value);
                }

                VariableParser valueParser = VariableParser.parse(value);
                if(valueParser.isHasParameter()){
                    value = valueParser.replace(appVariables.get(valueParser.getParameter()));
                }
                line = parser.replace(value);
            }
            builder.append(line);
            builder.append("\n");
        }
        System.out.println("=========================================");
        System.out.println(builder);
    }
}
