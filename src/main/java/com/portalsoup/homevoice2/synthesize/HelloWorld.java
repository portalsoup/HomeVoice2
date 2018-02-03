package com.portalsoup.homevoice2.synthesize;

import com.portalsoup.homevoice2.bot.Conversation;

import java.io.IOException;

public class HelloWorld implements Command {
    @Override
    public void run(Conversation conversation, String text) throws IOException {
        conversation.sayStatement("Hello world everybody holy shit what am i");
    }

    @Override
    public boolean isMatch(String text) {
        return text.contains("say") && text.contains("hello");
    }
}
