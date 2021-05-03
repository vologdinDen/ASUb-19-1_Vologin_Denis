package Lab_3;

import java.io.*;
public class TerrainLoaderr {
    public static byte[] load(String fileName, int mapSize) {
        byte pHeightMap[] = new byte[mapSize * mapSize];
        FileInputStream input = null;
        try {
            input = new FileInputStream(fileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            input.read(pHeightMap, 0, mapSize * mapSize);
            input.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < pHeightMap.length; i++)
            pHeightMap[i] &= 0xFF;
        return pHeightMap;
    }
}
