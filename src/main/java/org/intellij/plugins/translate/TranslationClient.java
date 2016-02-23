package org.intellij.plugins.translate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class TranslationClient {
    public static final String HOST = "https://translate.yandex.net/api/v1.5/tr.json/";
    public static final String API_KEY = "trnsl.1.1.20160212T183519Z.159e552a0d456f46.e9689f22d09a5dd36ea00994e1e373a4c0a794b4";

    protected static final String METHOD_TRANSLATE = "translate",
            METHOD_GET_LANGS = "getLangs";

    protected static final String PARAM_API_KEY = "?key=",
            PARAM_LANG_PAIR = "&lang=",
            PARAM_TEXT = "&text=";

    protected static final String ENCODING = "UTF-8";

    public String translate(String text, String lang) {

        final String url = HOST + METHOD_TRANSLATE + PARAM_API_KEY + API_KEY +
                PARAM_LANG_PAIR + lang + PARAM_TEXT + text;
        return request(url);
    }


    public String getLangPairs() {
        final String url = HOST + METHOD_GET_LANGS + PARAM_API_KEY + API_KEY;
        return request(url);
    }

    private String request(String url) {
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("Accept-Charset", ENCODING);
            InputStream response = connection.getInputStream();

            Scanner s = new Scanner(response, ENCODING).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            return null;
        }
    }

}
