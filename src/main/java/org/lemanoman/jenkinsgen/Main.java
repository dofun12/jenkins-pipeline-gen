package org.lemanoman.jenkinsgen;

import org.apache.commons.cli.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    private String templatePath;
    private String projectPath;

    private String outputPath;

    public static void main(String... args) {
        try {
            Main main = new Main(args);
            main.run();
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    private Map<String, Schema> templates = null;
    private Map<String, Pipeline> pipelines = null;

    private Map<String, Project> projects = null;

    public Main(String... args) throws ParseException {
        Options options = new Options();
        options.addOption("u", false, "Update templates")
                .addRequiredOption("t", "template-path", true, "Set the templates path")
                .addRequiredOption("p", "project-path", true, "Set the projects path")
                .addOption("o", "output-path", true, "Set the projects out path");

        CommandLineParser parser = new DefaultParser();
        CommandLine line = parser.parse(options, args);
        String templatePath = line.getOptionValue("t");
        String projectsPath = line.getOptionValue("p");

        outputPath = ".";
        if(line.hasOption("o")){
             outputPath = line.getOptionValue("o");
        }

        if (templatePath == null || projectsPath == null) {
            throw new ParseException("template path and project path is required");
        }
        File templateFile = new File(templatePath);
        if (!templateFile.exists()) {
            throw new ParseException("template path " + templatePath + " not exist");
        }

        if (!templateFile.exists()) {
            throw new ParseException("template path " + templatePath + " not exist");
        }

        File projectFile = new File(projectsPath);
        if (!projectFile.exists()) {
            throw new ParseException("project path " + templatePath + " not exist");
        }

        if (!templateFile.exists()) {
            throw new ParseException("template path " + templatePath + " not exist");
        }

        this.templatePath = templatePath;
        this.projectPath = projectsPath;

    }


    public void run() {
        //"./test-data/templates"
        this.templates = Loader.loadTemplates(this.templatePath);
        this.pipelines = Loader.loadPipelines(this.projectPath);
        this.projects = Loader.loadProjects(this.projectPath);

        if (this.templates == null) {
            System.out.println("No templates found");
            return;
        }

        for (Map.Entry<String, Pipeline> entry : pipelines.entrySet()) {
            final Pipeline pipeline = entry.getValue();
            if (pipeline == null) {
                continue;
            }
            if (pipeline.getTemplate() == null || pipeline.getTemplate().isEmpty()) {
                continue;
            }
            if (pipeline.getOutputPath() == null || pipeline.getOutputPath().isEmpty()) {
                continue;
            }
            Schema schema = templates.get(pipeline.getTemplate());
            if (schema == null) {
                continue;
            }
            schema.fillTemplate(pipeline.getVariables(), pipeline.getOutputPath(), outputPath);
        }

        for (Map.Entry<String, Project> entry : projects.entrySet()) {
            final Project project = entry.getValue();
            if (project == null) {
                continue;
            }
            if(project.getPipelines()==null || project.getPipelines().isEmpty()){
                continue;
            }
            for (ProjectPipelineDto pipeline : project.getPipelines()) {

                if (pipeline.getTemplate() == null || pipeline.getTemplate().isEmpty()) {
                    continue;
                }
                if (pipeline.getOutputPath() == null || pipeline.getOutputPath().isEmpty()) {
                    continue;
                }
                Schema schema = templates.get(pipeline.getTemplate());
                if (schema == null) {
                    continue;
                }
                schema.fillTemplate(project.getVariables(), pipeline.getOutputPath(), outputPath);
            }
        }
    }


    private List<String> readFile(String filepath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                lines.add(line);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lines;

    }
}
