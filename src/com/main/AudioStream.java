package com.main;

import com.main.signals.GameSignals;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AudioStream {
    public final Game game;
    public MUSICSTATE musicState = MUSICSTATE.Initialized;
    float volumeLevel = 0;
    private Long currentFrame;
    private Clip audioStream;
    private final Map<AUDIONAME, String> audioFiles = new HashMap<>();
    private AUDIONAME currentAudio = AUDIONAME.Menu;
    private boolean muted = false;

    public enum MUSICSTATE {
        Initialized, Playing, Stopped
    }

    public enum AUDIONAME {
        Menu, Game
    }

    public AudioStream(Game game) {
        this.game = game;
        audioFiles.put(AUDIONAME.Menu, "/resources/Half-Mystery.wav");
        audioFiles.put(AUDIONAME.Game, "/resources/Voxel Revolution.wav");

        GameSignals.AudioAdjusted.connect(this::adjustVolume);
        GameSignals.GameQuit.connect(this::swapAudioStream);
        GameSignals.GameStarted.connect(this::swapAudioStream);
        GameSignals.MuteToggled.connect(this::onToggleMute);

        try {
            audioStream = AudioSystem.getClip();
            audioStream.open(loadAudioInputStream(audioFiles.get(AUDIONAME.Menu)));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        play();
        adjustVolume(-19);
        currentFrame = audioStream.getMicrosecondPosition();
        stop();
    }

    private AudioInputStream loadAudioInputStream(String filePath) throws IOException, UnsupportedAudioFileException {
        InputStream inputStream = getClass().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new RuntimeException("Audio file not found: " + filePath);
        }
        return AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
    }

    public void swapAudioStream() {
        if (currentAudio == AUDIONAME.Menu) {
            currentAudio = AUDIONAME.Game;
        } else {
            currentAudio = AUDIONAME.Menu;
        }
        resetAudioStream(audioFiles.get(currentAudio));
    }

    public void resetAudioStream(String filePath) {
        synchronized (game) {
            audioStream.close();
            try {
                audioStream.open(loadAudioInputStream(filePath));
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
            if (!muted) play();
        }
    }

    private void onToggleMute() {
        if (this.muted) {
            this.muted = false;
            play();
        } else {
            this.muted = true;
            stop();
        }
    }

    public boolean isPlaying() {
        return musicState == MUSICSTATE.Playing;
    }

    private void play() {
        audioStream.loop(Clip.LOOP_CONTINUOUSLY);
        musicState = MUSICSTATE.Playing;
    }

    private void stop() {
        currentFrame = audioStream.getMicrosecondPosition();
        audioStream.stop();
        musicState = MUSICSTATE.Stopped;
    }

    public void startAudioStream() {
        synchronized (game) {
            audioStream.setMicrosecondPosition(currentFrame);
            play();
        }
    }

    public void pauseAudioStream() {
        synchronized (game) {
            if (musicState == MUSICSTATE.Stopped) return;
            stop();
        }
    }

    // For use before closing the application
    public void closeAudioStream() {
        synchronized (game) {
            currentFrame = 0L;
            stop();
            audioStream.close();
        }
    }

    public void adjustVolume(float delta) {
        synchronized (game) {
            FloatControl gainControl = (FloatControl) audioStream.getControl(FloatControl.Type.MASTER_GAIN);
            volumeLevel = Math.clamp(volumeLevel + delta, -80, 6);
            gainControl.setValue(volumeLevel);
        }
    }

    public float getCurrentVolume() {
        synchronized (game) {
            FloatControl gainControl = (FloatControl) audioStream.getControl(FloatControl.Type.MASTER_GAIN);
            return gainControl.getValue();
        }
    }
}