package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class SoundEffect {

    private Sound sound;
    private boolean isPlaying = false;
    private float passedTime = 0;
    private float duration;

    // costruttore che prende il path del file audio e la sua durata
    public SoundEffect(float duration)
    {
        this.duration = duration;
    }

    public boolean isPlaying()
        {return  isPlaying;}

    // viene dato il volume e il file sonoro per poi essere riprodotto
    public void play(float volume, FileHandle soundFile)
    {
        if(isPlaying)
            dispose();

        // sound deve essere dichiarato ogni volta che si vuole riprodurre il suono
        // di nuovo, perché "dispose" blocca il suono permanentemente
        sound = Gdx.audio.newSound(soundFile);
        sound.play(volume);
        isPlaying = true;
    }

    //si deve sempre un update per ogni frame per ciascun suono,
    // altrimenti vengono sprecate risorse senza motivo
    public void update(float delta)
    {
        if(passedTime > duration)
            dispose();

        if(isPlaying)
            passedTime += delta;
    }

    //funzione per annullare manualmente la riproduzione del suono
    public void dispose()
    {
        System.out.println("DISPOSING SOUND");

        isPlaying = false;
        passedTime = 0;
        sound.dispose();
    }

}
