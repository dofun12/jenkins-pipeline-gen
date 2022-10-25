package org.lemanoman.jenkinsgen;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableParser {
    private boolean hasParameter;
    private String parameter;
    private String oldValue;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String replace(String newValue){
        if(oldValue == null || newValue == null){
            return oldValue;
        }
        return oldValue.replace("${"+parameter+"}", newValue);
    }

    private VariableParser(String line){
        Pattern pattern = Pattern.compile("\\$\\{(.*)\\}");
        Matcher matcher = pattern.matcher(line);
        this.oldValue = line;
        if(matcher.find()) {
            this.parameter = matcher.group(1);
            this.hasParameter = true;
        }
    }
    public static VariableParser parse(String line){
        return new VariableParser(line);
    }

    public boolean isHasParameter() {
        return hasParameter;
    }

    public void setHasParameter(boolean hasParameter) {
        this.hasParameter = hasParameter;
    }
}
