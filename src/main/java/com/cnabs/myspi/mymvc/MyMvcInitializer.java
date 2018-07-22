package com.cnabs.myspi.mymvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * 自定义servlet
 */
public class MyMvcInitializer implements IMyMvcInitializer {
    public void loadOnstarp(ServletContext servletContext) {
        ServletRegistration.Dynamic testSetvelt=servletContext.addServlet("mytest","com.cnabs.myspi.mymvc.MyServlet");
        testSetvelt.setLoadOnStartup(1);
        testSetvelt.addMapping("/mytest");
    }
}