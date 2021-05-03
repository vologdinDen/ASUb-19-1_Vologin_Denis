package Lab_3;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Lab_2.TextureLoader;
import Lab_2_lite.Cube_fall;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;





public class Draw_3 extends Frame{

    protected static float xView, yView = 3;
    protected static float zView = 0;
    public static float aView = 120;

    protected static String label = String.format("x: %f y: %f z: %f aView: %f", xView, yView, zView, aView);
    private static Texture chessBoardText;
    public static FPSAnimator animator;

    private static Texture grassText;


    public static void main( String [] args ) {
        GLProfile.initSingleton();
        GLProfile.initProfiles(GLProfile.getDefaultDevice());
        var glprofile = GLProfile.getDefault();
        System.out.println(glprofile.getName() + " " + glprofile.isGL4bc());
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        GLCanvas glcanvas = new GLCanvas( glcapabilities );

        glcanvas.addGLEventListener( new GLEventListener() {

            @Override
            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
                What_Draw.reshape( glautodrawable, x, y, width, height );
            }

            @Override
            public void init( GLAutoDrawable glautodrawable ) {
                GL2 gl = glautodrawable.getGL().getGL2();

                gl.glEnable(GL2.GL_COLOR_MATERIAL);
                gl.glEnable(GL2.GL_NORMALIZE);
                gl.glClearColor(.9f, .9f, 1, 0);
                gl.glFogfv(GL2.GL_FOG_COLOR, new float[] { .9f, .9f, 1, 0 }, 0);

                grassText = TextureLoader.load(gl, "Grass.png");
            }

            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
            }

            @Override
            public void display( GLAutoDrawable glautodrawable ) {
                GL2 gl = glautodrawable.getGL().getGL2();
                gl.glEnable(GL.GL_TEXTURE_2D);
                gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
                What_Draw.display(glautodrawable);
            }
        });


        final Frame frame = new Frame( "Lab_3" );
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
                    zView += 1;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    zView -= 1;
                else if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    xView -= 1;
                else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                    xView += 1;
                else if (e.getKeyCode() == KeyEvent.VK_A)
                    yView -= 0.5;
                else if(e.getKeyCode() == KeyEvent.VK_D)
                    yView += 0.5;
                else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    zView = 6;
                    xView = -3;
                    aView = 45;
                }
                label = String.format("x: %f y: %f z: %f aView: %f", xView, yView, zView, aView);

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_RIGHT)){
                //   xView = 0;
                }

            }
        });
        frame.requestFocusInWindow();

        frame.setSize( 640, 480 );
        frame.setVisible( true );
        animator = new FPSAnimator(glcanvas, 50, true);
        animator.start();
    }

    public static Texture getChessBoardText() {
        return chessBoardText;
    }

    public static Texture getGrassText() { return grassText;}

}
