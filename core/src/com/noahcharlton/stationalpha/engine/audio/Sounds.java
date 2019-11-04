package com.noahcharlton.stationalpha.engine.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.List;

public class Sounds {

    private static final List<Sound> sounds = new ArrayList<>();

    public static final Sound ERROR = loadSound("error.wav");
    public static final Sound CLICK = loadSound("click.ogg");

    private static Sound loadSound(String path) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/" + path));

        sounds.add(sound);

        return sound;
    }

    public static void init(){}

    public static void dispose(){
        sounds.forEach(Sound::dispose);
    }
}
