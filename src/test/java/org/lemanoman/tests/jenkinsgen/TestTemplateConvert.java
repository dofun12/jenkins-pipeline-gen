package org.lemanoman.tests.jenkinsgen;

import org.junit.Assert;
import org.junit.Test;
import org.lemanoman.jenkinsgen.*;

import java.io.File;
import java.util.Map;

public class TestTemplateConvert {
    @Test
    public void testLoad(){
        Schema schema = Loader.loadTemplate("src/test/resources/test-data/templates/exemple-schema");
        Assert.assertNotNull(schema);

    }

    @Test
    public void testLoadPipeline(){
        Pipeline pipeline = Loader.loadPipeline("src/test/resources/test-data/pipelines-schemas/simple-pipeline.yml");
        Assert.assertNotNull(pipeline);
        Assert.assertEquals("mynamtest", pipeline.getName());
        Assert.assertEquals("http://gitasdajskdasdh", pipeline.getRepo());
        Assert.assertEquals("../test-output/test-pipeline-out", pipeline.getOutputPath());
        Map<String, String> variables = pipeline.getVariables();
        Assert.assertNotNull(variables);
        Assert.assertEquals(variables.get("env.BRANCH"), "${project.branch}");
        Assert.assertEquals(variables.get("env.GIT_REPO"), "http://xymzaasdaskdjasldkj");
        Assert.assertEquals(variables.get("env.PROJECT_NAME"), "${project.name}");
    }

    @Test
    public void testLoadPipelineAndFill(){
        Schema schema = Loader.loadTemplate("src/test/resources/test-data/templates/exemple-schema");
        Assert.assertNotNull(schema);

        Pipeline pipeline = Loader.loadPipeline("src/test/resources/test-data/pipelines-schemas/simple-pipeline.yml");
        Assert.assertNotNull(pipeline);

        String expected = FileUtils.readFileAsString("src/test/resources/test-data/expected.output");
        String out = schema.fillTemplate(pipeline.getVariables());
        Assert.assertEquals(expected, out);
    }

    @Test
    public void testLoadPipelineAndFillGlobals(){
        Schema schemaA = Loader.loadTemplate("src/test/resources/test-data/templates/second-schema");
        Assert.assertNotNull(schemaA);

        Pipeline pipelinePrimary = Loader.loadPipeline("src/test/resources/test-data/pipelines-schemas/test-pipeline-primary.yml");
        Assert.assertNotNull(pipelinePrimary);

        Pipeline pipelineSecondary = Loader.loadPipeline("src/test/resources/test-data/pipelines-schemas/test-pipeline-secondary.yml");
        Assert.assertNotNull(pipelineSecondary);

        String out = schemaA.fillTemplate(pipelineSecondary.getVariables());
        String expected = FileUtils.readFileAsString("src/test/resources/test-data/expectedGlobals.output");
        Assert.assertEquals(out,expected);
    }

    @Test
    public void testNewSyntax(){
        Map<String, Schema> templates = Loader.loadTemplates("src/test/resources/test-data/templates");

        Project projectPrimary = Loader.loadProject("src/test/resources/test-data/projects/multi-pipelines.yml");

    }
    @Test
    public void testLoadPipelineCreateOutput(){
        Schema schemaA = Loader.loadTemplate("src/test/resources/test-data/templates/second-schema");
        Assert.assertNotNull(schemaA);

        Pipeline pipelinePrimary = Loader.loadPipeline("src/test/resources/test-data/pipelines-schemas/test-pipeline-primary.yml");
        Assert.assertNotNull(pipelinePrimary);

        Pipeline pipelineSecondary = Loader.loadPipeline("src/test/resources/test-data/pipelines-schemas/test-pipeline-secondary.yml");
        Assert.assertNotNull(pipelineSecondary);


        String generatedPath = "src/test/resources/test-data/generatedfile.output";
        File generated = new File(generatedPath);
        if(generated.exists()){
            Assert.assertTrue(generated.delete());
        }

        schemaA.fillTemplate(pipelineSecondary.getVariables(), generatedPath);
        String expected = FileUtils.readFileAsString("src/test/resources/test-data/expectedGlobals.output");

        String out = FileUtils.readFileAsString(generatedPath);

        Assert.assertEquals(out,expected);
    }
    
    @Test
    public void testLoadAndCheckVars(){
        Schema schema = Loader.loadTemplate("src/test/resources/test-data/templates/exemple-schema");
        Assert.assertNotNull(schema);


    }
}
