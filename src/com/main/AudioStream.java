package com.main;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


public class AudioStream {
    private static String filePath;
    public final Game game;
    public AudioStream.MUSICSTATE musicState = MUSICSTATE.Initialized;
    float volumeLevel = 0;
    private boolean muted = false;
    private Long currentFrame;
    private Clip audioStream;
    private AudioInputStream audioInputStream;

    public AudioStream(String path, Game game) {
        this.game = game;
            filePath = path;
            try {
                InputStream inputStream = getClass().getResourceAsStream(filePath);
                if (inputStream == null) {
                    throw new RuntimeException("Audio file not found: " + filePath);
                }
                BufferedInputStream bufferedStream = new BufferedInputStream(inputStream);
                audioInputStream = AudioSystem.getAudioInputStream(bufferedStream);
            } catch (UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }

            try {
                audioStream = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

            try {
                audioStream.open(audioInputStream);
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
            audioStream.loop(Clip.LOOP_CONTINUOUSLY);
            this.changeVolumeOfAudioStream(-19);
            this.pauseAudioStream();
    }

    public boolean isMuted() {
        return this.muted;
    }

    public void reverseMuteState() {
        synchronized (game) {
            this.muted = !this.muted;
            if (this.muted) {
                pauseAudioStreamNoState();
            } else {
                if (this.musicState == MUSICSTATE.Playing) {
                    this.playAudioStream();
                }
            }


        }
    }

    public void reverseAudioStream() {
        synchronized (game) {

            if (this.muted) {
                if (this.musicState == MUSICSTATE.Playing) this.musicState = MUSICSTATE.Paused;
                else this.musicState = MUSICSTATE.Playing;
            } else if (this.musicState == MUSICSTATE.Playing) {
                pauseAudioStream();
            } else playAudioStream();
        }
    }

    public void changeVolumeOfAudioStream(float volume) {
        synchronized (game) {
            FloatControl gainControl = (FloatControl) audioStream.getControl(FloatControl.Type.MASTER_GAIN);
            volumeLevel += volume;
            if (volumeLevel < -80) {
                volumeLevel = -80;
            } else if (volumeLevel > 6) volumeLevel = 6;
            gainControl.setValue(volumeLevel);
        }
    }

    public float getCurrentVolume() {
        synchronized (game) {
            FloatControl gainControl = (FloatControl) audioStream.getControl(FloatControl.Type.MASTER_GAIN);
            return gainControl.getValue();
        }
    }

    public void playAudioStream() {
        synchronized (game) {
            audioStream.loop(Clip.LOOP_CONTINUOUSLY);
            audioStream.start();
            musicState = MUSICSTATE.Playing;
        }
    }

    public void pauseAudioStreamNoState() {
        synchronized (game) {
            if (musicState == MUSICSTATE.Paused) return;
            this.currentFrame = this.audioStream.getMicrosecondPosition();
            audioStream.stop();
        }
    }

    public void pauseAudioStream() {
        synchronized (game) {
            if (musicState == MUSICSTATE.Paused) return;
            this.currentFrame = this.audioStream.getMicrosecondPosition();
            audioStream.stop();
            musicState = MUSICSTATE.Paused;

        }
    }

    public void resumeAudioStream() {
        synchronized (game) {
            if (musicState == MUSICSTATE.Playing) return;
            audioStream.close();
            resetAudioStream();
            audioStream.setMicrosecondPosition(currentFrame);
            this.playAudioStream();

        }
    }

    public void restartAudioStream() {
        synchronized (game) {
            audioStream.stop();
            audioStream.close();
            resetAudioStream();
            currentFrame = 0L;
            audioStream.setMicrosecondPosition(0);
            this.playAudioStream();
        }
    }

    public void stopAudioStream() {
        synchronized (game) {
            currentFrame = 0L;
            audioStream.stop();
            audioStream.close();
        }
    }

    public void resetAudioStream() {
        synchronized (game) {
            try {
                InputStream inputStream = getClass().getResourceAsStream(filePath);
                if (inputStream == null) {
                    throw new RuntimeException("Audio file not found: " + filePath);
                }
                BufferedInputStream bufferedStream = new BufferedInputStream(inputStream);
                audioInputStream = AudioSystem.getAudioInputStream(bufferedStream);
            } catch (UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
            try {
                audioStream.open(audioInputStream);
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
            audioStream.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }


    public enum MUSICSTATE {
        Initialized, Playing, Paused,
    }

}