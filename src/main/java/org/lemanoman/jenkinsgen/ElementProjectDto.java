package org.lemanoman.jenkinsgen;

import java.util.List;
import java.util.Map;

public class ElementProjectDto {
    private String name;
    private String repo;
    private String version;
    private String branch;



    private List<Map<String,String>> variables;

    public List<Map<String, String>> getVariables() {
        return variables;
    }

    public void setVariables(List<Map<String, String>> variables) {
        this.variables = variables;
    }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getRepo() { return repo; }
    public void setRepo(String value) { this.repo = value; }

    public String getVersion() { return version; }
    public void setVersion(String value) { this.version = value; }


    public String getBranch() { return branch; }
    public void setBranch(String value) { this.branch = value; }

}