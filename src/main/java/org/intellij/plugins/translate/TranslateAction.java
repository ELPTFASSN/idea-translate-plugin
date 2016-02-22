package org.intellij.plugins.translate;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

import javax.swing.*;

public class TranslateAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent event) {

        Project project = event.getData(PlatformDataKeys.PROJECT);
        Editor data = DataKeys.EDITOR.getData(event.getDataContext());

        if (data != null) {
            String selectedText = data.getSelectionModel().getSelectedText();
            if (selectedText != null && selectedText.length() > 0) {

                JOptionPane.showMessageDialog(null, selectedText,
                        "Selected text", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}

