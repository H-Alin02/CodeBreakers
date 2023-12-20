package View.Hud;

import Model.Player;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.List;

public class HudComposite implements HudComponent{
    protected List<HudComponent> componentList = new ArrayList<>();
    private Table table = new Table();

    public HudComposite() {

    }
    public void addComponent(HudComponent component){
        componentList.add(component);
    }

    public void remove(HudComponent component) {
        componentList.remove(component);
    }
    public void compose(){
        for (int i =0 ; i<componentList.size() ; i++){
            this.table.add((Actor) componentList.get(i)).spaceRight(10);
        }
    }
    public void position(String position){
        switch(position) {
            case "left": this.table.left();
            case "right": this.table.right();
            case "top": this.table.top();
            case "bottom": this.table.bottom();
            default : this.table.center();
        }
    }


    @Override
    public void update(Player player) {

    }
}
