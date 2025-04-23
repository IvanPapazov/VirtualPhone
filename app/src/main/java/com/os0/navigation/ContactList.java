package com.os0.navigation;

import java.util.ArrayList;

public class ContactList extends ArrayList<Contact> {

    public ContactList() {

    }

    public ContactList(ContactList contacts) {
        super(contacts);
    }

}