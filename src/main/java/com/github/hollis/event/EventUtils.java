package com.github.hollis.event;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class EventUtils implements ApplicationContextAware {

    private static final AtomicReference<ApplicationContext> CONTEXT_HOLDER = new AtomicReference<>();

    private static ApplicationContext getContext() {
        return CONTEXT_HOLDER.get();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        EventUtils.CONTEXT_HOLDER.set(applicationContext);
    }


    public static void publish(ApplicationEvent event) {
        getContext().publishEvent(event);
    }
}
