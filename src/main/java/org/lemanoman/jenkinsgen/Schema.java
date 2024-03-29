package org.lemanoman.jenkinsgen;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Schema {
    private Map<String, String> variables;
    private final File templateFile;

    private final File basePath;

    private String name;

    public Schema(File schemaYMLFile) {
        variables = new HashMap<>();
        Yaml yaml = new Yaml();
        try {
            FileInputStream fis = new FileInputStream(schemaYMLFile);
            final Map<String, Object> obj = yaml.load(fis);
            this.basePath = schemaYMLFile.getParentFile();
            this.name = (String) obj.get("name");
            loadVariables(obj, "local");
            loadVariables(obj, "global");
            String mainTemplate = (String) obj.get("main-template");
            this.templateFile = new File(schemaYMLFile.getParentFile(), mainTemplate);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSubfile(String file) {
        File subYamlFile = new File(this.basePath, file);
        if (!subYamlFile.exists()) {
            return;
        }

        Yaml yaml = new Yaml();
        try {
            FileInputStream fis = new FileInputStream(subYamlFile);
            final Map<String, Object> obj = yaml.load(fis);
            loadVariablesFromMapObject(obj, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parseSingleStringVariable(String value) {
        if (!value.startsWith("file://")) {
            return;
        }
        String path = value.replaceAll("file://", "").trim();
        loadSubfile(path);
    }

    private void loadVariables(Map<String, Object> obj, String scope) {
        Object variableNode = obj.get(scope);
        if (variableNode == null) {
            return;
        }
        if (variableNode instanceof String) {
            parseSingleStringVariable((String) variableNode);
            return;
        }
        loadVariablesFromMapObject(obj, scope);
    }

    private void loadVariablesFromMapObject(Map<String, Object> obj, String scope) {
        Map<String, Object> localVars = null;
        if (scope != null) {
            localVars = (Map<String, Object>) obj.get(scope);
        } else {
            localVars = obj;
        }
        List<Map<String, Object>> baseVariables = (List<Map<String, Object>>) localVars.get("variables");
        for (Map<String, Object> var : baseVariables) {
            for (Map.Entry<String, Object> entry : var.entrySet()) {
                String varName = entry.getKey();
                Map<String, String> varValueMap = (Map<String, String>) entry.getValue();
                String defaultValue = varValueMap.get("default");
                if (defaultValue.equals("none")) {
                    defaultValue = "";
                }
                variables.put(varName, defaultValue);
            }
        }
    }

    private void writeToFile(String out, File outputFile) {
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] strToBytes = out.getBytes();
            fos.write(strToBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void fillTemplate(Map<String, String> appVariables, String outputPath) {
        fillTemplate(appVariables, outputPath, basePath.getParentFile().getAbsolutePath());
    }

    public void fillTemplate(Map<String, String> appVariables, String outputPath, String baseOutput) {
        String out = fillTemplate(appVariables);
        if (outputPath == null) {
            return;
        }
        File childDir = new File(baseOutput, outputPath).getParentFile();
        childDir.mkdirs();

        File outfile = new File(baseOutput, outputPath);


        System.out.println("=========================================");
        System.out.println(out);
        writeToFile(out, outfile);


    }

    public String fillTemplate(Map<String, String> appVariables) {
        List<String> lines = FileUtils.readFile(this.templateFile.getAbsolutePath());
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            if (line == null || "".equals(line)) {
                builder.append(line);
                builder.append(System.lineSeparator());
                continue;
            }

            ParseResult parseResult;
            do {
                VariableParser parser = VariableParser.parse(line);
                parseResult = parseParameter(appVariables, line, parser);
                line = parseResult.getLine();
            } while (parseResult.isParameterExists());


            builder.append(line);
            builder.append(System.lineSeparator());
        }
        return builder.toString();

    }

    private ParseResult parseParameter(Map<String, String> appVariables, String line, VariableParser parser) {
        boolean exists = false;
        if (parser.isHasParameter()) {
            String key = parser.getParameter();
            String value = "";
            if (variables.containsKey(key)) {
                value = variables.get(key);
                //System.out.println("${" + key + "} -> " + value);
                exists = true;
            }
            if (appVariables.containsKey(key)) {
                value = appVariables.get(key);
                // System.out.println("${" + key + "} -> " + value);
                exists = true;
            }
            if (!exists) {
                return new ParseResult(false, line);
            }

            VariableParser valueParser = VariableParser.parse(value);
            if (valueParser.isHasParameter()) {
                value = valueParser.replace(appVariables.get(valueParser.getParameter()));
            }
            line = parser.replace(value);
        }
        return new ParseResult(exists, line);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
