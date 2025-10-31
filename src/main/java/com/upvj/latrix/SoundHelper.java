package com.upvj.latrix;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

public class SoundHelper {

    public enum Sound {
        LASER, EXPLOSION, BACKGROUND
        // add your sound names here
    }

    private static final Map<Sound, Clip> clips = new EnumMap<>(Sound.class);
    private static final Map<Sound, Clip> loopedClips = new EnumMap<>(Sound.class);

    // Call this once at startup
    public static void init() {
        loadSound(Sound.LASER, "Laser.wav");
        loadSound(Sound.EXPLOSION, "Explosion.wav");
        loadSound(Sound.BACKGROUND, "Background.wav");
    }

    private static void loadSound(Sound sound, String fileName) {
        try {
            URL url = SoundHelper.class.getResource( "/com/upvj/latrix/sounds/" + fileName);
            System.out.println();
            if (url == null) {
                System.err.println("Sound file not found: " + fileName);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clips.put(sound, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void play(Sound sound) {
        Clip clip = clips.get(sound);
        if (clip == null) return;
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public static void playLooped(Sound sound) {
        Clip clip = clips.get(sound);
        if (clip == null) return;
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        loopedClips.put(sound, clip);
        System.out.println("Looped sound started: " + sound);
    }

    public static void stop(Sound sound) {
        Clip clip = loopedClips.get(sound);
        if (clip != null) {
            clip.stop();
            loopedClips.remove(sound);
            System.out.println("Looped sound stopped: " + sound);
        }
    }

    // Optional: stop all sounds
    public static void stopAll() {
        for (Clip clip : clips.values()) {
            clip.stop();
        }
        loopedClips.clear();
    }
}
