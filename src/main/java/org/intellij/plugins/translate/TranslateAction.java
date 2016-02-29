package org.intellij.plugins.translate;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

/**
 * Starter class of the extension. The {@code TranslateAction} class translate
 * comments or code written in a foreign language.
 * The method  {@code actionPerformed} invoked by pressing Alt + T shortcut.
 */

public class TranslateAction extends AnAction {

    /**
     * The method {@code actionPerformed} takes the selected text from the {@code Editor},
     * translates the text or gets exception which was caused by  {@code TranslationClient}
     * and shows response in {@code ResultDialog}.
     *
     * @param event represents keystroke with the selected {@code Editor} and selected text in it.
     */

    @Override
    public void actionPerformed(AnActionEvent event) {

        Project project = event.getData(PlatformDataKeys.PROJECT);
        Editor data = DataKeys.EDITOR.getData(event.getDataContext());

        if (data != null) {
            final String selectedText = data.getSelectionModel().getSelectedText();
            if (selectedText != null && selectedText.length() > 0) {

                final String splitedText = Splitter.split(selectedText);

                ResultDialog dialog = ResultDialog.createDialog("Yandex.Translator");

                dialog.setSelectedText(splitedText);

                dialog.setFromLangBox("English");
                dialog.setToLangBox("Russian");

                dialog.performTranslation();
            }
        }
    }
}

