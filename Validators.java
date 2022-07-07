package nl.hhs.apep2122group1.utils;

import java.util.Arrays;

import nl.hhs.apep2122group1.models.Label;

public class Validators {
    public static boolean validateStringNotNullOrEmpty(String str) {
        if (str != null && !str.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean validateStringDoesNotContainWhitespace(String str) {
        if (str.contains("\r") || str.contains("\n") || str.contains("\t") || str.contains(" ")) {
            return false;
        }
        return true;
    }

    public static ValidationResult validatePasswordComplexity(String password) {
        if (password.equals("123456") || password.equalsIgnoreCase("abcdef") || password.equalsIgnoreCase("password") || password.equalsIgnoreCase("batman")) {
            return ValidationResult.SPECIFIC_INPUT_NOT_ALLOWED;
        } else if (password.length() < 6) {
            return ValidationResult.TOO_SHORT;
        } else if (allCharactersAreTheSame(password)) {
            return ValidationResult.SAME_CHARACTERS;
        }
        return ValidationResult.OK;
    }

    public static boolean validateEditLabelTitleUnique(String oldLabelTitle, String newLabelTitle, Label[] existingLabels) {
        if (newLabelTitle.equalsIgnoreCase(oldLabelTitle)) {
            return true;
        } else if (Arrays.stream(existingLabels).noneMatch(l -> l.getTitle().equalsIgnoreCase(newLabelTitle))) {
            return true;
        }
        return false;
    }

    public static boolean validateNewLabelTitleUnique(String newLabelTitle, Label[] existingLabels) {
        boolean labelAlreadyUsed = Arrays.stream(existingLabels).anyMatch(l -> l.getTitle().equalsIgnoreCase(newLabelTitle));
        return !labelAlreadyUsed;
    }

    private static boolean allCharactersAreTheSame(String str) {
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (c != chars[0]) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateDateIsEmptyOrNotNull(String str) {
        if (str.trim().equals("") || Converter.inputStringToTimeStamp(str) != null) {
            return true;
        }
        return false;
    }
}

