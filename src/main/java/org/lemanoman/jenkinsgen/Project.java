package org.lemanoman.jenkinsgen;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Project {
    private String name;
    private Map<String, String> variables;
    private String repo;
    private String branch;
    private List<ProjectPipelineDto> pipelines;

    public static Project load(File projectpipelineFileYml) throws FileNotFoundException, YAMLException {
        return new Project(projectpipelineFileYml);
    }

    private Project(File projectFileYml) throws FileNotFoundException, YAMLException {
        Yaml yaml = new Yaml();
        FileInputStream fis = new FileInputStream(projectFileYml);
        ProjectDto pipelineDto = yaml.loadAs(fis, ProjectDto.class);
        this.name = pipelineDto.getProject().getName();
        this.variables = loadVariables(pipelineDto);
        this.repo = pipelineDto.getProject().getRepo();
        this.branch = pipelineDto.getProject().getBranch();
        this.pipelines = pipelineDto.getPipelines();

    }

    private Map<String, String> loadVariables(ProjectDto projectDto) {
        HashMap<String, String> tempVars = new HashMap<>();

        if (projectDto == null || projectDto == null) {
            return new HashMap<>();
        }
        tempVars.put("project.name", projectDto.getProject().getName());
        tempVars.put("project.version", projectDto.getProject().getVersion());
        tempVars.put("project.branch", projectDto.getProject().getBranch());
        tempVars.put("project.repo", projectDto.getProject().getRepo());
        if (projectDto.getVariables() == null) {
            return tempVars;
        }
        for (Map<String, String> varMap : projectDto.getVariables()) {
            var setEntry = varMap.entrySet();
            for (var entry : setEntry) {
                tempVars.put(entry.getKey(), entry.getValue());
            }
        }
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


    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<ProjectPipelineDto> getPipelines() {
        return pipelines;
    }

    public void setPipelines(List<ProjectPipelineDto> pipelines) {
        this.pipelines = pipelines;
    }
}
