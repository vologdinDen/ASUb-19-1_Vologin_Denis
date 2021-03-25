package Lab_2;


import com.jogamp.opengl.util.texture.*;
import java.io.*;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class TextureLoader {

    public static Texture[] textureSet = {null, null, null, null, null, null, null};
    final static String path_to_images = System.getProperty("user.dir") + "\\images\\";

    public static Texture load(GL2 gl, String fileName) {
        Texture text = null;

        try {
            text = TextureIO.newTexture(new File(path_to_images + fileName), false);
            text.setTexParameteri(gl, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
            text.setTexParameteri(gl, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
            text.setTexParameteri(gl, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            text.setTexParameteri(gl, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(fileName);
        }
        return text;
    }

    public static void cubeLoad(GL2 gl){
        for(int i = 1; i < 8; i++){
            textureSet[i - 1] = TextureLoader.load(gl, i + ".png");
        }
    }
}
