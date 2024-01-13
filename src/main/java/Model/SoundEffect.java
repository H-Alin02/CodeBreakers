package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

/**
 * La classe SoundEffect gestisce la riproduzione di effetti sonori in un'applicazione LibGDX.
 */
public class SoundEffect {

    private Sound sound;
    private boolean isPlaying = false;
    private float passedTime = 0;
    private float duration;

    /**
     * Costruttore della classe SoundEffect.
     *
     * @param duration La durata del suono in secondi.
     */
    public SoundEffect(float duration)
    {
        this.duration = duration;
    }

    /**
     * Verifica se il suono è attualmente in riproduzione.
     *
     * @return true se il suono è in riproduzione, false altrimenti.
     */
    public boolean isPlaying()
        {return  isPlaying;}

    /**
     * Riproduce il suono con il volume specificato.
     *
     * @param volume     Il volume del suono (compreso tra 0 e 1).
     * @param soundFile  Il gestore del file sonoro da riprodurre.
     */
    public void play(float volume, FileHandle soundFile)
    {
        System.out.println("PLAYING SOUND");

        if(isPlaying)
            dispose();

        // sound deve essere dichiarato ogni volta che si vuole riprodurre il suono di nuovo,
        // perché "dispose" blocca il suono permanentemente
        sound = Gdx.audio.newSound(soundFile);
        sound.play(volume);
        isPlaying = true;
    }

    /**
     * Aggiorna lo stato del suono in base al tempo trascorso.
     *
     * @param delta Il tempo trascorso dall'ultimo frame.
     */
    public void update(float delta)
    {
        if(passedTime > duration)
            dispose();

        if(isPlaying)
            passedTime += delta;
    }

    /**
     * Annulla manualmente la riproduzione del suono, liberando le risorse associate.
     */
    public void dispose()
    {
        System.out.println("DISPOSING SOUND");

        isPlaying = false;
        passedTime = 0;
        sound.dispose();
    }

}
