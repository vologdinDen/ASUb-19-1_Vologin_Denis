package Lab_2;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

public class Cube {
    public static void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        if (height <= 0) {
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45, (double) -4 / 3, 1, 60);
        glu.gluLookAt(0, 0, 6, 0, 0, 0, 0, 1, 0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    public static void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        long millis = System.currentTimeMillis();
        int cycle = 3600;
        double deg = millis % cycle * 360d / cycle;

        cubeDraw(gl, deg, 2, 2, 0);
        cubeDraw(gl, deg, -1, 0, 0);

        drawPlane(gl);

        gl.glFlush();
        gl.glDisable(GL2.GL_TEXTURE_2D);

    }

    public static void drawPlane(GL2 gl){

        gl.glPushMatrix();

        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, TextureLoader.textureSet[6].getTextureObject());
        gl.glBegin(gl.GL_QUADS);
        gl.glNormal3d(0, 1, 0);

        gl.glScaled(.3,.3,.3);
        gl.glColor3f(1, 1, 1);


        gl.glTexCoord2d(5, 5);
        gl.glVertex3f(25, -3, 25);

        gl.glTexCoord2d(5, 0);
        gl.glVertex3f(25, -3, -25);

        gl.glTexCoord2d(0, 0);
        gl.glVertex3f(-25, -3, -25);

        gl.glTexCoord2d(0, 5);
        gl.glVertex3f(-25, -3, 25);

        gl.glEnd();

        gl.glPopMatrix();

    }

    public static void cubeDraw(GL2 gl, double deg, int x, int y, int z){
        gl.glPushMatrix();
        gl.glTranslated(x,y,z);
        gl.glEnable(GL2.GL_TEXTURE_2D);

        gl.glRotated(deg, 1, 1, 1);

        gl.glColor3f(1, 1, 1);
        gl.glScaled(.3,.3,.3);
        //1
        gl.glBindTexture(GL.GL_TEXTURE_2D, TextureLoader.textureSet[0].getTextureObject());
        gl.glBegin(gl.GL_QUADS);
        gl.glNormal3d(0, 0, .5);

        gl.glTexCoord2d(1,1);
        gl.glVertex3d(-.5, .5, -.5);

        gl.glTexCoord2d(1,0);
        gl.glVertex3d(-.5, -.5, -.5);

        gl.glTexCoord2d(0,0);
        gl.glVertex3d(.5, -.5, -.5);

        gl.glTexCoord2d(0,1);
        gl.glVertex3d(.5, .5, -.5);

        gl.glEnd();

        //6
        gl.glBindTexture(GL.GL_TEXTURE_2D, TextureLoader.textureSet[5].getTextureObject());
        gl.glBegin(gl.GL_QUADS);
        gl.glNormal3d(0, 0, -.5);

        gl.glTexCoord2d(1,1);
        gl.glVertex3d(.5, .5, -1.5);

        gl.glTexCoord2d(1,0);
        gl.glVertex3d(.5, -.5, -1.5);

        gl.glTexCoord2d(0,0);
        gl.glVertex3d(-.5, -.5, -1.5);

        gl.glTexCoord2d(0,1);
        gl.glVertex3d(-.5, .5, -1.5);

        gl.glEnd();

        //2
        gl.glBindTexture(GL.GL_TEXTURE_2D, TextureLoader.textureSet[1].getTextureObject());
        gl.glBegin(gl.GL_QUADS);
        gl.glNormal3d(.5, 0, -.75);

        gl.glTexCoord2d(1,1);
        gl.glVertex3d(.5, .5, -1.5);

        gl.glTexCoord2d(1,0);
        gl.glVertex3d(.5, .5, -.5);

        gl.glTexCoord2d(0,0);
        gl.glVertex3d(.5, -.5, -.5);

        gl.glTexCoord2d(0,1);
        gl.glVertex3d(.5, -.5, -1.5);

        gl.glEnd();

        //5
        gl.glBindTexture(GL.GL_TEXTURE_2D, TextureLoader.textureSet[4].getTextureObject());
        gl.glBegin(gl.GL_QUADS);
        gl.glNormal3d(-.5, 0, -.75);

        gl.glTexCoord2d(1,1);
        gl.glVertex3d(-.5, .5, -1.5);

        gl.glTexCoord2d(1,0);
        gl.glVertex3d(-.5, -.5, -1.5);

        gl.glTexCoord2d(0,0);
        gl.glVertex3d(-.5, -.5, -.5);

        gl.glTexCoord2d(0,1);
        gl.glVertex3d(-.5, .5, -.5);

        gl.glEnd();


        //4
        gl.glBindTexture(GL.GL_TEXTURE_2D, TextureLoader.textureSet[3].getTextureObject());
        gl.glBegin(gl.GL_QUADS);
        gl.glNormal3d(0, 0.5, -0.75);

        gl.glTexCoord2d(1,1);
        gl.glVertex3d(.5, .5, -1.5);

        gl.glTexCoord2d(1,0);
        gl.glVertex3d(-.5, .5, -1.5);

        gl.glTexCoord2d(0,0);
        gl.glVertex3d(-.5, .5, -.5);

        gl.glTexCoord2d(0,1);
        gl.glVertex3d(.5, .5, -.5);

        gl.glEnd();


        //3
        gl.glBindTexture(GL.GL_TEXTURE_2D, TextureLoader.textureSet[2].getTextureObject());
        gl.glBegin(gl.GL_QUADS);
        gl.glNormal3d(0, -.5, -.75);

        gl.glTexCoord2d(1,1);
        gl.glVertex3d(.5, -.5, -1.5);

        gl.glTexCoord2d(1,0);
        gl.glVertex3d(.5, -.5, -.5);

        gl.glTexCoord2d(0,0);
        gl.glVertex3d(-.5, -.5, -.5);

        gl.glTexCoord2d(0,1);
        gl.glVertex3d(-.5, -.5, -1.5);

        gl.glEnd();


        gl.glPopMatrix();

    }
}
