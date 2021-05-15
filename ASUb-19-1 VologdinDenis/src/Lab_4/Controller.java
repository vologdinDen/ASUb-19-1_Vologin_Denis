package Lab_4;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import static Lab_4.Lab4.helicopter;
public class Controller implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private int mPreviousX, mPreviousY;

    private static final double toRadian = Math.PI / 180, toDegree = 180 / Math.PI, speed = 1;

    public static boolean yLeft, yRight, up, down, forward, back, rollRight, rollLeft;

    public static boolean spectator = true;

    private static float distance = 30;

    private static final float pull = (float) 0.1, pullAngle = (float) 0.3;

    public Controller() {
    }

    public static void controll() {
        double sin, cos, angle;

        if (spectator) {
            if (yLeft || yRight || forward || back) {
                angle = toRadian * MainCamera.angles.y;
                sin = Math.sin(angle) * speed;
                cos = Math.cos(angle) * speed;
                if (yRight) {
                    MainCamera.position.x += cos;
                    MainCamera.position.z -= sin;
                }
                if (yLeft) {
                    MainCamera.position.x -= cos;
                    MainCamera.position.z += sin;
                }
                if (forward) {
                    MainCamera.position.x -= sin;
                    MainCamera.position.z -= cos;
                }
                if (back) {
                    MainCamera.position.x += sin;
                    MainCamera.position.z += cos;
                }
            }
            if (up || down) {
                if (up) {
                    MainCamera.position.y += speed;
                }
                if (down) {
                    MainCamera.position.y -= speed;
                }
            }
        } else {
            helicopter.accel.mult(0);
            helicopter.accelAngle.mult(0);

            angle = toRadian * helicopter.angles.y;
            sin = Math.sin(angle);
            cos = Math.cos(angle);

            double cosX = Math.cos(-MainCamera.angles.x * toRadian + Math.PI * 0.5);
            double sinX = Math.sin(-MainCamera.angles.x * toRadian + Math.PI * 0.5);
            double cosY = Math.cos(-MainCamera.angles.y * toRadian - Math.PI * 0.5);
            double sinY = Math.sin(-MainCamera.angles.y * toRadian - Math.PI * 0.5);
            MainCamera.position.x = helicopter.position.x - (float) (distance * sinX * cosY);
            MainCamera.position.y = helicopter.position.y - (float) (distance * cosX);
            MainCamera.position.z = helicopter.position.z - (float) (distance * sinX * sinY);

            Vector3 direction = new Vector3(0, 0, 0);


            if (yRight) {
                helicopter.accelAngle.y = -(float) pullAngle;
            }

            if (yLeft) {
                helicopter.accelAngle.y = (float) pullAngle;
            }
            if (rollRight) {
                helicopter.angles.z += (20 - helicopter.angles.z) * 0.1;
                direction.x += -(float) cos;
                direction.z += (float) sin;
            }
            if (rollLeft) {
                helicopter.angles.z += (-20 - helicopter.angles.z) * 0.1;
                direction.x += (float) cos;
                direction.z += -(float) sin;
            }
            if (forward) {
                helicopter.angles.x += (30 - helicopter.angles.x) * 0.1;
                direction.x += (float) sin;
                direction.z += (float) cos;
            }
            if (back) {
                helicopter.angles.x += (-30 - helicopter.angles.x) * 0.1;
                direction.x += -(float) sin;
                direction.z += -(float) cos;
            }
            if (up) {
                direction.y += (float) 1;
            }
            if (down) {
                direction.y += -(float) 1;
            }
            if (!forward && !back) {
                helicopter.angles.x *= 0.9;
            }
            if (!rollRight && !rollLeft) {
                helicopter.angles.z *= 0.9;
            }
            direction.normalize();
            direction.mult(pull);
            helicopter.accel = direction;
        }
    }


    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_D:
                yRight = true;
                break;
            case KeyEvent.VK_A:
                yLeft = true;
                break;
            case KeyEvent.VK_W:
                forward = true;
                break;
            case KeyEvent.VK_S:
                back = true;
                break;
            case KeyEvent.VK_SHIFT:
                up = true;
                break;
            case KeyEvent.VK_CONTROL:
                down = true;
                break;
            case KeyEvent.VK_Q:
                rollLeft = true;
                break;
            case KeyEvent.VK_E:
                rollRight = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_D:
                yRight = false;
                break;
            case KeyEvent.VK_A:
                yLeft = false;
                break;
            case KeyEvent.VK_W:
                forward = false;
                break;
            case KeyEvent.VK_S:
                back = false;
                break;
            case KeyEvent.VK_SHIFT:
                up = false;
                break;
            case KeyEvent.VK_CONTROL:
                down = false;
                break;
            case KeyEvent.VK_Q:
                rollLeft = false;
                break;
            case KeyEvent.VK_E:
                rollRight = false;
                break;
            case KeyEvent.VK_F:
                spectator = !spectator;
                break;
        }

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        MainCamera.angles.x += (mPreviousY - e.getY()) * 0.4;
        MainCamera.angles.y += (mPreviousX - e.getX()) * 0.4;
        mPreviousX = e.getX();
        mPreviousY = e.getY();
    }

    public void mouseMoved(MouseEvent e) {
        mPreviousX = e.getX();
        mPreviousY = e.getY();
    }


    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0) {
            distance++;
        } else {
            distance--;
        }
    }

}
