package org.lemanoman.tests.jenkinsgen;

import org.apache.commons.cli.ParseException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.lemanoman.jenkinsgen.*;

import java.io.File;
import java.util.Map;

public class TestMain {

    @Test
    public void testInvalidArgs() {
        try {
            Main main = new Main("-t", "teste", "-p", "teste2");
            Assert.fail();
        }catch (ParseException ex){
            System.out.println("Error on parsed");
        }
    }


    @Test
    public void testRun() {
        try {
            Main main = new Main("-t", "test-data/templates", "-p",  "./test-data/pipelines-schemas");
            main.run();
        }catch (ParseException ex){
            Assert.fail();
            System.out.println("Error on parsed");
        }
    }
}
