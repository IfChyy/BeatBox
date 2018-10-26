package com.bignerdranch.android.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivo Georgiev (IfChyy)
 * BeatBox class holding information about ech button and sound it makes
 */

public class BeatBox {
    private static final String TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    //asset manager to access the assets we provided
    private AssetManager assetManager;
    //get the list of sohnds
    private List<Sound> sounds;
    //create a sound pool to play our soudns
    private SoundPool soundPool;

    //create the constructor and get the assets
    public BeatBox(Context context) {
        assetManager = context.getAssets();
        //sound pool (int1, int2, int3) - 1 the max sounds played 5 - if 6 oldest 1 muted
        //2 the sound manager for independent volume settings - the same as games and music
        //the quality of the sample rate converter 0 = ignored
        soundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();

    }

    //loads the assets
    private void loadSounds() {
        String[] soundNames;

        try {
            //init our string array to a list of sounds taken from our asset folder by our asset manager
            soundNames = assetManager.list(SOUNDS_FOLDER);
            Log.d(TAG, "Found " + soundNames.length + " sounds");


        } catch (IOException exception) {
            Log.e(TAG, "loadSounds: ", exception);
            return;
        }
        sounds = new ArrayList<Sound>();
        //populate sounds list with sounds
        for (String filename : soundNames) {
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                sounds.add(sound);
            } catch (Exception ex) {
                Log.d(TAG, "NOT loaded");
            }
        }
    }

    //load sounds into sound pool
    private void load(Sound sound) throws IOException {
        AssetFileDescriptor assetFileDescriptor = assetManager.openFd(sound.getAssetPath());
        int soundId = soundPool.load(assetFileDescriptor, 1);
        sound.setSoundId(soundId);
    }

    //get the sounds
    public List<Sound> getSounds() {
        return sounds;
    }

    //play sound method
    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (sound == null) {
            return;
        }
        //sound id, volume left, volume right, priority(ignored) , loop , playback rate
        soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    //unloading sounds ( cleans up soundpool )
    public void release(){
        soundPool.release();
    }

}
