package ru.desinfection.site.utils;

import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    @Value("${tokenLifetimeDays}")
    private String tokenLifetime;

    public String getAuthTokenFromRequest(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        
        Optional<Cookie> authCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> {
                    return cookie.getName().equals("Authorization") && cookie.getValue() != null;
                })
                .findFirst();

        return authCookie.map(Cookie::getValue)
                .orElse(null);
    }

    public void setUpAuthCookie(String token, HttpServletResponse response) {
        Cookie authCookie = new Cookie("Authorization", token);
        authCookie.setPath("/");
        authCookie.setHttpOnly(true);
        authCookie.setSecure(false);
        authCookie.setMaxAge(3600 * 24 * Integer.parseInt(tokenLifetime));

        response.addCookie(authCookie);
    }

    public void deleteAuthCookies(HttpServletResponse response){
        Cookie cookieAccess = new Cookie("Authorization",null);
        cookieAccess.setPath("/");
        cookieAccess.setHttpOnly(true);
        cookieAccess.setMaxAge(0);

        response.addCookie(cookieAccess);
    }
}
