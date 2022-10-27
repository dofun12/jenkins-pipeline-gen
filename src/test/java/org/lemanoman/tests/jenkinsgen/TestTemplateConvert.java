package org.lemanoman.tests.jenkinsgen;

import org.junit.Assert;
import org.junit.Test;
import org.lemanoman.jenkinsgen.FileUtils;
import org.lemanoman.jenkinsgen.Loader;
import org.lemanoman.jenkinsgen.Pipeline;
import org.lemanoman.jenkinsgen.Schema;

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
        Pipeline pipeline = Loader.loadPipeline("src/test/resources/test-data/pipelines-schemas/test-pipeline.yml");
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

        Pipeline pipeline = Loader.loadPipeline("src/test/resources/test-data/pipelines-schemas/test-pipeline.yml");
        Assert.assertNotNull(pipeline);

        String expected = FileUtils.readFileAsString("src/test/resources/test-data/expected.output");
        String out = schema.fillTemplate(pipeline.getVariables());
        Assert.assertEquals(expected, out);
    }

    @Test
    public void testLoadAndCheckVars(){
        Schema schema = Loader.loadTemplate("src/test/resources/test-data/templates/exemple-schema");
        Assert.assertNotNull(schema);


    }
}
