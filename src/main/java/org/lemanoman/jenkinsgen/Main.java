package org.lemanoman.jenkinsgen;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String... args) {
        System.out.println("foi carai");
        new Main().run();
    }
    private Map<String, Schema> templates = null;
    private Map<String, Pipeline> pipelines = null;

    public Main() {

    }

    public void run() {
        this.templates = Loader.loadTemplates("./test-data/templates");
        this.pipelines = Loader.loadPipelines("./test-data/pipelines-schemas");

        final Pipeline pipeline = this.pipelines.get("videoviz");
        Schema schema = this.templates.get(pipeline.getTemplate());
        schema.fillTemplate(pipeline.getVariables(), pipeline.getOutputPath());
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
