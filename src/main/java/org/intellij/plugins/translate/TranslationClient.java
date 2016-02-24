package org.intellij.plugins.translate;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import static org.intellij.plugins.translate.TranslateException.ResponseCode;


public class TranslationClient {
    private static final String HOST = "https://translate.yandex.net/api/v1.5/tr.json/";
    private static final String YANDEX_API_KEY = "trnsl.1.1.20160212T183519Z.159e552a0d456f46.e9689f22d09a5dd36ea00994e1e373a4c0a794b4";

    private enum Method {
        TRANSLATE("translate"),
        GET_LANGS("getLangs");

        private final String value;

        Method(String param) {
            this.value = param;
        }

        @Override
        public String toString() {
            return value;
        }
    }


    private enum Param {
        API_KEY("?key="),
        LANG_PAIR("&lang="),
        TEXT("&text=");

        private final String value;

        Param(String param) {
            this.value = param;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    protected static final String ENCODING = "UTF-8";

    public static String translate(String text, String langPair) throws TranslateException, IOException {
        final String url = HOST + Method.TRANSLATE + Param.API_KEY + YANDEX_API_KEY +
                Param.LANG_PAIR + langPair + Param.TEXT + text;

        JSONObject json = jsonRequest(url);
        int code = (int) json.get("code");
        isRespongeSuccessful(code);

        JSONArray langsArray = (JSONArray) json.get("text");
        String requestedText = (String) langsArray.get(0);
        return requestedText;
    }

    private static void isRespongeSuccessful(int code) throws TranslateException {
        if (code != ResponseCode.SUCCESSFUL.code) {
            throw new TranslateException(code);
        }
    }

    public static Set<String> getLangPairs() throws IOException, TranslateException {
        final String url = HOST + Method.GET_LANGS + Param.API_KEY + YANDEX_API_KEY;

        JSONObject json = jsonRequest(url);

        /* If request successful, response code don't exist in json */
        try {
            int code = (int) json.get("code");
            isRespongeSuccessful(code);
        } catch (JSONException e) {
        }

        JSONArray langsArray = (JSONArray) json.get("dirs");

        Set<String> langs = new HashSet<String>();
        for (int i = 0; i < langsArray.length(); i++) {
            langs.add(langsArray.getString(i));
        }

        return langs;
    }


    @NotNull
    private static JSONObject jsonRequest(String url) throws IOException {
        InputStream response = null;
        Scanner scanner = null;

        try {
            URLConnection connection = new URL(url).openConnection();
            response = connection.getInputStream();

            scanner = new Scanner(response, "UTF-8").useDelimiter("\\A");
            String request = scanner.hasNext() ? scanner.next() : "";

            return new JSONObject(request);
        } catch (IOException e) {
            throw new IOException("Response isn't obtained", e);
        } finally {
            if (response != null) {
                response.close();
            }
            if (scanner != null) {
                scanner.close();
            }
        }
    }


}
