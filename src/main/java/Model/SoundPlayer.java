package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class SoundPlayer {
    private SoundEffect[] sounds;
    private int count = 0;
    private int maxCount;
    private boolean parallel = true;
    private FileHandle soundFile;
    private static float volumeFactor = 0.5f;

    // Imposta il fattore tra 0 e 1 per cui vengono moltiplicati i volumi di tutti i suoni
    public static void setGlobalVolume(float volumeFactor)
        {SoundPlayer.volumeFactor = volumeFactor;}

    // La durata dei suoni è di un secondo se non specificata
    public SoundPlayer(String path)
        {this(1, 5, path);}

    // Costruttore che imposta la durata di un suono e il path del file sonoro
    public SoundPlayer(float duration, int maxCount, String path)
    {
        this.maxCount = maxCount;
        sounds = new SoundEffect[maxCount];
        soundFile = Gdx.files.internal(path);

        for(int i=0; i<maxCount; i++)
            sounds[i] = new SoundEffect(duration);
    }

    public static float getVolumeFactor() {
        return volumeFactor;
    }

    // riproduci il suono con il volume dato
    public void play(float volume)
    {
        // se l'effetto sonoro corrente è in esecuzione, passa al prossimo in modo ciclico
        if(sounds[count].isPlaying()) {
            count++;
            if (count >= maxCount)
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
        for(SoundEffect sound : sounds)
            sound.update(delta);
    }

    // fai il dispose di ogni singolo suono contenuto
    public void dispose()
    {
        System.out.println("DISPOSING SOUND");

        for(SoundEffect sound : sounds)
            sound.dispose();
    }
}
