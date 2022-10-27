package org.lemanoman.jenkinsgen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
    public static List<String> readFile(String filepath) {
        String out = readFileAsString(filepath);
        return Arrays.stream(out.split(System.lineSeparator())).toList();

    }
    public static String readFileAsString(String filepath) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {

            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();

    }
}
