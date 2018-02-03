package com.portalsoup.homevoice2.synthesize;

import com.portalsoup.homevoice2.bot.Conversation;

import java.io.IOException;

public interface Command {
    void run(Conversation conversation, String text) throws IOException;

    boolean isMatch(String text);


}
