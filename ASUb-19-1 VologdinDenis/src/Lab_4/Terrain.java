package Lab_4;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class Terrain {

    private double positionX, positionZ, scale, DV, DT, r_Distance, max, min, size, step;

    private int arrayPositionX, arrayPositionZ;

    private int[] r_X, r_Z;

    private double[][] vertexX, vertexZ, height;

    private int width, length, count, DA;


    private double[][] textureX, textureY;

    private Texture texture;

    private boolean hasTexture;

    public Terrain(double size, int count) {
        positionX = 0;
        positionZ = 0;
        arrayPositionX = 0;
        arrayPositionZ = 0;

        this.count = count;
        this.size = size;
        step = size / count;
        vertexMapInitialization();

        width = count;
        length = count;
        height = new double[width][length];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                height[i][j] = 0;
            }
        }

        r_X = new int[count];
        r_Z = new int[count];
        riftIndexDispose();

        scale = 1;
        textureMapInitialization();
        setR_Distance(step * 5);
    }

    public double getHeight(Vector3 vec) {
        Vector3 pos = new Vector3((float) (vec.x + width * step * 0.5 - positionX), vec.y, (float) (vec.z + length * step * 0.5 - positionZ));
        int i = (pos.x >= 0) ? (int) ((pos.x % (step * width)) / step) : width + (int) ((pos.x % (step * width)) / step);
        int j = (pos.z >= 0) ? (int) ((pos.z % (step * length)) / step) : length + (int) ((pos.z % (step * length)) / step);
        return height[(i + arrayPositionX) % width][(j + arrayPositionZ) % length];
    }

    private void vertexMapInitialization() {
        vertexX = new double[count][count];
        vertexZ = new double[count][count];
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                vertexX[i][j] = i * step - size * 0.5;
                vertexZ[i][j] = j * step - size * 0.5;
            }
        }
    }

    private void textureMapInitialization() {
        textureX = new double[count][count];
        textureY = new double[count][count];
        double dStep = (double) 1 / count;
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                textureX[i][j] = i * dStep * scale;
                textureY[i][j] = j * dStep * scale;
            }
        }
    }

    private void riftIndexDispose() {
        int x = arrayPositionX;
        int z = arrayPositionZ;
        for (int i = 0; i < count; i++) {
            while (x >= width) {
                x -= width;
            }
            while (z >= length) {
                z -= length;
            }
            r_X[i] = x;
            r_Z[i] = z;
            x++;
            z++;
        }
    }

    public void setScale(double scale) {
        this.scale = scale;
        DT = (DV / size) * scale;
        textureMapInitialization();
    }

    public void setTexture(Texture texture) {
        if (texture != null) {
            this.texture = texture;
            hasTexture = true;
        } else {
            System.out.println("Texture load error");
        }
    }

    public void setHeightMap(double[][] heightMap) {
        if (heightMap != null) {
            height = heightMap;
            width = height.length;
            length = height[0].length;
            max = min = heightMap[0][0];
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < length; j++) {
                    if (heightMap[i][j] < min) {
                        min = heightMap[i][j];
                    }
                    if (heightMap[i][j] > max) {
                        max = heightMap[i][j];
                    }
                }
            }
        } else {
            System.out.println("Invalid height map");
        }
    }

    public void setR_Distance(double distance) {
        if (distance >= step) {
            r_Distance = distance;
            DA = (int) (r_Distance / step);
            DV = DA * step;
            DT = (DV / size) * scale;
        } else {
            System.out.println("Invalid recount distance");
        }
    }

    public void dispose(Vector3 focus) {
        double dx = focus.x - positionX;
        double dz = focus.z - positionZ;
        if (dx > r_Distance || dx < -r_Distance || dz > r_Distance || dz < -r_Distance) {
            double dpw, dpl, dtw, dtl;
            int coeff;

            coeff = (int) (dx / r_Distance);
            dpw = coeff * DV;
            arrayPositionX += coeff * DA;
            dtw = coeff * DT;

            coeff = (int) (dz / r_Distance);
            dpl = coeff * DV;
            arrayPositionZ += coeff * DA;
            dtl = coeff * DT;

            while (arrayPositionX >= width) {
                arrayPositionX -= width;
            }
            while (arrayPositionX < 0) {
                arrayPositionX += width;
            }
            while (arrayPositionZ >= length) {
                arrayPositionZ -= length;
            }
            while (arrayPositionZ < 0) {
                arrayPositionZ += length;
            }
            positionX += dpw;
            positionZ += dpl;
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < count; j++) {
                    vertexX[i][j] += dpw;
                    vertexZ[i][j] += dpl;
                    textureX[i][j] += dtw;
                    textureY[i][j] += dtl;
                }
            }
            riftIndexDispose();
        }
    }

    public void draw(GL2 gl) {
        int inci = 1;
        int incj = 1;
        if (hasTexture) {
            gl.glColor3d(1, 1, 1);
            gl.glBindTexture(GL2.GL_TEXTURE_2D, texture.getTextureObject());
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
            gl.glBegin(GL2.GL_TRIANGLES);
            double c1, c2, c3, c4;
            for (int i = 0; i < count - 1; i++) {
                for (int j = 0; j < count - 1; j++) {
                    c1 = (height[r_X[i]][r_Z[j]] - min) / (max - min);
                    c1 *= c1;
                    c2 = (height[r_X[inci]][r_Z[j]] - min) / (max - min);
                    c2 *= c2;
                    c3 = (height[r_X[i]][r_Z[incj]] - min) / (max - min);
                    c3 *= c3;
                    c4 = (height[r_X[inci]][r_Z[incj]] - min) / (max - min);
                    c4 *= c4;
                    

                    gl.glColor3d(c1, c1, c1);

                    gl.glTexCoord2d(textureX[i][j], textureY[i][j]);
                    gl.glVertex3d(vertexX[i][j], height[r_X[i]][r_Z[j]], vertexZ[i][j]);
                    
                    gl.glColor3d(c2, c2, c2);

                    gl.glTexCoord2d(textureX[inci][j], textureY[inci][j]);
                    gl.glVertex3d(vertexX[inci][j], height[r_X[inci]][r_Z[j]], vertexZ[inci][j]);
                   
                    gl.glColor3d(c3, c3, c3);

                    gl.glTexCoord2d(textureX[i][incj], textureY[i][incj]);
                    gl.glVertex3d(vertexX[i][incj], height[r_X[i]][r_Z[incj]], vertexZ[i][incj]);

                    

                    gl.glColor3d(c4, c4, c4);

                    gl.glTexCoord2d(textureX[inci][incj], textureY[inci][incj]);
                    gl.glVertex3d(vertexX[inci][incj], height[r_X[inci]][r_Z[incj]], vertexZ[inci][incj]);
                   
                    gl.glColor3d(c3, c3, c3);

                    gl.glTexCoord2d(textureX[i][incj], textureY[i][incj]);
                    gl.glVertex3d(vertexX[i][incj], height[r_X[i]][r_Z[incj]], vertexZ[i][incj]);
                    
                    gl.glColor3d(c2, c2, c2);

                    gl.glTexCoord2d(textureX[inci][j], textureY[inci][j]);
                    gl.glVertex3d(vertexX[inci][j], height[r_X[inci]][r_Z[j]], vertexZ[inci][j]);

                    incj++;
                }
                incj = 1;
                inci++;
            }
            gl.glEnd();

        } else {
            gl.glColor3d(0.5, 0.5, 0.5);
            for (int i = 0; i < count - 1; i++) {
                for (int j = 0; j < count - 1; j++) {

                    gl.glBegin(GL2.GL_LINE_LOOP);
                    gl.glVertex3d(vertexX[i][j], height[r_X[i]][r_Z[j]], vertexZ[i][j]);
                    gl.glVertex3d(vertexX[inci][j], height[r_X[inci]][r_Z[j]], vertexZ[inci][j]);
                    gl.glVertex3d(vertexX[i][incj], height[r_X[i]][r_Z[incj]], vertexZ[i][incj]);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_LINE_LOOP);
                    gl.glVertex3d(vertexX[inci][incj], height[r_X[inci]][r_Z[incj]], vertexZ[inci][incj]);
                    gl.glVertex3d(vertexX[i][incj], height[r_X[i]][r_Z[incj]], vertexZ[i][incj]);
                    gl.glVertex3d(vertexX[inci][j], height[r_X[inci]][r_Z[j]], vertexZ[inci][j]);
                    gl.glEnd();

                    incj++;
                }
                incj = 1;
                inci++;
            }
        }
    }

    public float[][] createForest(int count) {
        float[][] t = new float[3][count];

        int i, j;

        for (int k = 0; k < count; k++) {
            i = (int) (Math.random() * width);
            j = (int) (Math.random() * length);

            t[0][k] = (float) (i * step - size * 0.5);
            t[1][k] = (float) (height[i][j]);
            t[2][k] = (float) (j * step - size * 0.5);
        }
        return t;
    }

}
