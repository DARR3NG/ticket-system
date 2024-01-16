package com.elkastali.ticketservice.utils;

import java.security.SecureRandom;

public class PasswordUtils {
    public static String generatePassword() {
        return new SecureRandom().ints(7, 0, 62)
                .mapToObj(i -> "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
