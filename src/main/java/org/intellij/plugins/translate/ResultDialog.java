package org.intellij.plugins.translate;

import javax.swing.*;

public class ResultDialog extends JDialog {
    private JPanel contentPane;
    private JPanel functions;
    private JPanel translation;
    private JComboBox comboBox1;
    private JButton swapButton;
    private JComboBox comboBox2;
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

        dialog.selectedTextArea.setLineWrap(true);
        dialog.selectedTextArea.setText(select);

        dialog.translatedTextArea.setLineWrap(true);
        dialog.translatedTextArea.setText(translate);

        dialog.pack();
        dialog.setMinimumSize(dialog.getSize());
        dialog.setVisible(true);
    }

}
