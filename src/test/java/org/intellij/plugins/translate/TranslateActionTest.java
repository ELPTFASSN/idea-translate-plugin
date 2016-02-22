package org.intellij.plugins.translate;

import org.junit.Assert;
import org.junit.Test;

public class TranslateActionTest {
    final public String[][] dataCamelCase = {
            {"value", "value"},
            {"camel Value", "camelValue"},
            {"Title Value","TitleValue"},
            {"test JSON Value","testJSONValue"},
            {"VALUE", "VALUE"},
    };

    final public String[][] dataUnderscore = {
            {"underscore Value","underscore_Value"},
            {"test underscore Value","_test_underscore_Value"}
    };

    final public String[][] dataDollar = {
            {"value","$value"},
            {"test dollar","$test$dollar"}
    };

    @Test
    public void testSplitCamelCase() {
        for (String[] stringSet : dataCamelCase) {
            Assert.assertTrue(stringSet[0].equalsIgnoreCase(
                    TranslateAction.splitCamelCase(stringSet[1])));
        }
    }

    @Test
    public void testSplitUnderscore() {
        for (String[] stringSet : dataUnderscore) {
            Assert.assertTrue(stringSet[0].equalsIgnoreCase(
                    TranslateAction.splitUnderscore(stringSet[1])));
        }
    }

    @Test
    public void testSplitDollar() {
        for (String[] stringSet : dataDollar) {
            Assert.assertTrue(stringSet[0].equalsIgnoreCase(
                    TranslateAction.splitDollar(stringSet[1])));
        }
    }

}