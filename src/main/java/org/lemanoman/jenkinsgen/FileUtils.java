package org.lemanoman.jenkinsgen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<String> readFile(String filepath) {
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
