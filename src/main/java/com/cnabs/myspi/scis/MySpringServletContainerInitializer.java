package com.cnabs.myspi.scis;

import com.cnabs.myspi.IMyDriver;
import com.cnabs.myspi.mymvc.IMyMvcInitializer;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/*
* Servlet3.0 SCI【SPI】实现 ServletContainerInitializer 实现容器初始化
* 利用HandlesTypes 查找所有的ApplicationInitializer类
* 模拟Spring SCI -> SpringServletContainerInitializer实现
* 增加了个人自定义IMyDriver 用SPI的例子做SCI测试
* * */
@HandlesTypes({WebApplicationInitializer.class,IMyDriver.class,IMyMvcInitializer.class})
public class MySpringServletContainerInitializer implements ServletContainerInitializer {

    public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext servletContext) throws ServletException {
        System.out.println("This is My SPI-SCI MySpringServletContainerInitializer WebApplicationInitializer WebParameter...................................");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        List<WebApplicationInitializer> initializers = new LinkedList<WebApplicationInitializer>();

        if (webAppInitializerClasses != null) {
            for (Class<?> waiClass : webAppInitializerClasses) {
                // Be defensive: Some servlet containers provide us with invalid classes,
                // no matter what @HandlesTypes says...

                //***********Spring WebApplicationInitializer 初始化**********************//
                if (!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers()) &&
                        WebApplicationInitializer.class.isAssignableFrom(waiClass)) {
                    try {
                        initializers.add((WebApplicationInitializer)
                                ReflectionUtils.accessibleConstructor(waiClass).newInstance());
                    }
                    catch (Throwable ex) {
                        throw new ServletException("Failed to instantiate WebApplicationInitializer class", ex);
                    }

                    System.out.println("WebApplicationInitializer................................");
                }


                //*************个人 IMyDriver SPI做为SCI使用的服务类发现实现*********************/
                if (!waiClass.isInterface() && IMyDriver.class.isAssignableFrom(waiClass)) {
                    try {

                        IMyDriver myDriver
                                =(IMyDriver)ReflectionUtils.accessibleConstructor(waiClass).newInstance();
                        myDriver.doWork();

                        System.out.println("IMyDriver................................");
                    }
                    catch (Throwable ex) {
                        System.out.println("IMyDriver测试出错"+ ex.getMessage());
                    }
                }

                //*****************自定义MVC servlet 实现**********************/
                if (!waiClass.isInterface()&& !Modifier.isAbstract(waiClass.getModifiers())&&IMyMvcInitializer.class.isAssignableFrom(waiClass)){
                    try {
                        IMyMvcInitializer IMyMvcInitializer =  ((IMyMvcInitializer) waiClass.newInstance());
                        IMyMvcInitializer.loadOnstarp(servletContext);

                        System.out.println("WebParameter................................");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

       /* if (initializers.isEmpty()) {
            servletContext.log("No Spring WebApplicationInitializer types detected on classpath");
            return;
        }*/

       /* servletContext.log(initializers.size() + " Spring WebApplicationInitializers detected on classpath");
        AnnotationAwareOrderComparator.sort(initializers);
        for (WebApplicationInitializer initializer : initializers) {
            initializer.onStartup(servletContext);
        }*/
    }
}
