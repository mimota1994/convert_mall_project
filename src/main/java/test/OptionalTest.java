package test;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

import java.util.Optional;

public class OptionalTest {

    static Temp temp1 = new Temp();
    static Temp temp2 = new Temp();

    public static void main(String[] args) {

        test(null ,null);
    }

    public static void test(String str1, String str2){
        System.out.println(Optional.of(str1).isPresent());
        System.out.println(Optional.of(str2).isPresent());
    }
}

class Temp{
    String m;
    String n;
}
