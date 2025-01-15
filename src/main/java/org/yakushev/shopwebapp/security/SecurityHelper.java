package org.yakushev.shopwebapp.security;

import java.util.Arrays;
import java.util.List;

public class SecurityHelper {
    private static final List<String> PUBLIC_API_URL_LIST =
            Arrays.asList("/api/login", "/api/signup");
    private static final String PRIVATE_API_PREFIX = "/api";

    public static boolean isPublicUrl(String servletPath) {
        if (PUBLIC_API_URL_LIST.contains(servletPath)) {
            return true;
        }

        return !servletPath.startsWith(PRIVATE_API_PREFIX);
    }
}
