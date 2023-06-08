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
            Main main = new Main("-t", "src/test/resources/test-data/templates", "-p",  "src/test/resources/test-data/pipelines-schemas");
            main.run();
        }catch (ParseException ex){
            Assert.fail();
            System.out.println("Error on parsed");
        }
    }

    @Test
    public void testRunWithPath() {
        try {
            File output = new File("src/test/resources/my-output");
            if(output.exists()){
                output.delete();
            }
            output.mkdirs();
            Main main = new Main("-t", "src/test/resources/test-data/templates", "-p",  "src/test/resources/test-data/pipelines-schemas-notrelative", "--output-path", output.getAbsolutePath());
            main.run();
            File expectedFile = new File(output, "pipelines/videoviz/latest/Jenkinsfile");
            File expectedFile2 = new File(output, "pipelines/videoviz-two/latest/Jenkinsfile");

            Assert.assertTrue(expectedFile.exists());
            Assert.assertTrue(expectedFile2.exists());

            Assert.assertTrue(expectedFile.isFile());
            Assert.assertTrue(expectedFile2.isFile());

            output.delete();

        }catch (ParseException ex){
            Assert.fail();
            System.out.println("Error on parsed");
        }
    }
}
