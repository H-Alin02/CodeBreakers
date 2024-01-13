package Model.NPC;

import View.Hud.Hud;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe gestisce un insieme di personaggi non giocanti (NPC) nel contesto del gioco.
 * Fornisce metodi per inizializzare, aggiornare e ottenere informazioni sugli NPC presenti nel gioco.
 * @author Alin Marian Habasescu
 */
public class NPCManager {
    private final List<NPC> NPC;
    private NPCFactoryImp NPCFactory;

    /**
     * Costruttore che inizializza la lista degli NPC e il NPCFactory.
     */
    public NPCManager(){
       this.NPC = new ArrayList<>();
       this.NPCFactory = new NPCFactoryImp();
    }

    /**
     * Inizializza gli NPC aggiungendo gli NPC desiderati alla lista.
     */
    public void initializeNPCs(){
        NPC.add(NPCFactory.createNPC("Dave",new Vector2(2176,2816)));
        NPC.add(NPCFactory.createNPC("DrGarfild", new Vector2(6528, 4352)));
    }

    /**
     * Aggiorna gli NPC nel manager, passando il tempo trascorso dall'ultimo aggiornamento.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public void update( float delta){
        for(NPC npc : NPC){
            npc.update(delta);
        }
    }

    /**
     * Restituisce la lista degli NPC gestiti dal manager.
     *
     * @return Una lista di oggetti NPC.
     */
    public List<NPC> getNPC() {
        return NPC;
    }

    /**
     * Aggiunge un osservatore (observer) a ciascun NPC nella lista.
     *
     * @param hud L'osservatore da aggiungere a tutti gli NPC.
     */
    public void addObserversToNPC(Hud hud){
        for(NPC npc : NPC){
            npc.addObserver(hud);
        }
    }
}

