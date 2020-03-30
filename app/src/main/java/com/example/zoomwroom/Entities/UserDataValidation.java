package com.example.zoomwroom.Entities;
import android.telephony.PhoneNumberUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * This class implements static methods used for data validation checks to ensure that the user
 * is inputting correct info when they are registering or editing their profile
 *
 * Source: https://www.techiedelight.com/check-string-contains-alphanumeric-characters-java/
 */
public class UserDataValidation {

    public static boolean isAlphaNumeric(String userName) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");

        return p.matcher(userName).find();
    }

    public static boolean isPhoneNumberValid(String number) {
        if (number.length() < 10) {
            return false;
        }
        return number.chars().allMatch(Character::isDigit);
    }

    public static boolean isAlpha(String name) {
        return name.chars().allMatch(Character::isLetter);
    }

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isFullNameValid(String fullName) {
        if (fullName.length() <2) {
            return false;
        }

        Pattern p = Pattern.compile("[a-z]+[ ]?[a-z]+$");
        return p.matcher(fullName).find();
    }

}
