package org.lemanoman.jenkinsgen;

public class ParseResult {
    private boolean parameterExists;
    private String line;

    public ParseResult(boolean parameterExists, String line) {
        this.parameterExists = parameterExists;
        this.line = line;
    }

    public boolean isParameterExists() {
        return parameterExists;
    }

    public void setParameterExists(boolean parameterExists) {
        this.parameterExists = parameterExists;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
