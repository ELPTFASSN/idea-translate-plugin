package org.intellij.plugins.translate;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static org.intellij.plugins.translate.TranslateException.ResponseCode;

/**
 * Class {@code TranslationClient} provides translation of the text or
 * the gets all pairs for translation.
 */
public class TranslationClient {
    private static final String HOST = "https://translate.yandex.net/api/v1.5/tr.json/";
    private static final String YANDEX_API_KEY =
            "trnsl.1.1.20160212T183519Z.159e552a0d456f46"
                    + ".e9689f22d09a5dd36ea00994e1e373a4c0a794b4";

    public TranslationClient() {

    }

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

    /**
     * Translates text to the specified language.
     * <p>
     * The method generates the request as a http
     * further and sends as an argument to the
     * {@code jsonRequest} method that returns
     * JSON which can be parsed,
     * returns the translated text.
     * <p>
     * Request syntax:
     * https://translate.yandex.net/api/v1.5/tr.json/translate ?
     * key=<API key>
     * & text=<text to translate>
     * & lang=<the translation direction>
     * & [format=<text format>]
     * & [options=<options>]
     * & [callback=<name of callback-function>]
     * <p>
     * key    API key. It is issued free of charge.
     * text   The text to translate.
     * You can use multiple text parameters in a request.
     * Restrictions:
     * -    For POST requests, the maximum size of the
     *      text being passed is 10000 characters.
     * -    In GET requests, the restriction applies
     *      not to the text itself, but to the size of
     *      the entire request string, which can contain
     *      other parameters besides the text.
     * The maximum size of the request string is 10 kB.
     * lang   The translation direction.
     * You can use any of the following ways to set it:
     * As a pair of language codes separated by a hyphen (“from”-“to”).
     * For example, en-ru indicates translating from English to Russian.
     * As the final language code (for example, ru). In this case,
     * the service tries to detect the source language automatically.
     * format Text format.
     * Possible values:
     * -   plain - Text without markup (default value).
     * -   html - Text in HTML format.
     * optionsThe only option available at this time is whether the response should include
     * the automatically detected language of the text being translated. This corresponds to
     * the value 1 for this parameter.
     * If the language of the text being translated is defined explicitly, meaning
     * the lang parameter is set as a pair of codes, the first code defines the source language.
     * This means that the options parameter does not allow switching to automatic language detection.
     * However, it does allow you to understand whether the source language was defined correctly in the lang parameter.
     * <p>
     * Response syntax codes:
     * 200	   Operation completed successfully;
     * 401    Invalid API key;
     * 402    Blocked API key;
     * 403    Exceeded the daily limit on the number of requests;
     * 404    Exceeded the daily limit on the amount of translated text;
     * 413    Exceeded the maximum text size;
     * 422    The text cannot be translated;
     * 501    The specified translation direction is not supported.
     *
     * @param text     text to translate.
     * @param langPair lang pair for translate, exemple: "en-ru".
     * @return translated text.
     * @throws TranslateException when code of responge is not successful.
     * @throws IOException        when appears problems with get request.
     */
    public static String translate(final String text, final String langPair) throws TranslateException, IOException {
        if (text == null) {
            throw new NullPointerException(
                    "Text for translation is null");
        }
        if (langPair == null) {
            throw new NullPointerException(
                    "Lang pair for translation is null");
        }

        String encodedText = URLEncoder.encode(text, "UTF-8");

        final String url = HOST + Method.TRANSLATE + Param.API_KEY + YANDEX_API_KEY
                + Param.LANG_PAIR + langPair + Param.TEXT + encodedText;

        JSONObject json = jsonRequest(url);
        int code = (int) json.get("code");
        isRespongeSuccessful(code);

        JSONArray langsArray = (JSONArray) json.get("text");

        String requestedText = (String) langsArray.get(0);

        return requestedText;
    }

    /**
     * The Responge is considered correct if the code is a 200, but
     * in {@code getLangPairs} hasn't {@code SUCCESSFUL.code} is not written in the JSON
     *
     * @throws TranslateException when responge has code that differ than {@code SUCCESSFUL.code}
     */
    private static void isRespongeSuccessful(int code) throws TranslateException {
        if (code != ResponseCode.SUCCESSFUL.code) {
            throw new TranslateException(code);
        }
    }

    /**
     * Gets a list of translation directions supported by the service.
     * <p>
     * Request syntax:
     * https://translate.yandex.net/api/v1.5/tr.json/getLangs ?
     * key=<API key>
     * & [ui=<language code>]
     * & [callback=<name of the callback function>]
     * key     API key. It is issued free of charge.
     * ui      If set, the response contains explanations of language codes.
     * Language names are output in the language corresponding to the code in this parameter.
     * Acceptable values: ru, en, uk, be, ... .
     * callbackThe name of the callback function. Used when a JSONP response is desired.
     * <p>
     * Response syntax codes:
     * 401    Invalid API key;
     * 402    Blocked API key.
     * <p>
     * Note: there no code: 200 Operation completed successfully, in JSON response.
     *
     * @return set of lang pairs that can used as a argument of a {@code translate} method.
     * @throws TranslateException when code of responge is not successful.
     * @throws IOException        when appears problems with get request.
     */
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

    /**
     * Performs processing the request URL, returns JSONObject.
     *
     * @param url http request string.
     * @return JSONObject witch represent data of Yandex.Translator response.
     * @throws IOException when connection is fale.
     */
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
