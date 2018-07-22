package com.cnabs.myspi;

/*
* SPI 实现 META-INF/services/com.cnabs.myspi.IMyDriver
* */
public class MySPIAppDriver implements IMyDriver {
    public void doWork() {
        System.out.println("SPI: this is MySPIAppDriver [IMyDriver]");
    }
}
