package com.pherodev.chatapp.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ChatMessage implements Parcelable {

    public String authorId;
    public String authorName;
    public Date sent;
    public String text;
    // TODO: Implement all the fields that a message might have
    public ChatMessageType[] types;

    private static String AUTHOR_ID_KEY = "authorId";
    private static String AUTHOR_NAME_KEY = "authorName";
    private static String SENT_KEY = "time_sent";
    private static String TEXT_KEY = "text";
    private static String TYPES_KEY = "types";

    // For Firebase

    public ChatMessage() {}

    private ChatMessage(Parcel in) {
        Bundle dateMessageBundle = in.readBundle(getClass().getClassLoader());
        authorId = dateMessageBundle.getString(AUTHOR_ID_KEY);
        authorName = dateMessageBundle.getString(AUTHOR_NAME_KEY);
        sent = (Date) dateMessageBundle.getSerializable(SENT_KEY);
        text = dateMessageBundle.getString(TEXT_KEY);
        // TODO: Make sure this types thing works
        types = (ChatMessageType[]) dateMessageBundle.getSerializable(TYPES_KEY);
    }

    public static final Creator<ChatMessage> CREATOR
            = new Creator<ChatMessage>() {
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }

        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle dateMessageBundle = new Bundle();
        dateMessageBundle.putString(AUTHOR_ID_KEY, authorId);
        dateMessageBundle.putString(AUTHOR_NAME_KEY, authorName);
        dateMessageBundle.putSerializable(SENT_KEY, sent);
        dateMessageBundle.putString(TEXT_KEY, text);
        dateMessageBundle.putSerializable(TYPES_KEY, types);

        dest.writeBundle(dateMessageBundle);
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ChatMessageType[] getTypes() {
        return types;
    }

    public void setTypes(ChatMessageType[] types) {
        this.types = types;
    }

}


