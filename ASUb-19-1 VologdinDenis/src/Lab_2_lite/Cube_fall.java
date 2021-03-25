package Lab_2_lite;

import Lab_2.TextureLoader;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;

public class Cube_fall {

    protected static float dy1 = .07f, dy2 = .1f;
    protected static float y1 = 0;
    protected static boolean fall = false;
    protected static boolean falled = false;


    private static double fdeg;


    public static void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        if (height <= 0) {
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);

    }

    public static void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(Draw_lite.aView, (double) -4 / 3, 1, 60);
        glu.gluLookAt(Draw_lite.xView, 0, Draw_lite.zView, 0, 0, 0, 0, 4, 0);
        gl.glMatrixMode(gl.GL_MODELVIEW);

        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glTexEnvf(gl.GL_TEXTURE_ENV, gl.GL_TEXTURE_ENV_MODE, gl.GL_MODULATE);

        long millis = System.currentTimeMillis();
        int cycle = 3600;
        double deg = millis % cycle * 360d / cycle;

        if (fall && y1 > -1) {
            y1 -= dy1;
        } else if (y1 <= -1 && fall) {
            falled = true;
            fall = false;
            fdeg = Math.floor(deg / 90) * 90;
        }

        cubeDraw(gl, deg, 2, y1, 0);
        cubeDraw(gl, deg, -1, y1, 0);

        gl.glRasterPos2d(-1.5f, 1.3);
        var renderer = new TextRenderer(new Font("SansSerif", Font.PLAIN, 18));
        renderer.beginRendering(drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
        renderer.draw(Draw_lite.label, 24, drawable.getSurfaceHeight() - 24);
        renderer.endRendering();

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

    public static void cubeDraw(GL2 gl, double deg, int x, float y, int z){
        gl.glPushMatrix();
        gl.glTranslated(x,y,z);
        gl.glEnable(GL2.GL_TEXTURE_2D);


        gl.glColor3f(1, 1, 1);
        gl.glScaled(.3,.3,.3);
        if (!falled) {
            gl.glRotated(deg, 1, 1, 1);
        }
        else
            gl.glRotated(fdeg, 0, 0, 1);

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
