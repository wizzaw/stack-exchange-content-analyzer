package com.nayki.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class URLUtils {

    public static boolean validate(String url) {
        try {
            final URI uri = new URL(url).toURI();

            final String scheme = uri.getScheme();
            if (!Objects.equals(scheme, "http") && !Objects.equals(scheme, "https")) {
                return false;
            }
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return true;
    }
}
