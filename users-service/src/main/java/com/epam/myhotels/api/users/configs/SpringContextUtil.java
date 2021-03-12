package com.epam.myhotels.api.users.configs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext appContext;

    public static <T> T getBean(Class<T> clazz) {
        return appContext.getBean(clazz);
    }

    public static <T> T getProperty(String key, Class<T> targetType) {
        return appContext.getEnvironment().getProperty(key, targetType);
    }

    public static String getProperty(String key) {
        return appContext.getEnvironment().getProperty(key);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.appContext = applicationContext;
    }
}