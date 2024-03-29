package Model;

import Model.NPC.NPC;
import Model.NPC.NPCManager;
import Model.Object.ObjectManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * La classe rappresenta il modello della mappa nel gioco, gestendo il caricamento,
 * il rendering e l'aggiornamento degli oggetti sulla mappa.
 * @author Alin Marian Habasescu
 * @author Gabriele Zimmerhofer
 */
public class MapModel {
    private static MapModel INSTANCE;
    private TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final MapObjects scaledCollisionObjects;
    private NPCManager npcManager;
    private float mapScale = 2.0f;
    private Player player;
    private Array<Interactable> interactables;
    private static Rectangle stairHitbox = new Rectangle(2470,970,64,64);

    /**
     * Ottiene un'istanza singleton della classe MapModel.
     *
     * @return Un'istanza di MapModel.
     */
    public static MapModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MapModel();
        }
        return INSTANCE;
    }

    private MapModel(){
        //Load the map

        map = new TmxMapLoader().load("map/MappaNUOVA.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map , 2.0f);

        //Get the collision objects from the "collisioni" object layer
        MapLayer collisioniLayer = map.getLayers().get("collisioni");
        if(collisioniLayer != null){
            scaledCollisionObjects = scaleCollisionObjects(collisioniLayer.getObjects());
        }else {
            throw new GdxRuntimeException("Object layer 'collisioni'not found in the map");
        }

        // Carica le porte dalla mappa
        loadDoors();

        npcManager = new NPCManager();
        //Aggiunge gli NPC alla mappa
        npcManager.initializeNPCs();

        for(NPC npc : npcManager.getNPC()){
            interactables.add((Interactable)npc);
        }

    }

    /**
     * Aggiorna lo stato della mappa.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public void update(float delta){
        this.player = Player.getInstance();
        if(player.getHitBox().overlaps(stairHitbox)){
            teleportPlayer(8448, 1216);
            MusicPlayer.play("level");
        }
        npcManager.update(delta);
    }

    // Scale the collision objects
    private MapObjects scaleCollisionObjects(MapObjects originalObjects) {
        MapObjects scaledObjects = new MapObjects();

        for (MapObject object : originalObjects) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // Scale the rectangle based on the map scale factor
                float scaledX = Math.round(rect.x * mapScale);
                float scaledY = Math.round(rect.y * mapScale);
                float scaledWidth = Math.round(rect.width * mapScale);
                float scaledHeight = Math.round(rect.height * mapScale);

                // Create a new RectangleMapObject with scaled values
                RectangleMapObject scaledObject = new RectangleMapObject(scaledX, scaledY, scaledWidth, scaledHeight);
                scaledObjects.add(scaledObject);
            }
            // Add additional checks for other object types if needed
        }

        return scaledObjects;
    }

    /**
     * Verifica se c'è una collisione con gli oggetti ridimensionati.
     *
     * @param x      La coordinata x dell'oggetto.
     * @param y      La coordinata y dell'oggetto.
     * @param width  La larghezza dell'oggetto.
     * @param height L'altezza dell'oggetto.
     * @return True se c'è una collisione, altrimenti false.
     */
    public boolean isCollisionWithScaledObjects(float x, float y, float width, float height){
        //Iterate through the objects in the object layer
        for(MapObject object : scaledCollisionObjects){
            if(object instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject)object).getRectangle();

                //Check for collision between player and object
                if( rect.overlaps(new Rectangle(x,y,width,height))){
                    return true; //Collision detected
                }
            }
        }

        // Itera attraverso le porte
        for (Interactable interactable : interactables) {
            if (interactable.isCollision(x, y, width, height)) {
                return true;
            }
        }
        return false;
    }

    private void loadDoors() {
        interactables = new Array<>();
        MapLayer doorsLayer = map.getLayers().get("porte");
        if (doorsLayer != null) {
            for (MapObject object : doorsLayer.getObjects()) {
                if (object instanceof RectangleMapObject rectObject) {
                    boolean bloccata = (boolean)rectObject.getProperties().get("bloccata");
                    interactables.add(new Door(rectObject.getRectangle().x * mapScale,
                            rectObject.getRectangle().y * mapScale,
                            rectObject.getRectangle().width * mapScale,
                            rectObject.getRectangle().height * mapScale, bloccata));
                }
            }
        }
        else {
            throw new GdxRuntimeException("Object layer 'porte' not found in the map");
        }
    }

    /**
     * Esegue il rendering della mappa e degli oggetti interattivi.
     *
     * @param spriteBatch Lo SpriteBatch utilizzato per il rendering.
     * @param camera      La telecamera ortografica utilizzata per la visualizzazione.
     */
    public void render(SpriteBatch spriteBatch, OrthographicCamera camera){
        mapRenderer.setView(camera);
        mapRenderer.render();

        for (Interactable door : interactables){
            door.draw(spriteBatch, this.player);
        }
    }

    /**
     * Ottiene gli oggetti di collisione ridimensionati.
     *
     * @return Gli oggetti di collisione ridimensionati come MapObjects.
     */
    public MapObjects getScaledCollisionObjects() {
        return scaledCollisionObjects;
    }

    /**
     * Ottiene la lista di oggetti interattivi sulla mappa.
     *
     * @return Un array di oggetti interattivi.
     */
    public Array<Interactable> getInteractables(){
        return this.interactables;
    }

    /**
     * Ottiene il gestore degli NPC associato alla mappa.
     *
     * @return Il gestore degli NPC.
     */
    public NPCManager getNpcManager() {
        return npcManager;
    }


    private void teleportPlayer(int x, int y){
        this.player.setPlayerX(x);
        this.player.setPlayerY(y);
    }

    /**
     * Reimposta lo stato di tutti gli oggetti interattivi sulla mappa.
     */
    public void resetMapModel(){
        for(Interactable interactable : interactables){
            interactable.reset();
        }
    }

    /**
     * Imposta l'ObjectManager per tutti gli oggetti interattivi sulla mappa.
     *
     * @param objectManager L'istanza di ObjectManager.
     */
    public void addObjectManager(ObjectManager objectManager){
        for(Interactable interactable : interactables){
            interactable.addObjectManager(objectManager);
        }
    }
}
