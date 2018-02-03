package com.portalsoup.homevoice2;

import com.portalsoup.homevoice2.Transcribe.LiveTranscriber;
import com.portalsoup.homevoice2.bot.Roboto;
import com.portalsoup.homevoice2.synthesize.HelloWorld;

import java.io.IOException;

public class Main {

    LiveTranscriber transcriber;
    Roboto roboto;

    public static void main(String args[]) throws Exception {
        Main main = new Main();

        main.getRoboto().initNewConversation();

    }

    public Main() throws IOException {
        this.transcriber = new LiveTranscriber();
        this.roboto = new Roboto("robot");
        roboto.addCommand(new HelloWorld());
    }

    public LiveTranscriber getTranscriber() {
         return transcriber;
    }

    public Roboto getRoboto() {
        return roboto;
    }
}
