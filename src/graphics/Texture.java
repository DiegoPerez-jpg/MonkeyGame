package graphics;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import utilities.math.Point;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {
    int texId, width, height;
    public Texture(String path){
        MemoryStack stack = MemoryStack.stackPush();
        IntBuffer widthBuf = stack.mallocInt(1);
        IntBuffer heightBuff = stack.mallocInt(1);
        IntBuffer channels = stack.mallocInt(1);

        STBImage.stbi_set_flip_vertically_on_load(true); // Flip vertical para que el origen esté abajo a la izq
        ByteBuffer image = STBImage.stbi_load(path, widthBuf, heightBuff, channels, 4);
        if (image == null) throw new RuntimeException("Error al cargar imagen: " + STBImage.stbi_failure_reason()+ " with this path: " + path);

        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        this.width = widthBuf.get(0);
        this.height = heightBuff.get(0);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0,
                GL_RGBA, GL_UNSIGNED_BYTE, image);

        glGenerateMipmap(GL_TEXTURE_2D);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        STBImage.stbi_image_free(image);
        stack.close();

        this.texId = textureID;
    }

    public void smoothRender(Point p, int scale, float alpha){
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, texId);
        glColor4f(1, 1, 1, alpha);
        glBegin(GL_QUADS);
        glTexCoord2f(0.0f, 0.0f); glVertex2f(p.x, p.y);         // Abajo izquierda
        glTexCoord2f(1.0f, 0.0f); glVertex2f(p.x + width*scale, p.y);     // Abajo derecha
        glTexCoord2f(1.0f, 1.0f); glVertex2f(p.x + width*scale, p.y + height*scale); // Arriba derecha
        glTexCoord2f(0.0f, 1.0f); glVertex2f(p.x, p.y + height*scale);     // Arriba izquierda
        glEnd();

        glDisable(GL_TEXTURE_2D);
    }

    public void smoothRender(Point p, float alpha){smoothRender(p,1,alpha);}

    public void render(Point p, int scale){smoothRender(p, scale, 1f);}

    public void render(Point p) {render(p,1);}
}
