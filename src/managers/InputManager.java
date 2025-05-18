package managers;

import org.lwjgl.glfw.GLFW;

public class InputManager {
    private GameManager gm;
    public boolean pressingW=false, pressingA=false, pressingS=false, pressingD=false,
            pressingRC=false, pressingLC=false; //RightClick, LeftClick
    public InputManager() {
        gm = GameManager.getInstance();
        setUpListeners();
    }

    private void setUpListeners(){
        long window = GameManager.getInstance().renderer.getWindow();
        //Listener teclas presionadas
        GLFW.glfwSetKeyCallback(window, (windowHandle, key, scancode, action, mods) -> {
            if (action == GLFW.GLFW_PRESS){
                switch (key){
                    case GLFW.GLFW_KEY_W:
                        pressingW = true;
                        break;
                    case GLFW.GLFW_KEY_A:
                        pressingA = true;
                        break;
                    case GLFW.GLFW_KEY_S:
                        pressingS = true;
                        break;
                    case GLFW.GLFW_KEY_D:
                        pressingD = true;
                        break;
                    case GLFW.GLFW_KEY_ESCAPE:
                        break;
                }
            }
            if (action == GLFW.GLFW_RELEASE){
                switch (key){
                    case GLFW.GLFW_KEY_W:
                        pressingW = false;
                        break;
                    case GLFW.GLFW_KEY_A:
                        pressingA = false;
                        break;
                    case GLFW.GLFW_KEY_S:
                        pressingS = false;
                        break;
                    case GLFW.GLFW_KEY_D:
                        pressingD = false;
                        break;
                }
            }
        });
        //Listener botones del ratón
        GLFW.glfwSetMouseButtonCallback(window, (windowHandle, button, action, mods) -> {
            if (action == GLFW.GLFW_PRESS) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {pressingLC = true;} //Click izq
                else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {pressingRC = true;} //Click drch
            }
            if (action == GLFW.GLFW_RELEASE) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {pressingLC = false;} //Click izq
                else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {pressingRC = false;} //Click drch
            }
        });
        //Listener rueda del ratón
        GLFW.glfwSetScrollCallback(window, (windowHandle, xoffset, yoffset) -> {
            if (yoffset > 0) {
                //Subir rueda del raton
            }
            else if (yoffset < 0) {
                //Bajar rueda del ratón
            }
        });
    }

    public void update() {checkInputs();}

    public void checkInputs(){
        if(pressingW){
            //Código que se ejecutará mientras se pulse la W
        }
        if(pressingA){
            //Código que se ejecutará mientras se pulse la A
        }
        if(pressingS){
            //Código que se ejecutará mientras se pulse la S
        }
        if(pressingD){
            //Código que se ejecutará mientras se pulse la D
        }
        if(pressingRC){
            //Código que se ejecutará mientras se pulse el click derecho
        }
        if(pressingLC){
            //Código que se ejecutará mientras se pulse el click izquierdo
        }
    }
}
