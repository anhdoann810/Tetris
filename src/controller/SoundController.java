package controller;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundController {
    private Clip bgmClip;
    private boolean bgmMuted = false;
    private boolean sfxMuted = false;

    public void playBGM(String resourcePath) {
        stopBGM();
        try {
            URL url = getClass().getResource(resourcePath);
            if (url == null) {
                System.err.println("BGM not found: " + resourcePath);
                return;
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(ais);
            if (!bgmMuted) {
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopBGM() {
        if (bgmClip != null) {
            bgmClip.stop();
            bgmClip.close();
            bgmClip = null;
        }
    }

    public void pauseBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }

    public void resumeBGM() {
        if (bgmClip != null && !bgmClip.isRunning() && !bgmMuted) {
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void playSFX(String resourcePath) {
        if (sfxMuted)
            return;
        try {
            URL url = getClass().getResource(resourcePath);
            if (url == null) {
                System.err.println("SFX not found: " + resourcePath);
                return;
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void toggleBGM() {
        bgmMuted = !bgmMuted;
        if (bgmMuted) {
            pauseBGM();
        } else {
            resumeBGM();
        }
    }

    public void toggleSFX() {
        sfxMuted = !sfxMuted;
    }

    public boolean isBgmMuted() {
        return bgmMuted;
    }

    public boolean isSfxMuted() {
        return sfxMuted;
    }
}
