package managers;

import entities.bullets.Bullet;
import entities.monkeys.*;
import graphics.Color;
import graphics.Tile;

import java.util.ArrayList;

public class MonkeyManager {
    ArrayList<Monkey> monkeys;
    public float dañoTribu;
    public float rateTribu;
    public MonkeyXMLLoader monkeyXMLLoader;
    public MonkeyManager() {
        monkeys = new ArrayList<Monkey>();
        monkeyXMLLoader = new MonkeyXMLLoader();
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
    public Monkey monoSelecionado;

    public Monkey setMonkey(Monkey monkey) {
        Tile tile = monkey.tile;
        if(!canIPlaceAMonkey(tile)) {
            System.out.println("No puedes colocar el monkey aquí");
            return null;
        }
        monkey.setPosition(tile.getPosition());
        tile.monkey = monkey;
        monkeys.add(monkey);
        return monkey;
    }

    public Monkey setTribeMonkey(Tile tile){
        return setMonkey(new TribeMonkey(tile));
    }

    public Monkey setCanonMonkey(Tile tile){
        return setMonkey(new MonoCanon(tile));
    }

    public Monkey setMachineMonkey(Tile tile){
        return setMonkey(new ClaveMachineMono(tile));
    }

    public void deleteMonkey(Tile tile) {
        for (Monkey monkey : monkeys) {
            if(monkey.position == tile.getPosition()){
                monkeys.remove(monkey);
            }
        }
    }
    public boolean canIPlaceAMonkey(Tile tile) {
        return tile.background == Color.GRASS && tile.monkey == null;
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
