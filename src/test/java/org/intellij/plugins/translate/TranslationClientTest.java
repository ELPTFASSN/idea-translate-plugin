package org.intellij.plugins.translate;

import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.util.Set;

public class TranslationClientTest {

    @Test
    public void testTranslate() throws TranslateException, IOException {
        String langPair = Languages.transPairExist("English", "Russian");
        String request = TranslationClient.translate("Hello!", langPair);
        JOptionPane.showMessageDialog(null, request,
                "Selected text", JOptionPane.PLAIN_MESSAGE);
        Assert.assertNotNull(request);
    }

    @Test
    public void testGetLangPairs() throws IOException, TranslateException {
        Set<String> request = TranslationClient.getLangPairs();
        Assert.assertNotNull(request);
    }
}