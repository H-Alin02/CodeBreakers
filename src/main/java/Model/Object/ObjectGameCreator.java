package Model.Object;

/**
 * L'interfaccia {@code ObjectGameCreator} fornisce un contratto per le classi
 * che devono implementare un metodo per la creazione di oggetti di gioco.
 * @author Rakotomalala Manda
 */
public interface ObjectGameCreator {

    /**
     * Crea un oggetto di gioco in base al nome specificato e alle coordinate date.
     *
     * @param nameObject Il nome dell'oggetto da creare.
     * @param posX La coordinata x dell'oggetto.
     * @param posY La coordinata y dell'oggetto.
     * @return Un oggetto di gioco corrispondente al nome specificato e alle coordinate date.
     */
    GameObject createObject(String nameObject, int posX, int posY);
}
