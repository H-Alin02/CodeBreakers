package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Questa classe gestisce la riproduzione di suoni in un gioco.
 */
public class SoundPlayer {
    private final SoundEffect[] sounds;
    private int count = 0;
    private int maxCount;
    private FileHandle soundFile;
    private static float volumeFactor = 0.5f;
    private static boolean isMute = false;

    /**
     * Imposta il fattore tra 0 e 1 per cui vengono moltiplicati i volumi di tutti i suoni.
     *
     * @param volumeFactor Il fattore di volume generale.
     */
    public static void setGeneralVolume(float volumeFactor)
    {
        if(volumeFactor < 0) volumeFactor = 0;
        if(volumeFactor > 1) volumeFactor = 1;

        SoundPlayer.volumeFactor = volumeFactor;
    }

    /**
     * Aggiunge un valore al fattore di volume generale.
     *
     * @param difference La quantità da aggiungere al fattore di volume.
     */
    public static void sumGeneralVolume(float difference) {
        setGeneralVolume(volumeFactor + difference);
    }

    /**
     * Crea un oggetto SoundPlayer con durata predefinita di 1 secondo e numero massimo di ripetizioni di 5.
     *
     * @param path Il percorso del file sonoro.
     */
    public SoundPlayer(String path)
        {this(1, 5, path);}

    /**
     * Crea un oggetto SoundPlayer con la durata specificata, il numero massimo di ripetizioni e il percorso del file sonoro.
     *
     * @param duration  La durata di un suono.
     * @param maxCount  Il numero massimo di ripetizioni.
     * @param path      Il percorso del file sonoro.
     */
    public SoundPlayer(float duration, int maxCount, String path)
    {
        this.maxCount = maxCount;
        sounds = new SoundEffect[maxCount];
        soundFile = Gdx.files.internal(path);

        for(int i=0; i<maxCount; i++)
            sounds[i] = new SoundEffect(duration);
    }

    /**
     * Ottieni il fattore di volume generale.
     *
     * @return Il fattore di volume generale.
     */
    public static float getVolumeFactor() {
        return volumeFactor;
    }

    /**
     * Riproduci il suono con il volume specificato.
     *
     * @param volume Il volume del suono.
     */
    public void play(float volume)
    {
        if(isMute) return;

        // se l'effetto sonoro corrente è in esecuzione, passa al prossimo in modo ciclico
        if(sounds[count].isPlaying()) {
            count++;
            if (count >= maxCount)
                count = 0;
        }

        // aumenta il volume in base al valore costante e riproduci il suono
        volume *= volumeFactor;
        sounds[count].play(volume, soundFile);
        System.out.println("current sound index:  " + count);
    }

    /**
     * Aggiorna ogni singolo suono contenuto.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public void update(float delta)
    {
        if(isMute) return;

        for(SoundEffect sound : sounds)
            sound.update(delta);
    }

    /**
     * Effettua il dispose di ogni singolo suono contenuto.
     */
    public void dispose()
    {
        System.out.println("DISPOSING SOUND");

        for(SoundEffect sound : sounds)
            sound.dispose();
    }

    /**
     * Verifica se l'audio è disattivato.
     *
     * @return True se l'audio è disattivato, altrimenti False.
     */
    public static boolean isMute() {return isMute;}

    /**
     * Cambia lo stato di mute dell'audio.
     */
    public static void switchMute() {isMute = ! isMute;}

    /**
     * Imposta lo stato di mute dell'audio.
     *
     * @param value True per disattivare l'audio, False per attivarlo.
     */
    public static void setMute(boolean value) {isMute = value;}
}
