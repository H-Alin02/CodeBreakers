package Model.Object;

/**
 * La classe {@code ObjectCreator} implementa l'interfaccia {@code ObjectGameCreator}
 * e fornisce un metodo per la creazione di oggetti di gioco in base al loro nome e posizione.
 * @author Rakotomalala Manda
 */
public class ObjectCreator implements ObjectGameCreator {

    /**
     * Crea un oggetto di gioco in base al nome specificato e alle coordinate date.
     *
     * @param nameObject Il nome dell'oggetto da creare.
     * @param posX La coordinata x dell'oggetto.
     * @param posY La coordinata y dell'oggetto.
     * @return Un oggetto di gioco corrispondente al nome specificato e alle coordinate date.
     *         Se il nome specificato è sconosciuto o nullo, restituisce {@code null}.
     */
    @Override
    public GameObject createObject(String nameObject, int posX, int posY) {
        if (nameObject == null) {
            return null;
        } else if (nameObject.equalsIgnoreCase("coin")) {
            return new Coin(posX, posY);
        } else if (nameObject.equalsIgnoreCase("key")) {
            return new Key(posX, posY);
        } else if (nameObject.equalsIgnoreCase("meat")) {
            return new Meat(posX, posY);
        } else if (nameObject.equalsIgnoreCase("ammunition")) {
            return new Ammunition(posX, posY);
        } else if (nameObject.equalsIgnoreCase("medikit")) {
            return new Medikit(posX, posY);
        } else if (nameObject.equalsIgnoreCase("keyUsb")) {
            return new KeyUSB(posX, posY);
        }
        return null;
    }
}
