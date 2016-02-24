package org.intellij.plugins.translate;

import java.util.HashMap;
import java.util.Map;

public class TranslateException extends Exception {
    private final ResponseCode response;

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

    TranslateException(ResponseCode response) {
        if (response == null) {
            throw new NullPointerException();
        }
        if (response.equals(ResponseCode.SUCCESSFUL)) {
            throw new RuntimeException("Exceptions cannot be applied to ResponseCode.SUCCESSFUL.code");
        }
        this.response = response;
    }

    TranslateException(int code) {
        if (code == ResponseCode.SUCCESSFUL.code) {
            throw new RuntimeException("Exceptions cannot be applied to ResponseCode.SUCCESSFUL.code");
        }
        ResponseCode response = ResponseCode.getByCode(code);

        if (response == null) {
            throw new NullPointerException("No such code in ResponseCode");
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