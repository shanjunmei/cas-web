package com.ffzx.cas;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by vincent on 2016/8/13.
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext context;

    private static void init(ApplicationContext context) {
        ApplicationContextHelper.context = context;
    }

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        init(applicationContext);
    }
}
