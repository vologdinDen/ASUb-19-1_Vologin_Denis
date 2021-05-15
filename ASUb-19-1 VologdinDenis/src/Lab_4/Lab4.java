package Lab_4;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Lab4 implements GLEventListener {

    public static Terrain terrain;
    public static SkyBox skybox;
    public static Forest forest;
    public static Helicopter helicopter;

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    public static void main(String[] args) {
        Frame frame = new Frame("Лабораторная работа №4");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Lab4());
        Controller controller = new Controller();
        canvas.addKeyListener(controller);
        canvas.addMouseListener(controller);
        canvas.addMouseMotionListener(controller);
        canvas.addMouseWheelListener(controller);
        frame.add(canvas);
        frame.setSize(WIDTH, HEIGHT);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL2 gl = drawable.getGL().getGL2();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        gl.setSwapInterval(1);

        gl.glShadeModel(GL2.GL_SMOOTH);

        gl.glClearColor(.9f, .9f, 1, 0);

        gl.glEnable(GL2.GL_FOG);
        gl.glFogi(GL2.GL_FOG_MODE, GL2.GL_LINEAR);
        gl.glFogfv(GL2.GL_FOG_COLOR, new float[]{.3686f, .3686f, .4313f, 0}, 0);
        gl.glFogf(GL2.GL_FOG_DENSITY, 0.35f);
        gl.glFogf(GL2.GL_FOG_START, 200);
        gl.glFogf(GL2.GL_FOG_END, 400);

        gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL2.GL_ALPHA_TEST);
        gl.glEnable(GL.GL_BLEND);
        gl.glEnable(GL.GL_TEXTURE_2D);

        terrain = new Terrain(1000, 100);
        terrain.setScale(4);
        terrain.setR_Distance(50);
        terrain.setTexture(TextureLoader.load(gl, "images/terrain0.jpg"));
        terrain.setScale(10);
        TerrainLoader.height_C = 150;
        terrain.setHeightMap(TerrainLoader.loadHeightMap("terrains/terrain.raw", 100, 100, 200, 300));

        float[][] t = terrain.createForest(100);

        skybox = new SkyBox(TextureLoader.load(gl,"images/sky.png"));

        forest = new Forest();
        forest.loadTexture(gl,"images/tree2.png");
        forest.setForest(t[0], t[1], t[2]);

        helicopter = new Helicopter(gl);
        helicopter.position.y = 100;
        MainCamera.position.y = 120;
        MainCamera.angles.y = 180;
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();

        if (height <= 0) {
            height = 1;
        }

        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 10000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        helicopter.collision((float) terrain.getHeight(helicopter.position));
        Controller.controll();
        GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glRotated(-MainCamera.angles.x, 1, 0, 0);
        gl.glRotated(-MainCamera.angles.y, 0, 1, 0);
        gl.glRotated(-MainCamera.angles.z, 0, 0, 1);

        gl.glDisable(GL.GL_DEPTH_TEST);
        skybox.draw(gl);
        gl.glEnable(GL.GL_DEPTH_TEST);

        gl.glTranslated(-MainCamera.position.x, -MainCamera.position.y, -MainCamera.position.z);

        terrain.draw(gl);
        terrain.dispose((Controller.spectator) ? MainCamera.position : helicopter.position);

        forest.drawForest(gl);

        helicopter.draw(gl);
        helicopter.physImpact();
        helicopter.accel.z = (float) 0;

        gl.glFlush();

    }

    public static double timeStep;
    public static int FPS;
    public static int frameCounter = 0;

    public void fixFPS() {
        frameCounter++;
        if (System.currentTimeMillis() - timeStep > 1000) {
            timeStep = System.currentTimeMillis();
            FPS = frameCounter;
            frameCounter = 0;
        }
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        
    }
}
