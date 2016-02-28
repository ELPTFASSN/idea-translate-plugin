package org.intellij.plugins.translate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SplitterTest {

    private String splited;
    private String value;

    public SplitterTest(String splited, String value) {
        this.splited = splited;
        this.value = value;
    }

    @Parameterized.Parameters
    public static Collection dataCamelCase() {
        return Arrays.asList(new Object[][]{
                /*test camel case split*/
                {"value", "value"},
                {"camel Value", "camelValue"},
                {"Title Value", "TitleValue"},
                {"test JSON Value", "testJSONValue"},
                {"VALUE", "VALUE"},

                /*test underscore split*/
                {"underscore Value", "underscore_Value"},
                {"test underscore Value", "_test_underscore_Value"},

                /*test dollar split*/
                {"value", "$value"},
                {"test Dollar", "$test$Dollar"},
                {"test dollar", "$test$dollar"}
        });
    }

    @Test
    public void testSplit() {
        final String splitedValue = Splitter.split(value);
        Assert.assertTrue(splited.equals(splitedValue));
    }

}