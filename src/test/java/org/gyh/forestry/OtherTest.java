package org.gyh.forestry;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * create by GYH on 2024/7/27
 */
public class OtherTest {

    @Test
    public void test() throws IOException {
        //         "order,refund,shop,price,faka,site,tixian,workorder,message,article,shequ,set,account"
        String s = "order,refund,shop,price,faka,site,tixian,workorder,message,article,shequ,set,account,super,shop";
        StringBuilder sb = new StringBuilder();
        char[] charArray = s.toCharArray();
        for (char c : charArray) {
            String hexString = Integer.toHexString(c);
            System.out.println(hexString);
            sb.append(hexString);
        }
        System.out.println(sb);
    }

    @Test
    public void test2() {
        System.out.println(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456"));
    }

}
