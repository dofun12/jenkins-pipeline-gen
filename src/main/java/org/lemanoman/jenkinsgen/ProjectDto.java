package org.lemanoman.jenkinsgen;

import java.util.List;
import java.util.Map;

public class ProjectDto {
    private ElementProjectDto project;
    private List<ProjectPipelineDto> pipelines;
    private List<Map<String,String>> variables;

    public ElementProjectDto getProject() {
        return project;
    }

    public void setProject(ElementProjectDto project) {
        this.project = project;
    }

    public List<ProjectPipelineDto> getPipelines() {
        return pipelines;
    }

    public void setPipelines(List<ProjectPipelineDto> pipelines) {
        this.pipelines = pipelines;
    }

    public List<Map<String, String>> getVariables() {
        return variables;
    }

    public void setVariables(List<Map<String, String>> variables) {
        this.variables = variables;
    }
}