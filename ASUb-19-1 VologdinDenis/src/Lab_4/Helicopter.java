
package Lab_4;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;


public class Helicopter {

    private final Object object, mainScrew, rollScrew;

    private static final double toRadian = Math.PI / 180, toDegree = 180 / Math.PI;

    public final int FPS = 60;
    
    public Vector3 position, angles, speed, accel, speedAngle, accelAngle;

    public final float mass = 11000, maxSpeedY = 20;

    public static float pull = (float) 0, pullAngle = (float) 0.3;

    public Helicopter(GL2 gl) {
        object = new Object("Helicopter/Helicopter.obj");
        mainScrew = new Object("Helicopter/MainScrew.obj");

        mainScrew.position.x = (float) 0.05961;
        mainScrew.position.y = (float) 3.58183;
        mainScrew.position.z = (float) -0.07208;
        mainScrew.angles.x = (float) -3;

        rollScrew = new Object("Helicopter/RollScrew.obj");

        rollScrew.position.x = (float) -0.51373;
        rollScrew.position.y = (float) 3.06563;
        rollScrew.position.z = (float) -10.9899;

        Texture texture = TextureLoader.load(gl, "Helicopter/texture.jpg");
        object.setTexture(texture);
        mainScrew.setTexture(texture);
        rollScrew.setTexture(texture);

        position = new Vector3(0, 0, 0);
        angles = new Vector3(0, 0, 0);

        speed = new Vector3(0, 0, 0);
        accel = new Vector3(0, 0, 0);

        speedAngle = new Vector3(0, 0, 0);
        accelAngle = new Vector3(0, 0, 0);
    }
    

    public void physImpact() {

        position.add(speed);
        speed.add(accel);

        speed.y -= 9.8 / FPS * 0.21;

        speed.mult(0.95);

        angles.add(speedAngle);
        speedAngle.add(accelAngle);
        speedAngle.mult(0.7);

        mainScrew.angles.y += 8 * Math.PI ;
        mainScrew.angles.y += (speed.y > 0) ? 3 : -3;

        rollScrew.angles.x += 40 * Math.PI;
        rollScrew.angles.x += (speedAngle.y > 0) ? 3 : -3;
        rollScrew.angles.x += (speed.y > 0) ? 2 : -2;
    }

    public void collision(float height) {
        if (height > position.y - 0.3656) {
            position.y += height - position.y + 0.3656;
            speed.y = 0;
        }
    }

    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(position.x, position.y, position.z);
        gl.glRotated(angles.y, 0, 1, 0);
        gl.glRotated(angles.x, 1, 0, 0);
        gl.glRotated(angles.z, 0, 0, 1);

        object.draw(gl);
        mainScrew.draw(gl);
        rollScrew.draw(gl);

        gl.glPopMatrix();
    }

}
