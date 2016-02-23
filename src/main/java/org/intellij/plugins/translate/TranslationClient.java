package org.intellij.plugins.translate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class TranslationClient {
    public static final String HOST = "https://translate.yandex.net/api/v1.5/tr.json/";
    public static final String API_KEY = "trnsl.1.1.20160212T183519Z.159e552a0d456f46.e9689f22d09a5dd36ea00994e1e373a4c0a794b4";

    enum Method {
        TRANSLATE("translate"),
        GET_LANGS("letLangs");

        String value;

        Method(String param) {
            this.value = param;
        }

        public String toString() {
            return value;
        }
    }

    enum Param {
        API_KEY("?key="),
        LANG_PAIR("&lang="),
        TEXT("&text=");

        String value;

        Param(String param) {
            this.value = param;
        }

        public String toString() {
            return value;
        }
    }


    protected static final String ENCODING = "UTF-8";

    public static String translate(String text, String langPair) {
        final String url = HOST + Method.TRANSLATE + Param.API_KEY.toString() + API_KEY +
                Param.LANG_PAIR.toString() + langPair + Param.TEXT.toString() + text;
        final String request = request(url);
        final String translatedText = (String) parseJSON(request, Method.TRANSLATE);
        return translatedText;
    }


    public static String[] getLangPairs() {
        final String url = HOST + Method.GET_LANGS + Param.API_KEY + API_KEY;
        final String request = request(url);
        final String[] langPairs = (String[]) parseJSON(request, Method.GET_LANGS);
        return langPairs;
    }

    private static Object parseJSON(String request, Method method) {

        return null;
    }

    private static String request(String url) {
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
