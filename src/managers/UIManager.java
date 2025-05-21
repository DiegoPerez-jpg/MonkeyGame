package managers;

import graphics.Color;
import graphics.Texture;
import graphics.Tile;
import graphics.UI;
import utilities.math.Point;

import java.util.ArrayList;

public class UIManager {
    public UI asideUI, bottomUI;
    public ArrayList<UI> divs, monkeyShop, monkeyUpgrades;
    public Texture shadowMonkey;
    public int currentMonkey;
    public boolean placeMonkey;
    public UIManager() {
        this.divs = new ArrayList<>();
        this.monkeyShop = new ArrayList<>();
        this.shadowMonkey = null;
    }

    public void initUI(){
        GameManager gm = GameManager.getInstance();
        //sentido antihorario empezando por arriba la izq
        this.asideUI = addUI(Color.BLUEUI, new ArrayList<>() {{
            add(new Point(gm.gameWidth, gm.height)); add(new Point(gm.gameWidth, gm.extension_y));
            add(new Point(gm.width, gm.extension_y)); add(new Point(gm.width, gm.height));
        }}, false, 10, 15);

        this.bottomUI = addUI(Color.BLUEUI2, new ArrayList<>() {{
            add(new Point(0, gm.extension_y)); add(new Point(0, 0));
            add(new Point(gm.width, 0)); add(new Point(gm.width, gm.extension_y));
        }}, false, 10, 0);

        UI bui1 = bottomUI.addSupport(0.13f);
            bui1.setVerticalAlign(true);
            bui1.addLayout(0.8f, Color.BLUE); //Monkey pfp
            bui1.addLayout(0.2f, Color.RED); //Monkey name
        UI bui2 = bottomUI.addSupport(0.29f);
            UI bui21 = bui2.addSupport(0.3f); //Parte izq
                bui21.setVerticalAlign(true);
                bui21.addSupport(0.2f);
                bui21.addLayout(0.6f, Color.TRANSPARENT); //foto
            UI bui22 = bui2.addLayout(0.7f, Color.TRANSPARENT); //txt
        UI bui3 = bottomUI.addSupport(0.29f);
            UI bui31 = bui3.addSupport(0.3f); //Parte izq
                bui31.setVerticalAlign(true);
                bui31.addSupport(0.2f);
                bui31.addLayout(0.6f, Color.TRANSPARENT); //foto
            UI bui32 = bui3.addLayout(0.7f, Color.TRANSPARENT); //txt
        UI bui4 = bottomUI.addSupport(0.29f);
        UI bui41 = bui4.addSupport(0.3f); //Parte izq
            bui41.setVerticalAlign(true);
            bui41.addSupport(0.2f);
            bui41.addLayout(0.6f, Color.TRANSPARENT); //foto
        UI bui42 = bui4.addLayout(0.7f, Color.TRANSPARENT); //txt
        monkeyUpgrades = new ArrayList<>();
        monkeyUpgrades.add(bui2);
        monkeyUpgrades.add(bui3);
        monkeyUpgrades.add(bui4);
        //Tienda de monos
        UI aui1 = asideUI.addSupport(0.5f);
        aui1.setVerticalAlign(true);
        monkeyShop.add(aui1.addLayout(0.235f, Color.TRANSPARENT));
        monkeyShop.get(0).setTexture2(new Texture("src/assets/monkeys/monkeyDarderolvl1.png"));
        monkeyShop.get(0).setTexture(new Texture("src/assets/monkeys/monkeyDarderolvl1_esc.png"));
        monkeyShop.add(aui1.addLayout(0.235f, Color.TRANSPARENT));
        monkeyShop.get(1).setTexture2(new Texture("src/assets/monkeys/creadora de clavos.png"));
        monkeyShop.get(1).setTexture(new Texture("src/assets/monkeys/creadora de clavos esc.png"));
        monkeyShop.add(aui1.addLayout(0.235f, Color.TRANSPARENT));
        monkeyShop.get(2).setTexture(new Texture("src/assets/monkeys/MonoCanon_esc.png"));
        monkeyShop.add(aui1.addLayout(0.235f, Color.TRANSPARENT));
        monkeyShop.get(2).setTexture2(new Texture("src/assets/monkeys/MonoCanon.png"));

        UI aui2 = asideUI.addSupport(0.5f);
        aui2.setVerticalAlign(true);
        monkeyShop.add(aui2.addLayout(0.235f, Color.TRANSPARENT));
//        monkeyShop.get(4).setTexture(new Texture(""));
        monkeyShop.add(aui2.addLayout(0.235f, Color.TRANSPARENT));
//        monkeyShop.get(5).setTexture(new Texture(""));
        monkeyShop.add(aui2.addLayout(0.235f, Color.TRANSPARENT));
//        monkeyShop.get(6).setTexture(new Texture(""));
        monkeyShop.add(aui2.addLayout(0.235f, Color.TRANSPARENT));
//        monkeyShop.get(7).setTexture(new Texture(""));


        this.divs.addAll(monkeyShop);
    }

    public UI addUI(Color color, ArrayList<Point> points, boolean verticalAlign, int margin, int padding){
        UI ui = new UI(color, points, verticalAlign, margin, padding);
        this.divs.add(ui);
        return ui;
    }

    public void check(){
        for (UI monkey : monkeyShop) {
            if (monkey.trigger) {
                monkey.select(2);
                monkey.trigger = false;
            }
            if (monkey.selected && monkey.texture2 != null) {
                this.shadowMonkey = monkey.texture2;
                placeMonkey = true;
            }
        }
        for(Tile tile: GameManager.getInstance().tileManager.tiles) {
            if (tile.colision.trigger) {
                tile.colision.trigger = false;
                if(tile.monkey==null)continue;
                GameManager.getInstance().monkeyManager.monoSelecionado = tile.monkey;
                monkeyUpgrades.get(0).layouts.get(0).texture= new Texture(tile.monkey.getFirstImageUpgrade());
                monkeyUpgrades.get(1).layouts.get(0).texture= new Texture(tile.monkey.getSecondImageUpgrade());
                monkeyUpgrades.get(2).layouts.get(0).texture= new Texture(tile.monkey.getThirdImageUpgrade());
            }
        }
        for (int i = 0; i < monkeyUpgrades.size(); i++) {
            UI monkey = monkeyUpgrades.get(i);
            if (monkey.trigger) {
                GameManager.getInstance().monkeyManager.monoSelecionado.upgrade(i);
                monkey.trigger = false;
            }
        }

    }

    public void renderShadow(){
        if (shadowMonkey == null) return;
        Point mp = GameManager.getInstance().inputManager.getMousePosition();
        int ts = GameManager.getInstance().tileSize;
        mp = new Point(mp.x-(ts/2), mp.y-(ts/2)); //Resto la mitad del ts a las coordenadas para dentrar la imagen
        shadowMonkey.smoothRender(mp, 0.5f);
        shadowMonkey = null;
    }

    public void unselectAll(){
        for (UI div : divs) {
            if (div.selected) {
                div.selected = false;
                div.borderSize = 0;
            }
        }
    }
}
