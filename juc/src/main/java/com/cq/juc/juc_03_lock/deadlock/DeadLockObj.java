package com.cq.juc.juc_03_lock.deadlock;

public class DeadLockObj implements Runnable{

    /**
     * 模拟死锁
     * 张三李四两个人都要做番茄炒鸡蛋
     * 番茄只有一份，鸡蛋也只有一份
     * 张三手里有鸡蛋，李四手里有番茄
     * 张三想要李四手机的番茄，李四想要张三手里的鸡蛋
     */
    public static Object egg = new Object();
    public static Object tomato = new Object();

    public String name;

    public DeadLockObj(String name){
        this.name = name;
    }


    @Override
    public void run() {

        if ("zs".equals(name)){
            synchronized (egg){
                System.out.println("zs拿到了egg,他现在想去拿tomato");
                synchronized (tomato){
                    System.out.println("zs拿到了tomato,他可以做番茄炒蛋了");
                }
            }
        }else if ("ls".equals(name)){
            synchronized (tomato){
                System.out.println("ls拿到了tomato,他现在想去拿egg");
                synchronized (egg){
                    System.out.println("ls拿到了egg,他可以做番茄炒蛋了");
                }
            }
        }




    }
}
