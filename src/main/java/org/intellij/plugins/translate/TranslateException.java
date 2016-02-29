package org.intellij.plugins.translate;

import java.util.HashMap;
import java.util.Map;

/**
 * It represents an error that can occur when translating.
 * Represents all the mistakes that can be found in the JOSN answer of Yandex translator.
 */
public class TranslateException extends Exception {
    private final ResponseCode response;

    /**
     * Response codes represent all exception code values and it's messages
     * that may occur in JSON response of Yandex.Translator.
     * <p>
     * Note: SUCCESSFUL response code is not exceprion, is the correct translation,
     * so it can not be accepted as exeption code value.
     */
    public enum ResponseCode {
        SUCCESSFUL(200, "Operation completed successfully"),
        INVAKID_KEY(401, "Invalid API key"),
        BLOKED_KEY(402, "Blocked API key"),
        REQUEST_LIMIT(403, "Exceeded the daily limit on the number of requests"),
        TEXT_LIMIT(404, "Exceeded the daily limit on the amount of translated text"),
        MAX_TEXT(413, "Exceeded the maximum text size"),
        CANT_TRANSLATE(422, "The text cannot be translated"),
        INVALID_LANG_PAIR(501, "The specified translation direction is not supported");

        final int code;
        final String message;

        ResponseCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        private static final Map<Integer, ResponseCode> map;

        static {
            map = new HashMap<Integer, ResponseCode>();
            for (ResponseCode v : ResponseCode.values()) {
                map.put(v.code, v);
            }
        }

        public static ResponseCode getByCode(int i) {
            return map.get(i);
        }

    }

    /**
     * Creating Exception
     *
     * @param code receives the code of TranslateException and saves {@code ResponseCode} in TranslateException.
     *             Can not take the value of ResponseCode.SUCCESSFUL.code == 200 or
     *             another value than doesn't consist in ResponseCode.
     * @throws IllegalArgumentException when ResponseCode.SUCCESSFUL.code == 200
     *                                  IllegalArgumentException when no such code in ResponseCode
     */
    TranslateException(int code) {
        ResponseCode response = ResponseCode.getByCode(code);

        if (response == null) {
            throw new IllegalArgumentException("No such code in the ResponseCode");
        }
        if (response.code == ResponseCode.SUCCESSFUL.code) {
            throw new IllegalArgumentException("Exceptions cannot be applied to ResponseCode.SUCCESSFUL.code");
        }

        this.response = response;
    }

    @Override
    public String toString() {
        return getMessage();
    }

    @Override
    public String getMessage() {
        return response.message;
    }

    public int getCode() {
        return response.code;
    }
}