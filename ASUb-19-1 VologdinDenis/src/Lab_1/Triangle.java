package Lab_1;

import com.jogamp.opengl.GL;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

public class Triangle {
    public static void reshape(GLAutoDrawable drawable, int x, int y, int width, int
            height) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        if (height <= 0) {
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        glu.gluLookAt(0, 0, 6, 0, 0, 0, 0, 1, 0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public static void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glRotatef(22.5f, 1, 0, 0);
        gl.glBegin(GL2.GL_POLYGON);
            //Black Corner
            gl.glColor3f(0.0f, 0.0f, 0.0f);
            gl.glVertex3f(0.0f, 0.0f, 1.0f);
            //Red corner
            gl.glColor3f(1.0f, .0f, 0.0f);
            gl.glVertex3f(-1.0f, 0.f, 0.0f);

            // Yellow Corner
            gl.glColor3f(1.0f, 1.0f, 0.0f);
            gl.glVertex3f(-1.0f, 1.22f, 0.0f);

            //Green Corner
            gl.glColor3f(0.0f, 1.0f, 0.0f);
            gl.glVertex3f(0.0f, 1.22f, 1.0f);

            // Very blue Corner
            gl.glColor3f(0.0f, 1.0f, 1.0f);
            gl.glVertex3f(1.0f, 1.22f, 0.0f);
            // Blue Corner
            gl.glColor3f(0.0f, 0.0f, 1.0f);
            gl.glVertex3f(1.0f, 0.0f, 0.0f);
            // Violet Corner
            gl.glColor3f(1.0f, 0.0f, 1.0f);
            gl.glVertex3f(0.0f, 0.0f, -1.0f);
            //Red corner
            gl.glColor3f(1.0f, .0f, 0.0f);
            gl.glVertex3f(-1.0f, 0.f, 0.0f);

        gl.glEnd();

        gl.glBegin(GL2.GL_POLYGON);
            //White Corner
            gl.glColor3f(1.0f, 1.0f, 1.0f);
            gl.glVertex3f(0.0f, 1.22f, -1.0f);
            // Yellow Corner
            gl.glColor3f(1.0f, 1.0f, 0.0f);
            gl.glVertex3f(-1.0f, 1.22f, 0.0f);
            //Red corner
            gl.glColor3f(1.0f, .0f, 0.0f);
            gl.glVertex3f(-1.0f, 0.0f, 0.0f);
            // Violet Corner
            gl.glColor3f(1.0f, 0.0f, 1.0f);
            gl.glVertex3f(0.0f, 0.0f, -1.0f);
            // Blue Corner
            gl.glColor3f(0.0f, 0.0f, 1.0f);
            gl.glVertex3f(1.0f, 0.0f, 0.0f);
            // Very blue Corner
            gl.glColor3f(0.0f, 1.0f, 1.0f);
            gl.glVertex3f(1.0f, 1.22f, 0.0f);
            //Green Corner
            gl.glColor3f(0.0f, 1.0f, 0.0f);
            gl.glVertex3f(0.0f, 1.22f, 1.0f);
            // Yellow Corner
            gl.glColor3f(1.0f, 1.0f, 0.0f);
            gl.glVertex3f(-1.0f, 1.22f, 0.0f);

        gl.glEnd();

    }
}
