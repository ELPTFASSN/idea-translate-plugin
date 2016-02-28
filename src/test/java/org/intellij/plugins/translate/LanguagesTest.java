package org.intellij.plugins.translate;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LanguagesTest {

    @Test
    public void testTransPairExist() {
        String langPair = Languages.getTransPair("English", "Russian");
        Assert.assertEquals("en-ru", langPair);
    }

    @Test(expected = NullPointerException.class)
    public void testTransPairNotExist() {
        Languages.getTransPair("aa", "aa");
    }

    @Test
    public void testGetLangs() {
        List<String> langPairs;
        Assert.assertNotNull(langPairs = Languages.getLangs());

        langPairs.contains("English");
    }
}