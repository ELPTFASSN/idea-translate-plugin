package org.intellij.plugins.translate;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultDialog extends JFrame {
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


    public ResultDialog() {
        setContentPane(contentPane);

        swapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int from = fromComboBox.getSelectedIndex();
                int to = toComboBox.getSelectedIndex();
                fromComboBox.setSelectedIndex(to);
                toComboBox.setSelectedIndex(from);
            }
        });

        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performTranslation();
            }
        });
    }

    public void performTranslation() {
        String translatedText;

        try {
            String from = (String) fromComboBox.getSelectedItem();
            String to = (String) toComboBox.getSelectedItem();

            String langPair = Languages.transPairExist(from, to);

            String selectedText = getSelectedText();
            translatedText = TranslationClient.translate(selectedText, langPair);
        } catch (Exception exc) {
            translatedText = exc.getMessage();
        }

        setTranslatedText(translatedText);
    }

    public static ResultDialog createDialog(String title) {
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
        selectedPane.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
        selectedPane.setEditable(true);
    }

    private void setTranslatedPane() {
        translatedPane.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
        translatedPane.setEditable(false);

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

    public void setSelectedText(String translate) {
        selectedPane.setText(translate);
    }

    public void setTranslatedText(String translate) {
        String textWithLink = translate + "<br> <a href='http://translate.yandex.com/'>Powered by Yandex.Translator</a>";
        translatedPane.setText(textWithLink);
    }

    public void setFromLangBox(String from) {
        this.fromComboBox.setSelectedItem(from);
    }

    public void setToLangBox(String to) {
        this.toComboBox.setSelectedItem(to);
    }

}
