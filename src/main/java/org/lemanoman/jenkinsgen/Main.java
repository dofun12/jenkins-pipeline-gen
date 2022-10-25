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
        this.templates = loadTemplates("./test-data/templates");
        this.pipelines = loadPipelines("./test-data/pipelines");

        final Pipeline pipeline = this.pipelines.get("videoviz");
        Schema schema = this.templates.get(pipeline.getTemplate());
        schema.fillTemplate(pipeline.getVariables());
    }

    public void parseYML(File file){

    }

    private Map<String, Pipeline> loadPipelines(String pipelinesPath){
        HashMap<String, Pipeline> tmpPipelines = new HashMap<>();
        List<File> files = getFiles(new File(pipelinesPath), ".yml");
        for(File file: files){
            final Pipeline pipeline = Pipeline.load(file);
            tmpPipelines.put(pipeline.getName(), pipeline);
        }
        return tmpPipelines;

    }

    public Map<String, Schema> loadTemplates(String templatesPath){
        Map<String, Schema> schemaMap = new HashMap<>();
        List<File> files = getFiles(new File(templatesPath), ".yml");
        for(File file: files){
            final Schema schema = new Schema(file);
            schemaMap.put(schema.getName(), schema);
        }
        return schemaMap;
    }

    private ArrayList<File> getFiles(File dir, String suffix) {
        ArrayList<File> files = new ArrayList<>();
        if (dir == null || dir.listFiles() == null) {
            return files;
        }
        for (File subFile : dir.listFiles()) {
            if (subFile.isDirectory()) {
                files.addAll(getFiles(subFile, suffix));
            }
            if (!subFile.getName().endsWith(suffix)) {
                continue;
            }
            files.add(subFile);
        }
        return files;
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
