package com.cq.base.exception;

/**
 * @Author chenquan
 * @Date 2022-12-10 23:56
 * @Description: TODO
 * @Version: 1.0
 **/

public class T_01_Exception {

    /**
     * 两数相除
     * @param a
     * @param b
     * @return
     */
    static int division(int a, int b) throws Exception {
        try {
            return a / b;
        } catch (Exception e) {
            System.out.println("Division Exception! -- 01");
            throw new Exception("Division Exception! -- 01");
        } finally {
            System.out.println("Division Finally! -- 02");
        }
    }

    public static void main(String[] args) {
        try {
            division(1,0);
        }catch (Exception e){
            System.out.println("Main " + e.getMessage());
        }
        System.out.println("Main finished -- 03");
    }

}
