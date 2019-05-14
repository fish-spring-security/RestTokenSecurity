package com.github.fish56.resttokensecurity.util;

import javax.servlet.http.HttpServletRequest;

public class ServletUtil {
    private static final String tokenPrefix = "bearer ";

    public static String parseTokenFromRequest(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.toLowerCase().startsWith(tokenPrefix)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
