package org.lemanoman.jenkinsgen;

import org.yaml.snakeyaml.Yaml;

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

    public Main() {

    }

    public void run() {
        loadTemplates();
    }
    public void parseYML(File file){

    }
    public void loadTemplates(){
        List<File> files = getFiles(new File("C:\\Users\\kvnsu\\IdeaProjects\\jenkins-pipeline-gen\\src\\main\\resources\\test-data\\templates"), ".yml");
        for(File file: files){
            Schema schema = new Schema(file);
            Map<String, String> appVars = new HashMap<>();
            appVars.put("env.BRANCH", "master");
            appVars.put("project_name", "meuprojeto");
            appVars.put("env.PROJECT_NAME", "${project_name}");
            appVars.put("env.STAGE_ONE", "OPAaaaa carai");
            schema.fillTemplate(appVars);

        }
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
