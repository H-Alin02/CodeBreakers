package Model.Enemies;

import Model.Enemies.Dummy.DummyEnemyCreator;
import Model.Enemies.MetalRobot.MetalRobotCreator;
import Model.Object.ObjectCreator;
import Model.Object.ObjectManager;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code EnemyManager} gestisce la creazione, l'aggiornamento e la rimozione degli
 * oggetti di tipo {@code Enemy} nel gioco. Include inoltre la gestione di creatori specifici per
 * i tipi di nemici (Dummy e MetalRobot) e la possibilità di aggiungere un {@code ObjectManager}.
 * @author Alin Marian Habasescu
 * @author Gabriele Zimmerhofer
 */
public class EnemyManager {
    private final List<Enemy> enemies;
    private DummyEnemyCreator dummyEnemyCreator;
    private MetalRobotCreator metalRobotCreator;
    private ObjectManager objectManager;
    private ObjectCreator objectCreator;

    /**
     * Crea una nuova istanza di {@code EnemyManager} inizializzando la lista di nemici e
     * i creatori specifici per i tipi di nemici.
     */
    public EnemyManager(){
        this.enemies = new ArrayList<>();
        this.dummyEnemyCreator = new DummyEnemyCreator();
        this.metalRobotCreator = new MetalRobotCreator();
        this.objectCreator = new ObjectCreator();
    }

    /**
     * Inizializza la lista di nemici aggiungendo nemici specifici alle posizioni desiderate.
     */
    public void initializeEnemies(){
        // Add enemies to the EnemyManager
        // Offset from tile x = -20 , y = -10
        //Poligono
        enemies.add(dummyEnemyCreator.createEnemy(3436,3126));
        enemies.add(dummyEnemyCreator.createEnemy(3628,3062));
        enemies.add(dummyEnemyCreator.createEnemy(3820, 3136));

        //Gym
        enemies.add(dummyEnemyCreator.createEnemy(492, 2166));
        enemies.add(dummyEnemyCreator.createEnemy(876, 2166));
        enemies.add(dummyEnemyCreator.createEnemy(492, 1846));
        enemies.add(dummyEnemyCreator.createEnemy(876, 1846));

        //Moving enemies
        enemies.add(metalRobotCreator.createEnemy(6912,1280));
        enemies.add(metalRobotCreator.createEnemy(6848,1856));
        enemies.add(metalRobotCreator.createEnemy(6848,2688));
        enemies.add(metalRobotCreator.createEnemy(7616,2520));
        enemies.add(metalRobotCreator.createEnemy(7872,2496));
        enemies.add(metalRobotCreator.createEnemy(7808,3224));
        enemies.add(metalRobotCreator.createEnemy(8960,3820));


    }

    /**
     * Aggiorna lo stato dei nemici, rimuovendo quelli morti e aggiungendo oggetti al {@code ObjectManager}.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public void update( float delta){
        //enemies.removeIf(enemy -> enemy.isDead() && enemy.isDamageAnimationComplete());
        for(Enemy enemy : enemies){
            if(enemy.isDead() && enemy.isDamageAnimationComplete()){
                objectManager.addObject(objectCreator.createObject("coin",(int)enemy.getEnemyX(),(int)enemy.getEnemyY()));
            }
        }

        enemies.removeIf(enemy -> enemy.isDead() && enemy.isDamageAnimationComplete());

        for (Enemy enemy : enemies){
            enemy.update(delta);
        }
    }

    /**
     * Restituisce la lista di nemici gestiti da questo {@code EnemyManager}.
     *
     * @return La lista di nemici.
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Aggiunge un {@code ObjectManager} a questo {@code EnemyManager}.
     *
     * @param objectManager Il {@code ObjectManager} da aggiungere.
     */
    public void addObjectManager(ObjectManager objectManager){
        this.objectManager = objectManager;
    }

}
