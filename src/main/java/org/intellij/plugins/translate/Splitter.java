package org.intellij.plugins.translate;

/**
 * The {@code Splitter} class is the collection
 * of methods for working with selected text.
 * String which represents name of value
 * with _ $ or CamelCase will be splited.
 */
final class Splitter {

    private Splitter() {

    }

    static String split(final String text) {
        String splitedString = splitDollar(text);
        splitedString = splitUnderscore(splitedString);
        splitedString = splitCamelCase(splitedString);
        return splitedString;
    }

    /**
     *  @return Method should split text in this way:
     *  $value -> value;
     *  $test$dollar -> test dollar.
     */
    static private String splitDollar(String text) {
        text = text.replace("$", " ");
        return text.trim();
    }

    /**
     *  @return Method should split text in this way:
     *  underscore_Value -> underscore Value;
     *  _test_underscore_Value ->  underscore Value.
     */
    static private String splitUnderscore(final String text) {
        String splitedText = text.replace("_", " ");
        return splitedText.trim();
    }

    /**
     *  @return Method should split text in this way:
     *  value -> value;
     *  camelValue -> camel Value;
     *  TitleValue -> Title Value;
     *  testJSONValue -> test JSON Value;
     *  VALUE -> VALUE.
     */
    static private String splitCamelCase(final String text) {
        StringBuilder builder = new StringBuilder();
        String regEXP = "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])";

        for (String subString : text.split(regEXP)) {
            char lastChar = subString.charAt(subString.length() - 1);

            builder.append(subString);

            /*avoids duplication of spaces*/
            if (lastChar != ' ') {
                builder.append(" ");
            }
        }
        return builder.toString().trim();
    }
}
