package org.lemanoman.jenkinsgen;

import java.util.List;
import java.util.Map;

public class PipelineDto {
    private ProjectDto project;
    private String template;
    private List<Map<String,String>> variables;

    public ProjectDto getProject() {
        return project;
    }

    public void setProject(ProjectDto project) {
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
}
