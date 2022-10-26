package org.lemanoman.jenkinsgen;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class Pipeline {
    private String name;

    private String outputPath;
    private Map<String, String> variables;
    private String template;

    public static Pipeline load(File pipelineFileYml){
        return new Pipeline(pipelineFileYml);
    }
    private Pipeline(File pipelineFileYml) {
        Yaml yaml = new Yaml();
        try {
            FileInputStream fis = new FileInputStream(pipelineFileYml);
            PipelineDto pipelineDto = yaml.loadAs(fis, PipelineDto.class);
            this.name = pipelineDto.getProject().getName();
            this.template = pipelineDto.getTemplate();
            this.variables = loadVariables(pipelineDto);
            this.outputPath = pipelineDto.getOutputPath();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private Map<String, String> loadVariables(PipelineDto pipelineDto){
        HashMap<String, String> tempVars = new HashMap<>();

        for(Map<String,String> varMap: pipelineDto.getVariables()){
            var setEntry = varMap.entrySet();
            for(var entry: setEntry){
                tempVars.put(entry.getKey(), entry.getValue());
            }
        }
        tempVars.put("project.name", pipelineDto.getProject().getName());
        tempVars.put("project.version", pipelineDto.getProject().getVersion());
        tempVars.put("project.branch", pipelineDto.getProject().getBranch());
        tempVars.put("project.repo", pipelineDto.getProject().getRepo());
        return tempVars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public String getTemplate() {
        return template;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
