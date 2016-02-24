package org.intellij.plugins.translate;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Languages {
    public static final String ALBANIAN = "sq",
            ENGLISH = "en",
            ARABIC = "ar",
            ARMENIAN = "hy",
            AZERBAIJAN = "az",
            AFRIKAANS = "af",
            BASQUE = "eu",
            BELARUSIAN = "be",
            BULGARIAN = "bg",
            BOSNIAN = "bs",
            WELSH = "cy",
            VIETNAMESE = "vi",
            HUNGARIAN = "hu",
            HAITIAN = "ht",
            GALICIAN = "gl",
            DUTCH = "nl",
            GREEK = "el",
            GEORGIAN = "ka",
            DANISH = "da",
            YIDDISH = "he",
            INDONESIAN = "id",
            IRISH = "ga",
            ITALIAN = "it",
            ICELANDIC = "is",
            SPANISH = "es",
            KAZAKH = "kk",
            CATALAN = "ca",
            KYRGYZ = "ky",
            CHINESE = "zh",
            KOREAN = "ko",
            LATIN = "la",
            LATVIAN = "lv",
            LITHUANIAN = "lt",
            MALAGASY = "mg",
            MALAY = "ms",
            MALTESE = "mt",
            MACEDONIAN = "mk",
            MONGOLIAN = "mn",
            GERMAN = "de",
            NORWEGIAN = "no",
            PERSIAN = "fa",
            POLISH = "pl",
            PORTUGUESE = "pt",
            ROMANIAN = "ro",
            RUSSIAN = "ru",
            SERBIAN = "sr",
            SLOVAKIAN = "sk",
            SLOVENIAN = "sl",
            SWAHILI = "sw",
            TAJIK = "tg",
            THAI = "th",
            TAGALOG = "tl",
            TATAR = "tt",
            TURKISH = "tr",
            UZBEK = "uz",
            UKRAINIAN = "uk",
            FINISH = "fi",
            FRENCH = "fr",
            CROATIAN = "hr",
            CZECH = "cs",
            SWEDISH = "sv",
            ESTONIAN = "et",
            JAPANESE = "ja";

    private static List<String> langPairs = null;

    static {
        try {
            langPairs = TranslationClient.getLangPairs();
        } catch (final IOException | TranslateException e) {
            throw new RuntimeException("Can't load lang pairs", e);
        }
    }

    @NotNull
    public String tranlationPairExist(String from, String to) {
        String newPair = (from + "-" + to).intern();
        for (String pair : langPairs) {
            if (newPair == pair) {
                return pair;
            }
        }
        return null;
    }

    @NotNull
    public List<String> doubleTranlationExist(String from, String midle, String to) {
        String firstPair = (from + "-" + midle).intern();
        String secondPair = (midle + "-" + to).intern();

        for (String fpair : langPairs) {
            if (firstPair == fpair) {
                for (String spair : langPairs) {
                    if (secondPair == spair) {
                        List<String> doubleTranlation = new ArrayList<String>(2);
                        doubleTranlation.add(firstPair);
                        doubleTranlation.add(secondPair);
                        return doubleTranlation;
                    }
                }
            }
        }
        return null;
    }

}
