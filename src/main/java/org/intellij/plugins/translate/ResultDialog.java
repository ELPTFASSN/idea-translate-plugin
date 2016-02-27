package org.intellij.plugins.translate;

import javax.swing.*;

public class ResultDialog extends JDialog {
    private JPanel contentPane;
    private JPanel functions;
    private JPanel translation;
    private JComboBox comboBox1;
    private JButton button1;
    private JComboBox comboBox2;
    private JButton button2;
    private JTextArea textArea1;
    private JTextArea textArea2;

    public ResultDialog() {
        setContentPane(contentPane);
        setModal(true);

    }

    public static void createDialog(String title, String select, String translate) {
        ResultDialog dialog = new ResultDialog();

        dialog.textArea1.setLineWrap(true);
        dialog.textArea1.setText(select);

        dialog.textArea2.setLineWrap(true);
        dialog.textArea2.setText(translate);

        dialog.pack();
        dialog.setMinimumSize(dialog.getSize());
        dialog.setVisible(true);
    }

}
