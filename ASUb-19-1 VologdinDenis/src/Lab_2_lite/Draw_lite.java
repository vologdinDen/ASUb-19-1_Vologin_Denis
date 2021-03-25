package Lab_2_lite;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Lab_2.TextureLoader;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;





public class Draw_lite extends Frame{

    protected static float xView, yView;
    protected static float zView;
    public static float aView = 45f;

    public static FPSAnimator animator;

    protected static String label = String.format("x: %f y: %f z: %f aView: %f", xView, yView, zView, aView);

    public static void main( String [] args ) {
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        final GLCanvas glcanvas = new GLCanvas( glcapabilities );

        glcanvas.addGLEventListener( new GLEventListener() {

            @Override
            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
                Cube_fall.reshape( glautodrawable, x, y, width, height );
            }

            @Override
            public void init( GLAutoDrawable glautodrawable ) {
                GL2 gl = glautodrawable.getGL().getGL2();
                TextureLoader.cubeLoad(gl);
                gl.glEnable(GL2.GL_CULL_FACE);
                gl.glCullFace(GL2.GL_FRONT);
                gl.glEnable(GL2.GL_COLOR_MATERIAL);
                gl.glEnable(GL2.GL_NORMALIZE);
                gl.glEnable(GL2.GL_LIGHTING);
                gl.glEnable(GL2.GL_LIGHT0);
                gl.glLightiv(GL2.GL_LIGHT0, GL2.GL_POSITION, new int[] { -1, -1, -1, 0 }, 0);
                gl.glEnable(GL2.GL_FOG);
                gl.glFogfv(GL2.GL_FOG_COLOR, new float[] { 0, 0, 0, 1 }, 0);
                gl.glFogf(GL2.GL_FOG_DENSITY, .1f);
                gl.glClearColor(0,0,0,0);

                xView = -3;
                zView = 6;
            }

            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
            }

            @Override
            public void display( GLAutoDrawable glautodrawable ) {

                var gl = glautodrawable.getGL().getGL4bc();
                gl.glEnable(GL.GL_TEXTURE_2D);
                gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
                Cube_fall.display(glautodrawable);
            }
        });


        final Frame frame = new Frame( "One Triangle AWT" );
        frame.add( glcanvas );
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent windowevent ) {
                frame.remove( glcanvas );
                frame.dispose();

                System.exit( 0 );
            }
        });

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP)
                    zView += .05;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    zView -= .05;
                else if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    xView -= .05;
                else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                    xView += .05;
                else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP)
                    aView -= .05;
                else if(e.getKeyCode() == KeyEvent.VK_PAGE_DOWN)
                    aView += .05;
                else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    zView = 6;
                    xView = -3;
                    aView = 45;
                }
                else if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    if(!Cube_fall.fall)
                        Cube_fall.fall = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_S) {
                    Cube_fall.y1 = 0;
                    Cube_fall.falled = false;
                }
                label = String.format("x: %f y: %f z: %f aView: %f", xView, yView, zView, aView);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        frame.requestFocusInWindow();

        animator = new FPSAnimator(glcanvas, 50, true);
        animator.start();


        frame.setSize( 640, 480 );
        frame.setVisible( true );
    }

}