package com.pherodev.chatapp.models;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;
import java.util.ArrayList;

public class Person implements Parcelable {

    public String userId;
    public String name;
    public String email;
    public URL profilePictureURL;
    public String bio;
    public ArrayList<String> conversationIds;
    public Location location;
    public String status;

    // Bundle keys that are just the names of member variables
    private static String ID_KEY = "userId";
    private static String NAME_KEY = "name";
    private static String EMAIL_KEY = "email";
    private static String PPURL_KEY = "profilePictureURL";
    private static String BIO_KEY = "bio";
    private static String CONVERSATIONS_KEY = "conversations";
    private static String STATUS_KEY = "status";
    private static String LOCATION_KEY = "location";

//    private String bio;
//    private ArrayList<Issue> issues;
//    private ArrayList<Report> reports;

    // For Firebase
    public Person() { }

    public Person(String userId, String name, String email)
    {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.conversationIds = new ArrayList<>();
    }

    // For Parcelable
    private Person(Parcel in) {
        Bundle personBundle = in.readBundle(getClass().getClassLoader());
        this.userId = personBundle.getString(ID_KEY);
        this.name = personBundle.getString(NAME_KEY);
        this.email = personBundle.getString(EMAIL_KEY);
        this.bio = personBundle.getString(BIO_KEY);
        this.conversationIds = personBundle.getStringArrayList(CONVERSATIONS_KEY);
        this.status = personBundle.getString(STATUS_KEY);
    }

    public static final Creator<Person> CREATOR
            = new Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    // Parcelable implementation methods

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle personBundle = new Bundle();
        personBundle.putString(ID_KEY, userId);
        personBundle.putString(NAME_KEY, name);
        personBundle.putString(EMAIL_KEY, email);
        personBundle.putSerializable(PPURL_KEY, profilePictureURL);
        personBundle.putString(BIO_KEY, bio);
        personBundle.putStringArrayList(CONVERSATIONS_KEY, conversationIds);
        personBundle.putString(STATUS_KEY, status);
        personBundle.putParcelable(LOCATION_KEY, location);
        dest.writeBundle(personBundle);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public URL getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(URL profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ArrayList<String> getConversationIds() {
        return conversationIds;
    }

    public void setConversationIds(ArrayList<String> conversationIds) {
        this.conversationIds = conversationIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


}
