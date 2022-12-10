package com.cq.juc.juc_03_lock.deadlock;

public class TestDeadLock {
    public static void main(String[] args) {

        DeadLockObj zs = new DeadLockObj("zs");
        DeadLockObj ls = new DeadLockObj("ls");

        // 开始做菜
        Thread t1 = new Thread(zs);
        Thread t2 = new Thread(ls);
        t1.start();
        t2.start();

        // 执行之后发现有时候他们能炒菜成功，有时候又相互等待，因为zs先开始，如果zs手快，李四还没反应过来就被zs拿走了番茄
        // 这个时候请来了奇异博士，穿越时空观察100次他们的竞争结果 BLOCKED
//        int success = 0;
//        int fail = 0;
//        int count = 1;
//
//        while (count <= 100){
//            System.out.println("奇异博士开始第"+count+"次穿梭时空>>>>>");
//            DeadLockObj zs = new DeadLockObj("zs");
//            DeadLockObj ls = new DeadLockObj("ls");
//            Thread t1 = new Thread(zs);
//            Thread t2 = new Thread(ls);
//            System.out.println(zs.hashCode());
//            System.out.println(ls.hashCode());
//            System.out.println(t1.hashCode());
//            System.out.println(t2.hashCode());
//            t1.start();
//            t2.start();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if ("BLOCKED".equals(t1.getState().name())){
//                t1.interrupt();
//                t2.interrupt();
//                System.out.println("失败的结局");
//                fail++;
//            }else{
//                System.out.println("成功的结局");
//                t1.interrupt();
//                t2.interrupt();
//                success++;
//            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            count++;
//        }








    }
}
