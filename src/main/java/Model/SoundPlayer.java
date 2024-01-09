package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class SoundPlayer {
    private SoundEffect[] sounds = new SoundEffect[5];
    private int count = 0;
    private FileHandle soundFile;

    public SoundPlayer(String path)
    {
        this(1, path);
    }
    public SoundPlayer(float duration, String path)
    {
        soundFile = Gdx.files.internal(path);

        sounds[0] = new SoundEffect(duration);
        sounds[1] = new SoundEffect(duration);
        sounds[2] = new SoundEffect(duration);
        sounds[3] = new SoundEffect(duration);
        sounds[4] = new SoundEffect(duration);
    }

    public void play(float volume)
    {
        if(sounds[count].isPlaying()) {
            count++;
            if (count == 5)
                count = 0;
        }

        System.out.println("current sound index:  " + count);
        sounds[count].play(volume, soundFile);
    }

    public void update(float delta)
    {
        sounds[0].update(delta);
        sounds[1].update(delta);
        sounds[2].update(delta);
        sounds[3].update(delta);
        sounds[4].update(delta);
    }

    public void dispose()
    {
        System.out.println("DISPOSING SOUND");

        sounds[0].dispose();
        sounds[1].dispose();
        sounds[2].dispose();
        sounds[3].dispose();
        sounds[4].dispose();
    }
}
