package com.os0.navigation;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.Comparator;
import java.util.Locale;

public class Contact {
    private String firstName, lastName, email, address, nameFirst2Letters, phoneNum;

    public Contact(String fN, String lN, String eM, String addrs, String phone) {
        firstName = fN;
        lastName = lN;
        email = eM;
        address = addrs;
        phoneNum = phone;
        nameFirst2Letters = "";
        if(firstName.isEmpty() || phoneNum.isEmpty()) throw new UnsupportedOperationException();
        if(!email.equals("") && !hasValidEmail()) throw new IllegalArgumentException();
        if(!phoneNum.equals("") && !hasValidPhone()) throw new IllegalArgumentException();
        if (firstName.length() == 0) {
            nameFirst2Letters = (lastName.substring(0, 2)).toUpperCase();
        } else if (lastName.length() == 0) {
            nameFirst2Letters = (firstName.substring(0, 2)).toUpperCase();
        } else {
            nameFirst2Letters = (firstName.substring(0, 1) + lastName.substring(0, 1)).toUpperCase();
        }
    }

    public Contact(Contact c){
        firstName = c.getFirstName();
        lastName = c.getLastName();
        email = c.getEmail();
        address = c.getAddress();
        phoneNum = c.getTelNum();
        nameFirst2Letters = "";
        if (firstName.length() == 0) {
            nameFirst2Letters = (lastName.substring(0, 2)).toUpperCase();
        } else if (lastName.length() == 0) {
            nameFirst2Letters = (firstName.substring(0, 2)).toUpperCase();
        } else {
            nameFirst2Letters = (firstName.substring(0, 1) + lastName.substring(0, 1)).toUpperCase();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getNameFirst2Letters() {
        return nameFirst2Letters;
    }

    public String getTelNum() {
        return phoneNum;
    }

    private boolean hasValidEmail() {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean hasValidPhone(){
        for(int i = 0; i < phoneNum.length(); i++) {
            if(phoneNum.charAt(i)<'0' || phoneNum.charAt(i)>'9') return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return firstName + lastName + email + address + phoneNum;
    }

    /*@Override
    public int compareTo(Contact c2) {
        if(!firstName.equals(c2.getFirstName())){
            return firstName.compareTo(c2.getFirstName());
        } else if(!lastName.equals(c2.getLastName())){
            return lastName.compareTo(c2.getLastName());
        }
        return 0;
    }
    */

}
