package com.pherodev.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;

import com.pherodev.chatapp.models.ChatMessage;
import com.pherodev.chatapp.models.ChatMessageType;
import com.pherodev.chatapp.models.Conversation;
import com.pherodev.chatapp.models.Person;

import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void personbundle_isCorrect() {
        Person aaron = new Person(UUID.randomUUID().toString(), "Aaron", "kawalsky.aaron@gmail.com");
        Person eric = new Person(UUID.randomUUID().toString(), "Eric", "eric.kawalsky@gmail.com");
        Person daniel = new Person(UUID.randomUUID().toString(), "Daniel", "daniel.kawalsky@gmail.com");

        ChatMessage message = new ChatMessage();
        message.types = new ChatMessageType[] {ChatMessageType.GIF, ChatMessageType.TEXT, ChatMessageType.VIDEO};
        message.text = "Hello dude";
        message.sent = new Date();
        message.authorId = aaron.userId;
        message.authorName = aaron.name;

        Bundle messageBundle = new Bundle();
        messageBundle.putParcelable("KEY", message);
        Intent messageBundleIntent = new Intent();
        messageBundleIntent.putExtra("KEY", messageBundle);
        ChatMessage other = (ChatMessage) messageBundleIntent.getBundleExtra("KEY").getParcelable("KEY");
        assertEquals(message.authorId, other.authorId);
        assertEquals(message.types, other.types);
    }
}