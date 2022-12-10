package com.cq.base.serializable.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Reader {



    public static  Object read(File file){

        ObjectInputStream oin = null;

        Object obj = null;

        try {
            // 反序列化
            oin = new ObjectInputStream(new FileInputStream(file));

            // 读取
            obj = oin.readObject();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 关闭流
            try {
                oin.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return obj;
    }
}
