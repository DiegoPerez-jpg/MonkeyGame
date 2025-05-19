package managers;

import entities.bullets.Bullet;
import entities.monkeys.Monkey;

import java.util.ArrayList;

public class MonkeyManager {
    ArrayList<Monkey> monkeys;
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
