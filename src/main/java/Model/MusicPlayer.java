package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * La classe MusicPlayer gestisce la riproduzione delle tracce musicali nel gioco.
 */
public class MusicPlayer {
    private final Music music;
    private final float volume;
    private static float volumeFactor = 0.3f;

    /**
     * Costruttore della classe MusicPlayer.
     *
     * @param volume Il volume desiderato della traccia musicale.
     * @param path   Il percorso del file della traccia musicale.
     */
    public MusicPlayer(float volume, String path)
    {
        this.volume = volume;
        volume *= volumeFactor;

        music = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.setVolume(volume);
        music.setLooping(true);
    }

    /**
     * Restituisce l'oggetto Music associato a questa istanza di MusicPlayer.
     *
     * @return L'oggetto Music associato.
     */
    public Music getData() {return music;}

    /**
     * Moltiplica il volume corrente per un valore specifico.
     *
     * @param value Il valore con cui moltiplicare il volume corrente.
     */
    public void multiplyVolume(float value) {
        music.setVolume(music.getVolume() * value);
    }

    /**
     * Imposta il volume generale per l'istanza specifica.
     *
     * @param volumeFactor Il fattore di volume da applicare.
     */
    public void resetVolumeFactor(float volumeFactor) {
        music.setVolume(volume * volumeFactor);
        System.out.println("AAAA " + volume + ", " + volumeFactor);
    }


    private  static boolean isMute = false;

    /**
     * Tutte le musiche utilizzate nel gioco.
     */
    private static final MusicPlayer tutorialMusic = new MusicPlayer(0.2f, "Music/tutorial_music.mp3");
    private static final MusicPlayer levelMusic = new MusicPlayer(0.1f, "Music/level_music.mp3");
    private static final MusicPlayer mainMenuMusic = new MusicPlayer(0.3f, "Music/main_soundtrack.mp3");

    /**
     * Puntatore alla musica da riprodurre nel momento specifico.
     */
    public static MusicPlayer currentMusic = mainMenuMusic;

    /**
     * Imposta la musica attuale in base al nome dato.
     *
     * @param musicName Il nome della traccia musicale da riprodurre.
     */
    public static void play(String musicName)
    {
        currentMusic.music.dispose();

        currentMusic = switch(musicName)
        {
            case "main menu" -> mainMenuMusic;
            case "tutorial" -> tutorialMusic;
            case "level" -> levelMusic;
            case "current" -> currentMusic;

            default -> throw new IllegalStateException("This music does not exist: " + musicName);
        };

        currentMusic.music.play();
    }

    /**
     * Termina la musica attuale.
     */
    public static void dispose() {currentMusic.music.dispose();}

    /**
     * Imposta il volume generale per tutte le istanze.
     *
     * @param volumeFactor Il fattore di volume da applicare a tutte le istanze.
     */
    public static void setGeneralVolume(float volumeFactor)
    {
        if(volumeFactor<0) volumeFactor = 0;
        if(volumeFactor>1) volumeFactor = 1;

        MusicPlayer.volumeFactor = volumeFactor;

        mainMenuMusic.resetVolumeFactor(volumeFactor);
        tutorialMusic.resetVolumeFactor(volumeFactor);
        levelMusic.resetVolumeFactor(volumeFactor);
    }

    /**
     * Aggiunge un valore al volume generale per tutte le istanze.
     *
     * @param difference Il valore da aggiungere al volume generale.
     */
    public static void sumGeneralVolume(float difference) {
        setGeneralVolume(volumeFactor + difference);
    }

    /**
     * Verifica se la musica è in riproduzione.
     *
     * @return True se la musica è in riproduzione, false altrimenti.
     */
    public static boolean isPlaying() {
        return currentMusic.music.isPlaying();
    }

    /**
     * Restituisce lo stato della modalità silenziosa (mute).
     *
     * @return True se la modalità silenziosa è attiva, false altrimenti.
     */
    public static boolean isMute() {return isMute;}

    /**
     * Imposta la modalità silenziosa (mute) a un valore specifico.
     *
     * @param value True per attivare la modalità silenziosa, false altrimenti.
     */
    public static void setMute(boolean value)
    {
        isMute = value;

        if(value)
            currentMusic.multiplyVolume(0);
        else
            currentMusic.resetVolumeFactor(volumeFactor);
    }

    /**
     * Restituisce il fattore di volume globale.
     *
     * @return Il fattore di volume globale.
     */
    public static float getVolumeFactor() {
        return volumeFactor;
    }
}
