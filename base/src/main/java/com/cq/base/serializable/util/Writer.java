package com.cq.base.serializable.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * 写入文件到磁盘
 */
public class Writer {



    public static Boolean write(Object target, File file){

        ObjectOutputStream oout = null;

        try {

            // 使用对象输出流
            oout = new ObjectOutputStream(new FileOutputStream(file));

            // 写入磁盘文件
            oout.writeObject(target);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                oout.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return Boolean.TRUE;
    }
}
