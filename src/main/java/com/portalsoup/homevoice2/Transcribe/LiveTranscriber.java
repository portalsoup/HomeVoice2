package com.portalsoup.homevoice2.Transcribe;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class LiveTranscriber {

    private final LiveSpeechRecognizer recognizer;
    private Thread transcriber;

    private PipedOutputStream out;
    private PipedInputStream in;

    private final Runnable runner;

    public LiveTranscriber() throws IOException {

        in = new PipedInputStream(1024 * 1024);
        out = new PipedOutputStream(in);

        Configuration configuration = new Configuration();

        // Set path to acoustic model.
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        // Set path to dictionary.
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        // Set language model.
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        recognizer = new LiveSpeechRecognizer(configuration);

        runner = () -> {
            recognizer.startRecognition(true);
            System.out.println("Begin recording...");
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                SpeechResult result = recognizer.getResult();
                try {
                    out.write(result.getHypothesis().getBytes());
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            recognizer.stopRecognition();
            System.out.println("Finished recording.");
        };
    }

    public void startRecognition() {
        if (transcriber != null && transcriber.isAlive()) {
            throw new RuntimeException("I am already transcribing.");
        }

        transcriber = new Thread(runner);

        transcriber.start();
    }

    public void stopRecognition() {
        transcriber.interrupt();
        try {
            transcriber.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNextPhrase() {
        startRecognition();
        SpeechResult result = recognizer.getResult();
        stopRecognition();
        return result.getHypothesis();
    }

    public PipedInputStream getInputStream() {
        return in;
    }

}
