package ru.desinfection.site.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static String getIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
