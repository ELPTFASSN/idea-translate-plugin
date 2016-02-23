package org.intellij.plugins.translate;

import org.junit.Assert;
import org.junit.Test;

public class TranslationClientTest {

    @Test
    public void testTranslate() {
        String langPair = Languages.ENGLISH + "-" + Languages.RUSSIAN;
        String request = new TranslationClient().translate("Hello!", langPair);
        Assert.assertNotNull(request);
    }

    @Test
    public void testGetLangPairs() {
        String request = new TranslationClient().getLangPairs();
        Assert.assertNotNull(request);
    }
}