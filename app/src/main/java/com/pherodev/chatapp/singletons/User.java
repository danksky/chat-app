package com.pherodev.chatapp.singletons;

import com.google.firebase.auth.FirebaseAuth;
import com.pherodev.chatapp.models.Person;

public class User {

    private static User instance;

    public Person userPerson;
    private FirebaseAuth firebaseAuth;

    public User(Person userPerson)
    {
        this.userPerson = userPerson;
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public static User getInstance()
    {
        return instance;
    }

    public static void setInstance(Person userPerson)
    {
        instance = new User(userPerson);
    }

}
