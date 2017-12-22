package com.ramannada.springdemo.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    @Override
    protected String[] getServletMappings() {
        String[] mahasiswaMap =
                {"/", "/mahasiswa", "/mahasiswa/{id}", "/mahasiswa/update", "/mahasiswa/find"};

        return mahasiswaMap;
    }
}
