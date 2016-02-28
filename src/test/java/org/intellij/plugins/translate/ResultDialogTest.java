package org.intellij.plugins.translate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.text.BadLocationException;

import static junit.framework.Assert.assertEquals;

public class ResultDialogTest {
    public static final String TITLE = "TITLE";
    public static final String FROM = "English";
    public static final String TO = "Russian";
    public static final String TEXT = "The quick brown fox jumps over the lazy dog";
    public static final String TRANSLATED_TEXT = "Быстрая коричневая лиса прыгает через ленивую собаку";
    public static final String HIPER_TEXT = " Powered by Yandex.Translator";

    private ResultDialog dialog;

    public ResultDialogTest() {
        super();
    }

    @Before
    public void setUp() {
        dialog = ResultDialog.createDialog(TITLE);
        dialog.setVisible(false);
    }

    @Test
    public void testCreateDialog() {
        assertEquals(TITLE, dialog.getTitle());
    }

    @Test
    public void testPerformTranslation() {
        dialog.setSelectedText(TEXT);

        dialog.setFromLangBox("English");
        dialog.setToLangBox("Russian");

        dialog.performTranslation();

        String translatedText = null;

        try {
            translatedText = dialog.getTranslatedText();
        } catch (BadLocationException e) {
        }

        Assert.assertEquals('\n' + TRANSLATED_TEXT + HIPER_TEXT, translatedText);
    }

}