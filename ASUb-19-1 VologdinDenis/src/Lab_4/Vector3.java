
package Lab_4;

public class Vector3 {

    public float x, y, z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void add(Vector3 vector) {
        x += vector.x;
        y += vector.y;
        z += vector.z;
    }

    public void add(float digit) {
        x += digit;
        y += digit;
        z += digit;
    }

    public void add(double digit) {
        x += digit;
        y += digit;
        z += digit;
    }

    public void add(int digit) {
        x += digit;
        y += digit;
        z += digit;
    }

    public void mult(Vector3 vector) {
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;
    }

    public void mult(float digit) {
        x *= digit;
        y *= digit;
        z *= digit;
    }

    public void mult(double digit) {
        x *= digit;
        y *= digit;
        z *= digit;
    }

    public void mult(int digit) {
        x *= digit;
        y *= digit;
        z *= digit;
    }

    public void normalize() {
        float m = magnitude();
        if (m != 0) {
            x /= m;
            y /= m;
            z /= m;
        }
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }
}
