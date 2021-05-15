
package Lab_4;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.GL2;
import java.io.File;

public class TextureLoader {

    public static Texture load(GL2 gl, String adress) {
        Texture text = null;
        try {
            text = TextureIO.newTexture(new File(adress), false);
            text.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
            text.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(adress);
        }
        return text;
    }
}
