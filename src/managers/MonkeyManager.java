package managers;

import entities.bullets.Bullet;
import entities.monkeys.Monkey;
import graphics.Color;
import graphics.terrain.Tile;

import java.util.ArrayList;

public class MonkeyManager {
    ArrayList<Monkey> monkeys;
    public float dañoTribu;
    public float rateTribu;
    public MonkeyManager() {
        monkeys = new ArrayList<Monkey>();
    }

    public ArrayList<Monkey> getMonkeys() {
        return monkeys;
    }
    public void addMonkey(Monkey monkey) {
        monkeys.add(monkey);
    }
    public void removeMonkey(Monkey monkey) {
        monkeys.remove(monkey);
    }
    public void addTipeMonkey(Monkey monkey, Tile tile) {
        if(!canIPlaceAMonkey(tile)) {return;}
        monkey.setPosition(tile.getPosition());
        monkeys.add(monkey);
    }
    public void deleteMonkey(Tile tile) {
        for (Monkey monkey : monkeys) {
            if(monkey.position == tile.getPosition()){
                monkeys.remove(monkey);
            }
        }
    }
    public boolean canIPlaceAMonkey(Tile tile) {
        if(GameManager.getInstance().tileManager.searchTile(tile.getCasilla().x, tile.getCasilla().y).background== Color.GREEN)return true;
        return false;
    }
    public void updateMonkey() {
        float t = (float)GameManager.getInstance().timer.getTime();
        for(Monkey m : monkeys) {
            Bullet b = m.disparar(t);
            if(b != null) {
                GameManager.getInstance().bulletManager.addBullet(b);
            }
        }
    }
}
