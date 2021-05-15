package Lab_4;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class SkyBox {
        
    public Texture texture;
    
    public float radius;
    
    public SkyBox(Texture texture) {
        
        this.texture = texture;
        radius = 10;
    }
    

    public void draw(GL2 gl) {

        gl.glColor3d(1, 1, 1);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture.getTextureObject());
        gl.glBegin(GL2.GL_QUADS);

        gl.glTexCoord2f(.2505f, .334f);
        gl.glVertex3f(-radius, radius, -radius);        
        gl.glTexCoord2f(0, .334f);
        gl.glVertex3f(-radius, radius, radius);     
        gl.glTexCoord2f(0, .666f);      
        gl.glVertex3f(-radius, -radius, radius);      
        gl.glTexCoord2f(.2505f, .666f);
        gl.glVertex3f(-radius, -radius, -radius);

        gl.glTexCoord2f(.75f, .334f);
        gl.glVertex3f(radius, radius, radius);
        gl.glTexCoord2f(.4996f, .334f);
        gl.glVertex3f(radius, radius, -radius);        
        gl.glTexCoord2f(.4996f, .666f);
        gl.glVertex3f(radius, -radius, -radius);       
        gl.glTexCoord2f(.75f, .666f);
        gl.glVertex3f(radius, -radius, radius);

        gl.glTexCoord2f(.4996f, .334f);
        gl.glVertex3f(radius, radius, -radius);
        gl.glTexCoord2f(.2505f, .334f);
        gl.glVertex3f(-radius, radius, -radius);   
        gl.glTexCoord2f(.2505f, .666f);
        gl.glVertex3f(-radius, -radius, -radius);       
        gl.glTexCoord2f(.4996f, .666f);
        gl.glVertex3f(radius, -radius, -radius);

        gl.glTexCoord2f(1.0f, .334f);
        gl.glVertex3f(-radius, radius, radius);
        gl.glTexCoord2f(.75f, .334f);
        gl.glVertex3f(radius, radius, radius);
        gl.glTexCoord2f(.75f, .666f);
        gl.glVertex3f(radius, -radius, radius);
        gl.glTexCoord2f(1.0f, .666f);
        gl.glVertex3f(-radius, -radius, radius);

        gl.glTexCoord2f(.2505f, 0.334f);
        gl.glVertex3f(-radius, radius, -radius);
        gl.glTexCoord2f(.4996f, 0.334f);
        gl.glVertex3f(radius, radius, -radius);
        gl.glTexCoord2f(.4996f, 0f);
        gl.glVertex3f(radius, radius, radius);
        gl.glTexCoord2f(.2505f, 0f);
        gl.glVertex3f(-radius, radius, radius);

        gl.glTexCoord2f(.2505f, 1.0f);
        gl.glVertex3f(-radius, -radius, radius);
        gl.glTexCoord2f(.4996f, 1.0f);
        gl.glVertex3f(radius, -radius, radius);
        gl.glTexCoord2f(.4996f, 0.666f);
        gl.glVertex3f(radius, -radius, -radius);
        gl.glTexCoord2f(.2505f, 0.666f);
        gl.glVertex3f(-radius, -radius, -radius);
        gl.glEnd();
    }
    
}
