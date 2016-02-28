package org.intellij.plugins.translate;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

public class TranslationClientTest {
    public static final String FROM = "English";
    public static final String TO = "Russian";

    public static final String TEXT = "The quick brown fox jumps over the lazy dog";
    public static final String TRANSLATED_TEXT = "Быстрая коричневая лиса прыгает через ленивую собаку";

    @Test
    public void testTranslate() throws TranslateException, IOException {
        String pair = Languages.transPairExist(FROM, TO);
        String translatedText = TranslationClient.translate(TEXT, pair);

        Assert.assertEquals(TRANSLATED_TEXT, translatedText);
    }

    @Test
    public void testGetLangPairs() throws IOException, TranslateException {
        Set<String> request = TranslationClient.getLangPairs();
        Assert.assertNotNull(request);
        Assert.assertTrue(request.contains("en-ru"));
    }


}