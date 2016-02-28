package org.intellij.plugins.translate;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultDialog extends JDialog {
    private JPanel contentPane;
    private JPanel functions;
    private JPanel translation;
    private JComboBox fromComboBox;
    private JButton swapButton;
    private JComboBox toComboBox;
    private JButton translateButton;
    private JEditorPane selectedPane;
    private JEditorPane translatedPane;
    private JSplitPane splitPane;

    public ResultDialog() {
        setContentPane(contentPane);
        setModal(true);

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
                String translatedText;

                try {
                    String from = (String) fromComboBox.getSelectedItem();
                    String to = (String) toComboBox.getSelectedItem();

                    String langPair = Languages.transPairExist(from, to);

                    String selectedText = getSelectetText();
                    translatedText = TranslationClient.translate(selectedText, langPair);
                } catch (Exception exc) {
                    translatedText = exc.getMessage();
                }

                setTranslatedText(translatedText);
            }
        });
    }

    public static void createDialog(String title, String select, String translate) {
        ResultDialog dialog = new ResultDialog();

        dialog.setTitle(title);

        for (String s : Languages.getLangs()) {
            dialog.fromComboBox.addItem(s);
            dialog.toComboBox.addItem(s);
        }

        dialog.setSelectedPane();
        dialog.setTranslatedPane();

        dialog.setSelectedText(select);
        dialog.setTranslatedText(translate);

        dialog.pack();
        dialog.setMinimumSize(dialog.getSize());
        dialog.setVisible(true);
    }

    private String getSelectetText() throws BadLocationException {
        int length = selectedPane.getDocument().getLength();
        return selectedPane.getDocument().getText(0, length);
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

    private void setSelectedText(String translate) {
        selectedPane.setText(translate);
    }

    private void setTranslatedText(String translate) {
        String textWithLink = translate + "<br> <a href='http://translate.yandex.com/'> Powered by Yandex.Translator</a>";
        translatedPane.setText(textWithLink);
    }

}
