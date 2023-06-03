package org.lemanoman.jenkinsgen;

import java.util.List;
import java.util.Map;

public class PipelineDto {
    private ElementProjectDto project;
    private String template;

    private String outputPath;
    private List<Map<String,String>> variables;

    public ElementProjectDto getProject() {
        return project;
    }

    public void setProject(ElementProjectDto project) {
        this.project = project;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<Map<String, String>> getVariables() {
        return variables;
    }

    public void setVariables(List<Map<String, String>> variables) {
        this.variables = variables;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
}
