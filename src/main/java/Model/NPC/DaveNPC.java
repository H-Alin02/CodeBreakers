package Model.NPC;

import Model.Interactable;
import Model.Object.ObjectCreator;
import Model.Object.ObjectManager;
import Model.Player;
import Model.SoundPlayer;
import View.Hud.NPCObserver;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.List;

/**
 * Questa classe rappresenta il personaggio non giocante (NPC) "Dave" nel contesto del gioco.
 * Dave è un istruttore all'interno della base segreta, che fornisce al giocatore indicazioni
 * e missioni durante il corso del gioco.
 * @author Alin Marian Habasescu
 */
public class DaveNPC implements NPC , Interactable {
    private Vector2 position;
    private TextureRegion texture;
    private Texture interactTexture;
    private static final SoundPlayer buttonClickSound = new SoundPlayer("sound_effects/abs-confirm-1.mp3");
    private NPCObserver observer;
    private boolean hasTalked = false;
    private List<String> dialogues;
    private List<Vector2> positions;
    private int currentIndex;
    private ObjectManager objectManager;

    /**
     * Costruttore che inizializza la posizione e le risorse grafiche di Dave NPC.
     *
     * @param position La posizione iniziale di Dave NPC nel mondo di gioco.
     */
    public DaveNPC(Vector2 position){
        this.position = position;
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("NPC/Dave/Dave.png")));
        interactTexture = new Texture(Gdx.files.internal("map/Interaction/Interaction.png"));
        dialogues = Arrays.asList(
                "Ciao! Sono Dave, il tuo intraprendente istruttore. \nProva a esplorare la base, per iniziare fai un salto nella palestra ",
                "",
                "Benvenuto nella palestra delle arti marziali! \nPuoi allenarti per diventare un supereroe o solo per sfogarti. \nE, oh, sopra ci sono i bagni. \nNon so perché, ma sembra un'informazione importante.",
                "",
                "Premi [K] oppure [tasto sinistro del mouse] e sfoga la tua rabbia \nsul povero manichino. Poi vieni a trovarmi nella zona relax, perché dopo una lotta, \ntutti abbiamo bisogno di un po' di relax... o forse anche prima.",
                "",
                "Eccoti nella zona relax. Rilassati, respira profondamente, \ne rifletti sul fatto che salvare il mondo è un lavoro duro.",
                "",
                "Ora segui la strada , mi troverai davanti al poligono ",
                "",
                "Ah dimenticavo , premi [SHIFT] per correre",
                "",
                "Ora, a sinistra troverai munizioni. \nRaccoglile, e poi unisciti a me nella stanza a destra. \nMa attenzione, non ti perdonerò se perdi quelle munizioni!",
                "",
                "Questo è il Poligono! \nPremi [SPAZIO] o [tasto destro del mouse] e fai fuoco sui bersagli.",
                "",
                "Benvenuto nella nostra tana, dove mettiamo a punto missioni segrete, \nintrecci intriganti e piani che potrebbero essere definiti \"interessanti\", \nse non addirittura geniali.",
                "...",
                "Appena hai finito di guardarti \nun pò attorno, unisciti a noi nella sala riunioni. \nAbbiamo una missione importante da discutere",
                "",
                "Abbiamo ricevuto un messaggio proveniente dal nostro informatore \nall'interno della base segreta numero 4.",
                "Il messaggio non è molto chiaro ma siamo riusciti \na capire solo due parole : \"Aiuto\" e \"Fanatici\". ",
                "Chiaramente il nostro informatore è in pericolo : il tuo incarico\n è di salvarlo e scoprire che cosa sta succedendo nella base.",
                "Vai nella stanza in basso , le scale ti porteranno attraverso\nun tunnel creato da noi ,direttamente nel sotterraneo della base.",
                "Spero di rivederti fuori da lì , vai ora \nnon c'è altro tempo da perdere !"
        );
        positions = List.of(
                position, //Posizione iniziale
                new Vector2(832, 2304), //dentro la palestra
                new Vector2(832, 2304),
                new Vector2(640,1984),//davanti ai manichini
                new Vector2(640,1984),
                new Vector2(640,1152),//zona relax
                new Vector2(640,1152),
                new Vector2(1152,1024),//fuori dalla zona relax
                new Vector2(1152,1024),
                new Vector2(1472,1472),// mi ricorda lo sprint
                new Vector2(1472,1472),
                new Vector2(3200,2240), //tra poligono e stanza munizioni
                new Vector2(3200,2240),
                new Vector2(3328,2688), //poligono
                new Vector2(3328,2688),
                new Vector2(3392,1792),//Entrata sala PC
                new Vector2(3392,1792),//Entrata sala PC
                new Vector2(3392,1792),//Entrata sala PC
                new Vector2(3392,1792),//Entrata sala PC
                new Vector2(1856,2048)
        );
        currentIndex = 0;
    }

    /**
     * Gestisce l'interazione tra il giocatore e Dave NPC. Aggiorna la posizione di Dave
     * e avvia il dialogo corrispondente.
     *
     * @param player Il giocatore che sta interagendo con Dave NPC.
     */
    @Override
    public void interact(Player player) {
        buttonClickSound.play(0.1f);
        if(currentIndex < positions.size())
            this.position = positions.get(currentIndex);
        talk();
        if(currentIndex < dialogues.size()-1)
            currentIndex++;
        if(currentIndex == 23){
            objectManager.addObject(new ObjectCreator().createObject("key",(int)position.x-128,(int)position.y));
        }
    }

    /**
     * Aggiunge un osservatore (observer) a Dave NPC.
     *
     * @param observer L'osservatore da aggiungere.
     */
    @Override
    public void addObserver(NPCObserver observer) {
        this.observer = observer;
        System.out.println("Added observer for Dave : " + observer);
    }


    @Override
    public void update(float delta) {

    }

    /**
     * Imposta l'oggetto ObjectManager associato a Dave NPC.
     *
     * @param objectManager L'ObjectManager da associare a Dave NPC.
     */
    @Override
    public void addObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    /**
     * Notifica l'osservatore con un messaggio specificato.
     *
     * @param message Il messaggio da inviare agli osservatori.
     */
    private void notifyObservers(String message) {
        observer.onNPCTalk(message);
    }

    /**
     * Restituisce la posizione corrente di Dave NPC nel mondo di gioco.
     *
     * @return La posizione di Dave NPC come oggetto Vector2.
     */
    @Override
    public Vector2 getPosition() {
        return new Vector2(position.x+ texture.getRegionHeight()/2,position.y+texture.getRegionHeight()/2);
    }

    @Override
    public boolean isCollision(float x, float y, float width, float height) {
        return false;
    }

    /**
     * Disegna la texture di Dave NPC e l'indicatore di interazione se il giocatore è nel raggio di interazione.
     *
     * @param batch  Il batch per disegnare gli elementi grafici.
     * @param player Il giocatore nel mondo di gioco.
     */
    @Override
    public void draw(SpriteBatch batch, Player player) {
        batch.draw(texture,position.x,position.y,texture.getRegionWidth()*3,texture.getRegionHeight()*3);

        if (isPlayerInRange(player.getHitBox().x+ player.getHitBox().width/2,
                player.getHitBox().y +player.getHitBox().height/2,
                150)) {
            float messageX = position.x + 10;
            float messageY = position.y + texture.getRegionHeight()*2;

            batch.draw(interactTexture, messageX, messageY, 96, 96);
        }else if (!hasTalked) {
            observer.onNPCFinishedTalk();
            hasTalked = true; // Imposta la variabile a true dopo la chiamata
        }
    }

    /**
     * Reimposta l'indice di dialogo corrente a zero e riporta la posizione di Dave NPC alla posizione iniziale.
     */
    @Override
    public void reset() {
        this.currentIndex = 0;
        this.position = positions.get(currentIndex);
    }

    private boolean isPlayerInRange(float playerX, float playerY, float range) {
        float distance = new Vector2(playerX, playerY).dst(position.x + texture.getRegionHeight()/2, position.y + texture.getRegionHeight()/2);
        return distance <= range;
    }

    /**
     * Avvia il dialogo corrente di Dave NPC se ci sono ancora dialoghi disponibili.
     */
    @Override
    public void talk() {
        if (currentIndex < dialogues.size()) {
            hasTalked = false;
            notifyObservers("Dave : " + dialogues.get(currentIndex));
        }
    }
}
