package Lab_4;

import java.io.FileInputStream;
import java.io.IOException;


public final class TerrainLoader {
    
    static public double height_C = 1000;

    private TerrainLoader() {
    }

    private static byte[] load(String fileName, int pictureWidth, int pictureHeight) {
        int pictSize = pictureWidth * pictureHeight * 3;
        byte pict[] = new byte[pictSize];
        FileInputStream input = null;
        try {
            input = new FileInputStream(fileName);
            input.read(pict, 0, pictSize);
            input.close();
            return pict;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static double[][] loadHeightMap(String adress, int widthCount, int lengthCount, int pictureWidth, int pictureHeight) {
        byte[] pict = load(adress, pictureWidth, pictureHeight);
        if (pict != null) {            

            double[][] hMap = new double[widthCount][lengthCount];
            
            double wStep = (double) pictureWidth / widthCount;
            double hStep = (double) pictureHeight / lengthCount;

            double pi = 0;
            double pj = 0;

            int index;

            for (int i = 0; i < lengthCount; i++) {

                pj = 0;
                for (int j = 0; j < widthCount; j++) {
                    index = ((int) (pi) * pictureWidth + (int) (pj)) * 3;
                    
                    hMap[j][i] += (pict[index] < 0) ? pict[index] + 256 : pict[index];
                    hMap[j][i] += (pict[index + 1] < 0) ? pict[index + 1] + 256 : pict[index + 1];
                    hMap[j][i] += (pict[index + 2] < 0) ? pict[index + 2] + 256 : pict[index + 2];

                    hMap[j][i] = hMap[j][i] * 0.33 * height_C * 0.00390625 - 5;

                    pj += wStep;
                }
                pi += hStep;
            }
            return hMap;
        } else {
            System.out.println("File open failure : file not found or couldn't read file");
            return null;
        }
    }

}
