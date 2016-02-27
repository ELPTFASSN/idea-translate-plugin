package org.intellij.plugins.translate;

import javax.swing.*;

public class ResultDialog extends JDialog {
    private JPanel contentPane;
    private JPanel functions;
    private JPanel translation;
    private JComboBox fromComboBox;
    private JButton swapButton;
    private JComboBox toComboBox;
    private JButton translateButton;
    private JTextArea selectedTextArea;
    private JTextArea translatedTextArea;
    private JSplitPane splitPane;

    public ResultDialog() {
        setContentPane(contentPane);
        setModal(true);

    }

    public static void createDialog(String title, String select, String translate) {
        ResultDialog dialog = new ResultDialog();

        for (String s : Languages.getLangs()) {
            dialog.fromComboBox.addItem(s);
            dialog.toComboBox.addItem(s);
        }

        dialog.selectedTextArea.setLineWrap(true);
        dialog.selectedTextArea.setText(select);

        dialog.translatedTextArea.setLineWrap(true);
        dialog.translatedTextArea.setText(translate);

        dialog.pack();
        dialog.setMinimumSize(dialog.getSize());
        dialog.setVisible(true);
    }

}
