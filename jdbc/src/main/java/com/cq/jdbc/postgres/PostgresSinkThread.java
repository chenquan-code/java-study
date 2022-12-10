package com.cq.jdbc.postgres;

/**
 * @Author chenquan
 * @Description
 * @Date 2022/6/21 9:28
 **/

public class PostgresSinkThread implements Runnable{

    private String name;
    private Boolean isRun = true;

    public PostgresSinkThread(String name){
        this.name = name;
    }

    @Override
    public void run() {
        while (isRun){
            int i = 0;
//            System.out.println("==========="+name+"===========");
            PostgresUtil.insert(new Student("Achilles"+i++, "Male", "14"));
            PostgresUtil.insert_t1("INSERT INTO public.t1(order_id, origin, destination, is_arrived) VALUES (14, 'aa', 'aaaa', true)");
        }
    }
}
