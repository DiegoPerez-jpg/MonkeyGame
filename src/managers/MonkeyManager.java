package managers;

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
        float t = GameManager.getInstance().time;
        for(Monkey m : monkeys) {
            m.disparar(t);
        }
    }
}
