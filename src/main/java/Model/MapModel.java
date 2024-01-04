package Model;

import Model.NPC.NPC;
import Model.NPC.NPCManager;
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

public class MapModel {
    private static MapModel INSTANCE;
    private TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final MapObjects scaledCollisionObjects;
    private NPCManager npcManager;
    private float mapScale = 2.0f;
    private Player player;
    private Array<Interactable> interactables;

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

    public void update(float delta){
        this.player = Player.getInstance();
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
                    interactables.add(new Door(rectObject.getRectangle().x * mapScale,
                            rectObject.getRectangle().y * mapScale,
                            rectObject.getRectangle().width * mapScale,
                            rectObject.getRectangle().height * mapScale));
                }
            }
        }
        else {
            throw new GdxRuntimeException("Object layer 'porte' not found in the map");
        }
    }



    public void render(SpriteBatch spriteBatch, OrthographicCamera camera){
        mapRenderer.setView(camera);
        mapRenderer.render();

        for (Interactable door : interactables){
            door.draw(spriteBatch, this.player);
        }
    }

    public MapObjects getScaledCollisionObjects() {
        return scaledCollisionObjects;
    }

    public Array<Interactable> getInteractables(){
        return this.interactables;
    }

}
