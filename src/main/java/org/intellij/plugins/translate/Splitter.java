package org.intellij.plugins.translate;

public class Splitter {
    static public String split(String text) {
        text = splitDollar(text);
        text = splitUnderscore(text);
        text = splitCamelCase(text);
        return text;
    }

    /**
     * Method should split text in this way:
     * $value -> value
     * $test$dollar -> test dollar
     */
    static private String splitDollar(String text) {
        text = text.replace("$", " ");
        return text.trim();
    }

    /**
     * Method should split text in this way:
     * underscore_Value -> underscore Value
     * _test_underscore_Value ->  underscore Value
     */
    static private String splitUnderscore(String text) {
        text = text.replace("_", " ");
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
    static private String splitCamelCase(String text) {
        StringBuilder builder = new StringBuilder(text.length() * 3 / 2);
        String regEXP = "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])";

        for (String subString : text.split(regEXP)) {
            char lastChar = subString.charAt(subString.length() - 1);

            builder.append(subString);

            if (lastChar != ' ') {
                builder.append(" ");
            }
        }
        return builder.toString().trim();
    }
}
