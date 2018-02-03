package com.portalsoup.homevoice2.bot;

import com.portalsoup.homevoice2.synthesize.Command;
import com.portalsoup.homevoice2.synthesize.Synthesizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Roboto {

    private List<Command> knownCommands;
    private Synthesizer synthesizer;
    private final String name;

    public Roboto(String name) {
        this.name = name;
        synthesizer = new Synthesizer();
        knownCommands = Collections.synchronizedList(new ArrayList<>());
    }


    public void process(Conversation conversation, String verbalCommand) {
        knownCommands.stream()
                .filter(command -> command.isMatch(verbalCommand))
                .findFirst()
                .ifPresent(command -> {
                    try {
                        BufferedReader reader = conversation.getListener();
                        command.run(conversation, verbalCommand);
                        synthesizer.synthesize(reader.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void initNewConversation() throws IOException {
        Conversation conversation = new Conversation();
        String response = conversation.askRequest("What do you need?");
        process(conversation, response);
    }

    public void addCommand(Command command) {
        knownCommands.add(command);
    }
}
