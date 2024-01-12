package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer {
    private final Music music;
    private final float volume;
    private static float volumeFactor = 0.3f;

    public MusicPlayer(float volume, String path)
    {
        this.volume = volume;
        volume *= volumeFactor;

        music = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.setVolume(volume);
        music.setLooping(true);
    }
    public Music getData() {return music;}

    // moltiplica il volume corrente per un valore
    public void multiplyVolume(float value) {
        music.setVolume(music.getVolume() * value);
    }

    // imposta il volume generale per l'istanza specifica
    public void resetVolumeFactor(float volumeFactor) {
        music.setVolume(volume * volumeFactor);
        System.out.println("AAAA " + volume + ", " + volumeFactor);
    }


    private  static boolean isMute = false;

    // Tutte le musiche utilizzate nel gioco
    private static final MusicPlayer tutorialMusic = new MusicPlayer(0.2f, "Music/tutorial_music.mp3");
    private static final MusicPlayer levelMusic = new MusicPlayer(0.1f, "Music/level_music.mp3");
    private static final MusicPlayer mainMenuMusic = new MusicPlayer(0.3f, "Music/main_soundtrack.mp3");

    // Puntatore alla musica da riprodurre nel momento specifico
    public static MusicPlayer currentMusic = mainMenuMusic;

    // imposta la musica attuale in base al nome dato
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

    // Termina la musica attuale
    public static void dispose() {currentMusic.music.dispose();}

    // Imposta il volume generale per tutte le istanze
    public static void setGeneralVolume(float volumeFactor)
    {
        if(volumeFactor<0) volumeFactor = 0;
        if(volumeFactor>1) volumeFactor = 1;

        MusicPlayer.volumeFactor = volumeFactor;

        mainMenuMusic.resetVolumeFactor(volumeFactor);
        tutorialMusic.resetVolumeFactor(volumeFactor);
        levelMusic.resetVolumeFactor(volumeFactor);
    }
    public static void sumGeneralVolume(float difference) {
        setGeneralVolume(volumeFactor + difference);
    }

    public static boolean isPlaying() {
        return currentMusic.music.isPlaying();
    }

    public static void switchMute()
    {
        isMute = !isMute;

        if(isMute)
            currentMusic.multiplyVolume(0);
        else
            currentMusic.resetVolumeFactor(volumeFactor);
    }
    public static boolean isMute() {return isMute;}
    public static void setMute(boolean value)
    {
        isMute = value;

        if(value)
            currentMusic.multiplyVolume(0);
        else
            currentMusic.resetVolumeFactor(volumeFactor);
    }

    public static float getVolumeFactor() {
        return volumeFactor;
    }
}
