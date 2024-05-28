package com.FinalExam.FinalExam.util;

public class SimplePasswordEncoder {

    private static final int SHIFT = 4;

    public static String encode(String input) {
        StringBuilder encoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            encoded.append((char) (c + SHIFT));
        }
        return encoded.toString();
    }

    public static String decode(String input) {
        StringBuilder decoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            decoded.append((char) (c - SHIFT));
        }
        return decoded.toString();
    }
}
