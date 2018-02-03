package com.portalsoup.homevoice2.synthesize;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Synthesizer {

    public void Synthesizer() {

    }

    public void synthesize(String text) {
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice("kevin16");
        voice.allocate();
        voice.speak(text);
    }
}
