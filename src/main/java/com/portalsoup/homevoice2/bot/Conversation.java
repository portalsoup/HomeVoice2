package com.portalsoup.homevoice2.bot;

import com.portalsoup.homevoice2.Transcribe.LiveTranscriber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Conversation {

    private static int BUF_SIZE = 1024 * 1024;
    private final Party person;

    public Conversation() throws IOException {
        person = new Party(BUF_SIZE);
    }

    public BufferedReader getListener() {
        return person.getListener();
    }

    public void sayStatement(String statement) throws IOException {
        person.listen(statement);
    }

    public String askRequest(String request) throws IOException {
        person.listen(request);
        LiveTranscriber transcriber = new LiveTranscriber();

        PipedInputStream inputStream = transcriber.getInputStream();
        return transcriber.getNextPhrase();
    }


    private static class Party {
        private final int buf;
        private final PipedInputStream in;
        private final PipedOutputStream out;

        public Party(int buf) throws IOException {
            this.buf = buf;
            in = new PipedInputStream(buf);
            out = new PipedOutputStream(in);
        }

        public BufferedReader getListener() {
            return new BufferedReader(new InputStreamReader(in), buf);
        }

        public void listen(String message) throws IOException {
            out.write(message.getBytes());
        }
    }

}
