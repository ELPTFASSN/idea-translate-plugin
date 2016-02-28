package org.intellij.plugins.translate;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

public class TranslateAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {

        Project project = event.getData(PlatformDataKeys.PROJECT);
        Editor data = DataKeys.EDITOR.getData(event.getDataContext());

        if (data != null) {
            final String selectedText = data.getSelectionModel().getSelectedText();
            if (selectedText != null && selectedText.length() > 0) {

                final String splitedText = splitText(selectedText);

                ResultDialog dialog = ResultDialog.createDialog("Yandex.Translator");

                dialog.setSelectedText(splitedText);

                dialog.setFromLangBox("English");
                dialog.setToLangBox("Russian");

                dialog.performTranslation();
            }
        }
    }

    private String splitText(String text) {
        text = splitDollar(text);
        text = splitCamelCase(text);
        text = splitUnderscore(text);
        return text;
    }

    /**
     * Method should split text in this way:
     * $value -> value
     * $test$dollar -> test dollar
     */
    static String splitDollar(String text) {
        text = text.replace("$", " ");
        return text.trim();
    }

    /**
     * Method should split text in this way:
     * value -> value
     * camelValue -> camel Value
     * TitleValue -> Title Value
     * testJSONValue -> test JSON Value
     * VALUE -> VALUE
     */
    static String splitCamelCase(String text) {
        StringBuilder builder = new StringBuilder(text.length() * 3 / 2);
        String regEXP = "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])";
        for (String subString : text.split(regEXP)) {
            builder.append(subString).append(" ");
        }
        return builder.toString().trim();
    }

    /**
     * Method should split text in this way:
     * underscore_Value -> underscore Value
     * _test_underscore_Value ->  underscore Value
     */
    static String splitUnderscore(String text) {
        text = text.replace("_", " ");
        return text.trim();
    }

}

