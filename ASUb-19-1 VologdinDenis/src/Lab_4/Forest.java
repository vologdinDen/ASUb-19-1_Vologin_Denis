package Lab_4;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class Forest {

    public Texture texture;

    public float[] x, y, z;

    public float halfWidth, height;

    public Forest() {
        halfWidth = 15;
        height = 33;
    }

    public void loadTexture(GL2 gl, String address) {
        texture = TextureLoader.load(gl, address);
    }

    public void setForest(float[] x, float[] y, float[] z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void drawForest(GL2 gl) {
        gl.glColor3d(1, 1, 1);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture.getTextureObject());
        for (int i = 0; i < x.length; i++) {
            gl.glPushMatrix();
            gl.glTranslatef(x[i], y[i], z[i]);
            gl.glRotated(MainCamera.angles.y, 0, 1, 0);
            gl.glBegin(GL2.GL_QUADS);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex2f(halfWidth, height);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex2f(halfWidth, 0);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex2f(-halfWidth, 0);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex2f(-halfWidth, height);
            gl.glEnd();
            gl.glPopMatrix();
        }
    }
}
