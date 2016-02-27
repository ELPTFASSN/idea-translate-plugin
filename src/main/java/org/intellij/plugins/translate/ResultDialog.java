package org.intellij.plugins.translate;

import javax.swing.*;
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
    private JTextArea selectedTextArea;
    private JTextArea translatedTextArea;
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

            }
        });
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
