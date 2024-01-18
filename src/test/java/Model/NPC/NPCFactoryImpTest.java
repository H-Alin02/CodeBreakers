package Model.NPC;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class NPCFactoryImpTest {

    NPCFactoryImp npcFactoryImp;
    @BeforeEach
    void setUp() {
        npcFactoryImp = new NPCFactoryImp();
        assertNotNull(npcFactoryImp);
    }

    @AfterEach
    void tearDown() {
        npcFactoryImp = null;
        assertNull(npcFactoryImp);
    }

    @Test
    void createNPC() {
        /*var dave = npcFactoryImp.createNPC("Dave",new Vector2(100, 100));
        var drGarfield = npcFactoryImp.createNPC("DrGarfild",new Vector2(100, 100));
        var npc = npcFactoryImp.createNPC("npc",new Vector2(100, 100));
        assertNotNull(dave);
        assertNotNull(drGarfield);
        assertNull(npc);

        assertEquals( DaveNPC.class, dave.getClass());
        assertEquals( DrGarfield.class, drGarfield.getClass());
        */
    }
}