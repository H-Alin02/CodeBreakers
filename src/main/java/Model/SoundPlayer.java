package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class SoundPlayer {
    private Sound sound;
    private float passedTime;
    private boolean isPlaying;
    private float duration;

    //Il file da cui prendere il suono
    private FileHandle soundFile;

    // costruttore che prende il path del file audio e la sua durata
    public SoundPlayer(float duration, String path)
    {
        passedTime = 0;
        isPlaying = false;
        soundFile = Gdx.files.internal(path);
        this.duration = duration;
    }

    // se la durata non è specificata, sarà di 3 secondi di default
    public SoundPlayer(String path)
        {this(3, path);}

    public boolean isPlaying()
        {return  isPlaying;}

    public void setDuration(float duration)
        {this.duration = duration;}

    public void play(float volume)
    {
        // sound deve essere dichiarato ogni volta che si vuole riprodurre il suono di nuovo,
        // perché "dispose" blocca il suono permanentemente
        sound = Gdx.audio.newSound(soundFile);

        sound.play(volume);
        isPlaying = true;

        System.out.println("PLAYING SOUND");
    }

    //si deve sempre un update per ogni frame per ciascun suono,
    // altrimenti vengono sprecate risorse senza motivo
    public void update(float delta)
    {
        if(passedTime > duration)
        {
            System.out.println("DISPOSING SOUND");
            System.out.println("PASSED TIME: " + passedTime);

            isPlaying = false;
            passedTime = 0;
            sound.dispose();
        }

        if(isPlaying)
            passedTime += delta;
    }

    public void setPitch(float pitch)
    {
        sound.setPitch(0, pitch);
    }

    //funzione per annullare manualmente la riproduzione del suono
    public void dispose()
        {sound.dispose();}
}
