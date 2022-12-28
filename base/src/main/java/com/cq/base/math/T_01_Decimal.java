package com.cq.base.math;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @Author chenquan@banggood.com
 * @Description
 * @Date 2022-11-03 10:21
 **/

public class T_01_Decimal {

    public static void main(String[] args) {

        BigDecimal sum = BigDecimal.ZERO;

        BigDecimal b1= new BigDecimal(6.6);
        BigDecimal b2= new BigDecimal(8.8);

        sum = sum.add(b2);
        sum = sum.add(b1);

        DecimalFormat format = new DecimalFormat("0.00");
        System.out.println(format.format(23.7878));



        //BigDecimal求和
        System.out.println(sum);

    }
}
