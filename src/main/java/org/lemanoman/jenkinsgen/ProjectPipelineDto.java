package org.lemanoman.jenkinsgen;

import java.util.List;
import java.util.Map;

public class ProjectPipelineDto {
    private String name;

    private String template;

    private String outputPath;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
