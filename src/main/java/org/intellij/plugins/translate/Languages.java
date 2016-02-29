package org.intellij.plugins.translate;

import java.io.IOException;
import java.util.*;

/**
 * Class {@code Languages} contains all shortcuts of supported
 * languages is used to create a pair for the translation.
 * And method to get language pair.
 */

public class Languages {

    /**
     * Contains all shortcuts of supported languages is used to create a pair
     * for the translation.
     * Contains the full name as a key (example: "English") and the shortcut (example: "ru")
     */
    private static HashMap<String, String> langShortcuts = null;

    /**
     * Yandex.Translate allows you to translate from any language to any within
     * the list of supported languages
     * <p>
     * The service translates between any of the languages in the list of supported languages.
     * To get this list call the {@code getLangs} method.
     */
    private static Set<String> langPairs = null;

    static {
        try {
            langPairs = TranslationClient.getLangPairs();
        } catch (final IOException | TranslateException e) {
            throw new RuntimeException("Can't load lang pairs. " + e.getMessage());
        }

        langShortcuts = new HashMap<>();

        langShortcuts.put("Albanian", "sq");
        langShortcuts.put("English", "en");
        langShortcuts.put("Arabic", "ar");
        langShortcuts.put("Armenian", "hy");
        langShortcuts.put("Azerbaijan", "az");
        langShortcuts.put("Afrikaans", "af");
        langShortcuts.put("Basque", "eu");
        langShortcuts.put("Belarusian", "be");
        langShortcuts.put("Bulgarian", "bg");
        langShortcuts.put("Bosnian", "bs");
        langShortcuts.put("Welsh", "cy");
        langShortcuts.put("Vietnamese", "vi");
        langShortcuts.put("Hungarian", "hu");
        langShortcuts.put("Haitian", "ht");
        langShortcuts.put("Galician", "gl");
        langShortcuts.put("Dutch", "nl");
        langShortcuts.put("Greek", "el");
        langShortcuts.put("Georgian", "ka");
        langShortcuts.put("Danish", "da");
        langShortcuts.put("Yiddish", "he");
        langShortcuts.put("Indonesian", "id");
        langShortcuts.put("Irish", "ga");
        langShortcuts.put("Italian", "it");
        langShortcuts.put("Icelandic", "is");
        langShortcuts.put("Spanish", "es");
        langShortcuts.put("Kazakh", "kk");
        langShortcuts.put("Catalan", "ca");
        langShortcuts.put("Kyrgyz", "ky");
        langShortcuts.put("Chinese", "zh");
        langShortcuts.put("Korean", "ko");
        langShortcuts.put("Latin", "la");
        langShortcuts.put("Latvian", "lv");
        langShortcuts.put("Lithuanian", "lt");
        langShortcuts.put("Malagasy", "mg");
        langShortcuts.put("Malay", "ms");
        langShortcuts.put("Maltese", "mt");
        langShortcuts.put("Macedonian", "mk");
        langShortcuts.put("Mongolian", "mn");
        langShortcuts.put("German", "de");
        langShortcuts.put("Norwegian", "no");
        langShortcuts.put("Persian", "fa");
        langShortcuts.put("Polish", "pl");
        langShortcuts.put("Portuguese", "pt");
        langShortcuts.put("Romanian", "ro");
        langShortcuts.put("Russian", "ru");
        langShortcuts.put("Serbian", "sr");
        langShortcuts.put("Slovakian", "sk");
        langShortcuts.put("Slovenian", "sl");
        langShortcuts.put("Swahili", "sw");
        langShortcuts.put("Tajik", "tg");
        langShortcuts.put("Thai", "th");
        langShortcuts.put("Tagalog", "tl");
        langShortcuts.put("Tatar", "tt");
        langShortcuts.put("Turkish", "tr");
        langShortcuts.put("Uzbek", "uz");
        langShortcuts.put("Ukrainian", "uk");
        langShortcuts.put("Finish", "fi");
        langShortcuts.put("French", "fr");
        langShortcuts.put("Croatian", "hr");
        langShortcuts.put("Czech", "cs");
        langShortcuts.put("Swedish", "sv");
        langShortcuts.put("Estonian", "et");
        langShortcuts.put("Japanese", "ja");
    }

    /**
     * Creates a pair for the translation from a full language names,
     * example: "en-ru".
     * <p>
     * Note: some of the translation pairs can not exist, even if we are given to reduce them.
     * If there is no translation pairs method throws exception witch displayed in ResultDialog.
     *
     * @param from the full name of the language,must be at key at the langShortcuts.
     * @param to   the full name of the language,must be at key at the langShortcuts.
     * @return pair for translation or if the translation pair
     * isn't founded in langPair set throws NullPointerException.
     */
    public static String getTransPair(String from, String to) {
        from = langShortcuts.get(from);
        to = langShortcuts.get(to);

        String newPair = (from + "-" + to).intern();

        if (!langPairs.contains(newPair)) {
            throw new NullPointerException("No such lang pair");
        }
        return newPair;
    }

    /**
     * Method returns all languages that can be used when translating from one language to another.
     *
     * @return all languages (map keys) in full form for which you can find abbreviation,
     * exemple: "English".
     */
    public static List<String> getLangs() {
        ArrayList<String> fullLangs = new ArrayList<>(langShortcuts.keySet());
        Collections.sort(fullLangs);
        return fullLangs;
    }

}
