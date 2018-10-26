package com.bignerdranch.android.beatbox;

/**
 * Created by Ivo Georgiev(IfChyy)
 * Sound class responsible for storing the asset sound path
 * and its name
 * to represent to the user
 */

public class Sound {

    private String assetPath;
    private String name;
    //integer instead of int to let sound be null value
    private Integer soundId;


    //constructor with given asesetpath
    public Sound(String assetPath) {
        this.assetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        name = filename.replace(".wav", "");
    }
    //--------------------------GETTERS AND SETTERS
    public String getAssetPath() {
        return assetPath;
    }

    public String getName() {
        return name;
    }

    public Integer getSoundId(){
        return soundId;
    }

    public void setSoundId(Integer soundId){
        this.soundId = soundId;
    }


}
