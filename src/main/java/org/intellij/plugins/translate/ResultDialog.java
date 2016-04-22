package org.intellij.plugins.translate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The {@code ResultDialog} class represents form for performing translation.
 * It displays the selected and translated text and also allows
 * to edit the entered text, or change languages translation.
 */
public final class ResultDialog extends JFrame {
    private JPanel contentPane;
    private JPanel functions;
    private JPanel translation;

    private JComboBox fromComboBox;
    private JComboBox toComboBox;

    private JButton swapButton;
    private JButton translateButton;

    private JSplitPane splitPane;
    private JEditorPane selectedPane;
    private JEditorPane translatedPane;

    /**
     * The {@code ResultDialog} constructor configurates form,
     * adds languages to langBoxes.
     * Adds the listener to link Yandex.Translator.
     * Adds the listener to swapButton witch changes
     * from and to languages in boxes.
     */
    private ResultDialog() {
        setContentPane(contentPane);

        swapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                int from = fromComboBox.getSelectedIndex();
                int to = toComboBox.getSelectedIndex();
                fromComboBox.setSelectedIndex(to);
                toComboBox.setSelectedIndex(from);
            }
        });

        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                performTranslation();
            }
        });
    }

    /**
     * Allows to translate text from one language to another.
     *  
     * Text from selectedPane is translated and outputs to the translatedPane.
     * In case of exception message will be shown.
     *
     * To use translation form you need:
     * 1)  set the text to {@code selectedPane};
     * 2)  select the languages in from to language boxes;
     * 3)  call translation of the text.
     */
    public void performTranslation() {
        String translatedText;

        try {
            String from = (String) fromComboBox.getSelectedItem();
            String to = (String) toComboBox.getSelectedItem();

            String langPair = Languages.getTransPair(from, to);

            String selectedText = getSelectedText();
            translatedText =
                    TranslationClient.translate(selectedText, langPair);
        } catch (Exception exc) {
            translatedText = exc.getMessage();
        }

        setTranslatedText(translatedText);
    }

    /**
     *  Specifies a primary setup frame.
     *  Adds all the existing languages on the lunchbox.
     *  Defines a title.
     *
     *  @param title title of Frame.
     *  @return created ResultDialog.
     */
    public static ResultDialog createDialog(final String title) {
        ResultDialog dialog = new ResultDialog();

        dialog.setTitle(title);

        for (String s : Languages.getLangs()) {
            dialog.fromComboBox.addItem(s);
            dialog.toComboBox.addItem(s);
        }

        dialog.setSelectedPane();
        dialog.setTranslatedPane();

        dialog.pack();
        dialog.setMinimumSize(dialog.getSize());
        dialog.setVisible(true);

        return dialog;
    }

    private void setSelectedPane() {
        selectedPane.setEditorKit(
                JEditorPane.createEditorKitForContentType("text/html"));
        selectedPane.setEditable(true);
    }

    private void setTranslatedPane() {
        translatedPane.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
        translatedPane.setEditable(false);

        /* Add hyperlink listener for http://translate.yandex.com/ */
        translatedPane.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    if (Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().browse(e.getURL().toURI());
                        } catch (Exception exc) {
                            setTranslatedText(exc.getMessage());
                        }
                    }
                }
            }
        });
    }

    public String getSelectedText() throws BadLocationException {
        int length = selectedPane.getDocument().getLength();
        return selectedPane.getDocument().getText(0, length);
    }

    public String getTranslatedText() throws BadLocationException {
        int length = translatedPane.getDocument().getLength();
        return translatedPane.getDocument().getText(0, length);
    }

    public void setSelectedText(final String translate) {
        selectedPane.setText(translate);
    }

    /**
     *  Sets translated text to the translatedPane.
     *  Adds a link to the translate.yandex.com.
     *  @param translate translated text.
     */
    public void setTranslatedText(final String translate) {
        String textWithLink = translate + "<br> <a style='text-decoration:none; color: black'  href='http://translate.yandex.com/'>Powered by Yandex.Translator</a>";
        translatedPane.setText(textWithLink);
    }

    public void setFromLangBox(final String from) {
        this.fromComboBox.setSelectedItem(from);
    }

    public void setToLangBox(final String to) {
        this.toComboBox.setSelectedItem(to);
    }

}
