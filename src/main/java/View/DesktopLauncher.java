package View;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] args) {

        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            // Aggiungi l'opzione del thread per macOS
            args = new String[]{"-XstartOnFirstThread"};
        }

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setIdleFPS(60);
        config.useVsync(true);
        config.setTitle("Tutorial");
        config.setWindowedMode(1152 , 864);
        config.setResizable(false);
        //config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());

        new Lwjgl3Application(new Boot(), config);
    }
}
