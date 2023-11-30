package Model;

public class Bullet {
    private float x,y; //Posizione del Proiettile
    private float speed; //velocità del proiettile
    private char direction; // Direzione del proiettile
    private boolean active; //Flag per indicare se il proiettile è attivo

    public Bullet(float startX, float startY, float speed, char direction){
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.direction = direction;
        this.active = true;
    }

    public void update(float delta) {
        // Aggiorna la posizione del proiettile in base alla direzione e alla velocità
        switch (direction) {
            case 'w':
                y += speed; //Modificare per delta ?
                break;
            case 's':
                y -= speed;
                break;
            case 'a':
                x -= speed;
                break;
            case 'd':
                x += speed;
                break;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }
}
