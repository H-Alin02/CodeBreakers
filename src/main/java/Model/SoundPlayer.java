package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class SoundPlayer {
    private SoundEffect[] sounds = new SoundEffect[5];
    private int count = 0;
    private FileHandle soundFile;
    private static float volumeFactor = 1f;

    // Imposta il fattore tra 0 e 1 per cui vengono moltiplicati i volumi di tutti i suoni
    public static void setVolumeFactor(float value)
        {volumeFactor = value;}

    // La durata dei suoni è di un secondo se non specificata
    public SoundPlayer(String path)
        {this(1, path);}

    // Costruttore che imposta la durata di un suono e il path del file sonoro
    public SoundPlayer(float duration, String path)
    {
        soundFile = Gdx.files.internal(path);

        sounds[0] = new SoundEffect(duration);
        sounds[1] = new SoundEffect(duration);
        sounds[2] = new SoundEffect(duration);
        sounds[3] = new SoundEffect(duration);
        sounds[4] = new SoundEffect(duration);
    }

    // riproduci il suono con il volume dato
    public void play(float volume)
    {
        // se l'effetto sonoro corrente è in esecuzione, passa al prossimo in modo ciclico
        if(sounds[count].isPlaying()) {
            count++;
            if (count == 5)
                count = 0;
        }

        System.out.println("current sound index:  " + count);

        // aumenta il volume in base al valore costante e riproduci il suono
        volume *= volumeFactor;
        sounds[count].play(volume, soundFile);
    }

    // fai l'update di ogni singolo suono contenuto
    public void update(float delta)
    {
        sounds[0].update(delta);
        sounds[1].update(delta);
        sounds[2].update(delta);
        sounds[3].update(delta);
        sounds[4].update(delta);
    }

    // fai il dispose di ogni singolo suono contenuto
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
