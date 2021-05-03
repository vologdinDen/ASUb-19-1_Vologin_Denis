package Lab_3;

//import Lab_2.TextureLoader;
import Lab_1.Draw;
import Lab_2_lite.Draw_lite;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;

import static Lab_3.Draw_3.xView;
import static Lab_3.Draw_3.yView;

public class What_Draw {
    private static int mapSize = 128;
    private static byte[] pHeightMap = TerrainLoaderr.load("Terrain_2.raw", mapSize);
    private static int stepSize = 1;
    private static float h;
    private static int sdvig_z = 0;
    private static int sdvig_x = 0;
    private static  int pred_z = 0;
    private static  int pred_x = 0;

    public static void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        GLU glu = new GLU();

        h = width / height;

    }

    public static void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();


        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(Draw_3.aView, (double) -4 / 3, 1, 60);
        glu.gluLookAt(xView, 0,Draw_3.zView, xView, 0, Draw_3.zView + 1, 0, 1, 0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);

        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glTexEnvf(gl.GL_TEXTURE_ENV, gl.GL_TEXTURE_ENV_MODE, gl.GL_MODULATE);


        //gl.glRotated(Draw_3.xView, 0, 1, 0);
        var nt_z = Math.round((Draw_3.zView) / 64);
        switch (nt_z - pred_z){
            case 1:
                sdvig_z = sdvig_z + 64;
                pred_z = nt_z;
                break;
            case -1:
                sdvig_z = sdvig_z - 64;
                pred_z = nt_z;
                break;
        }

        var nt_x = Math.round((Draw_3.xView) / 64);
        switch (nt_x - pred_x){
            case 1:
                sdvig_x = sdvig_x + 64;
                pred_x = nt_x;
                break;
            case -1:
                sdvig_x = sdvig_x - 64;
                pred_x = nt_x;
                break;
        }

        map_V(gl, 0 + sdvig_z, 0);
        map_V(gl, 0 + sdvig_z, 105 / 2 + sdvig_x);
        map_V(gl, 105 / 2 + sdvig_z, 0 + sdvig_x);
        map_V(gl, 105 / 2 + sdvig_z, 105 / 2 + sdvig_x);
        map_V(gl, 105 / 2 + sdvig_z, -105 / 2 + sdvig_x);
        map_V(gl, 0 + sdvig_z, -105 / 2 + sdvig_x);
        map_V(gl, -105 / 2 + sdvig_z, -105 / 2 + sdvig_x);
        map_V(gl, -105 / 2 + sdvig_z, 0 + sdvig_x);
        map_V(gl, -105 / 2 + sdvig_z, -105 / 2 + sdvig_x);




        gl.glRasterPos2d(-1.5f, 1.3);
        var renderer = new TextRenderer(new Font("SansSerif", Font.PLAIN, 18));
        renderer.beginRendering(drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
        renderer.draw(Draw_3.label, 24, drawable.getSurfaceHeight() - 24);
        renderer.endRendering();

        gl.glEnd();
        gl.glFlush();
        gl.glDisable(GL.GL_TEXTURE_2D);


    }

    public static void map_V(GL2 gl, int f_p, float s_p){
        double y1, y2, y3, y4, x1, x2, z1, z2;
        //gl.glBindTexture(GL2.GL_TEXTURE_2D, Draw_3.getGrassText().getTextureObject());
        gl.glBegin(GL.GL_TRIANGLES);
        for (var x = 0; x < mapSize; x+=stepSize) {
            for (var z = 0; z < mapSize; z += stepSize) {
                y1 = -getHeight(x, z) / 10;
                y2 = -getHeight(x + stepSize, z) / 10;
                y3 = -getHeight(x + stepSize, z + stepSize) / 10;
                y4 = -getHeight(x, z + stepSize) / 10;

                x1 = 100 * x / mapSize - 50;
                x2 = 100 * (x + 16) / mapSize - 50;

                z1 = 100 * z / mapSize - 50;
                z2 = 100 * (z + 16) / mapSize - 50;

                gl.glTexCoord2d(0, 0);
                gl.glVertex3d(x1 + s_p, y1, z1 + f_p);
                gl.glTexCoord2d(1, 0);
                gl.glVertex3d(x2 + s_p, y2, z1 + f_p);
                gl.glTexCoord2d(1, 1);
                gl.glVertex3d(x2 + s_p, y3, z2 + f_p);
                gl.glTexCoord2d(1, 1);
                gl.glVertex3d(x2 + s_p, y3, z2 + f_p);
                gl.glTexCoord2d(0, 1);
                gl.glVertex3d(x1 + s_p, y4, z2 + f_p);
                gl.glTexCoord2d(0, 0);
                gl.glVertex3d(x1 + s_p, y1, z1 + f_p);
            }
        }

        gl.glEnd();
        gl.glFlush();


    }

    static double getHeight(int x, int z) {
        int mapX = x % mapSize;
        int mapZ = z % mapSize;
        return (pHeightMap[mapX + (mapZ * mapSize)] & 0xFF);
    }

}


