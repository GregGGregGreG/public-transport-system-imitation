package ua.telesens.ostapenko.systemimitation.util;

import static ua.telesens.ostapenko.systemimitation.util.RandomStringGenerator.Mode.ALPHANUMERIC;

/**
 * @author root
 * @since 20.01.16
 */
public class RandomStringGenerator {

    private static final int DEFAULT_LENGTH = 10;

    public static enum Mode {
        ALPHA, ALPHANUMERIC, NUMERIC
    }

    public static String generate() {
        return generate(DEFAULT_LENGTH, ALPHANUMERIC);
    }

    public static String generate(int length, Mode mode) {

        StringBuilder buffer = new StringBuilder();
        String characters = "";

        switch (mode) {

            case ALPHA:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;

            case ALPHANUMERIC:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                break;

            case NUMERIC:
                characters = "1234567890";
                break;
        }

        int charactersLength = characters.length();

        for (int i = 0; i < length; i++) {
            double index = Math.random() * charactersLength;
            buffer.append(characters.charAt((int) index));
        }
        return buffer.toString();
    }
}
