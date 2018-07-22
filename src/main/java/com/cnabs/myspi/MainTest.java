package com.cnabs.myspi;

import java.util.Iterator;
import java.util.ServiceLoader;

/*
* 测试ServiceLoader发现SPI接口 动态查找IMyDriver实现
* */
public class MainTest {
    public static void main(String[] args){
        IMyDriver myDriver1=new MyAppDriver();
        myDriver1.doWork();


        ServiceLoader<IMyDriver> serviceLoader= ServiceLoader.load(IMyDriver.class);
        Iterator<IMyDriver> drivers = serviceLoader.iterator();
        System.out.println("classPath:"+System.getProperty("java.class.path"));
        while (drivers.hasNext()) {
            IMyDriver driver = drivers.next();
            driver.doWork();
        }
    }
}
