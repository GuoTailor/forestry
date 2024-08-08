package org.gyh.forestry;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * create by GYH on 2024/7/27
 */
public class OtherTest {

    @Test
    public void test() throws IOException {
        int[] arr = {2, 0, 1, 6, 7};
        File file = new File("F:\\password.txt");
        FileOutputStream fos = new FileOutputStream(file);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {
                        for (int m = 0; m < 5; m++) {
                            for (int n = 0; n < 5; n++) {
                                fos.write(("" + arr[i] + arr[j] + arr[k] + arr[l] + arr[m] + arr[n] + "\n").getBytes());
                            }
                        }
                    }
                }
            }
        }
        fos.flush();
        fos.close();
    }

}
