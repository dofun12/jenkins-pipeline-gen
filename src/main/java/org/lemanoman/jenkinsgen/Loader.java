package org.lemanoman.jenkinsgen;

import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loader {

    public static Map<String, Pipeline> loadPipelines(String pipelinesPath) {
        HashMap<String, Pipeline> tmpPipelines = new HashMap<>();
        List<File> files = getFiles(new File(pipelinesPath), ".yml");
        for (File file : files) {
            Pipeline pipeline = null;
            try {
                pipeline = Pipeline.load(file);
            }catch (YAMLException e){
                continue;
            }catch (FileNotFoundException fnf){
                fnf.printStackTrace();
                continue;
            }
            tmpPipelines.put(pipeline.getName(), pipeline);
        }
        return tmpPipelines;

    }

    public static Map<String, Project> loadProjects(String pipelinesPath) {
        HashMap<String, Project> tmpPipelines = new HashMap<>();
        List<File> files = getFiles(new File(pipelinesPath), ".yml");
        for (File file : files) {
            Project pipeline = null;
            try {
                pipeline = Project.load(file);
            }catch (YAMLException e){
                continue;
            }catch (FileNotFoundException fnf){
                fnf.printStackTrace();
                continue;
            }
            tmpPipelines.put(pipeline.getName(), pipeline);
        }
        return tmpPipelines;

    }
    public static Pipeline loadPipeline(String pipelinePath){
        if(pipelinePath==null){
            return null;
        }
        File pipelineFile = new File(pipelinePath);
        if(!pipelineFile.exists() || !pipelineFile.isFile()){
            return null;
        }
        try {
            return Pipeline.load(pipelineFile);
        } catch (FileNotFoundException|YAMLException e) {
            return null;
        }
    }

    public static Project loadProject(String projectPath){
        if(projectPath==null){
            return null;
        }
        File pipelineFile = new File(projectPath);
        if(!pipelineFile.exists() || !pipelineFile.isFile()){
            return null;
        }
        try {
            return Project.load(pipelineFile);
        } catch (FileNotFoundException|YAMLException e) {
            return null;
        }
    }

    public static Map<String, Schema> loadTemplates(String templatesPath) {
        Map<String, Schema> schemaMap = new HashMap<>();
        File dir = new File(templatesPath);
        System.out.println("Loading schemas from..."+dir.getAbsolutePath());
        List<File> files = getFiles(dir, ".yml");
        for (File file : files) {
            final Schema schema = loadTemplate(file);
            schemaMap.put(schema.getName(), schema);
        }
        return schemaMap;
    }
    public static Schema loadTemplate(File templateFile) {
        if(!templateFile.exists()){
            return null;
        }
        if(templateFile.isFile()){
            return new Schema(templateFile);
        }
        File[] subFiles = templateFile.listFiles();
        if(subFiles==null){
            return null;
        }

        for(File file: subFiles){
            if(file.isFile() && file.getName().endsWith(".yml")){
                return new Schema(file);
            }
        }
        return null;
    }
    public static Schema loadTemplate(String templatesPath) {
        if(templatesPath==null){
            return null;
        }
        File templateFile = new File(templatesPath);
        return loadTemplate(templateFile);
    }

    private static ArrayList<File> getFiles(File dir, String suffix) {
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
}
