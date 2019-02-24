/*
 *  Created by ZhongWenjie on 2019-02-21 10:20
 */

package org.zhong.zschedule.spring.register;

import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;
import org.zhong.zschedule.core.Util;
import org.zhong.zschedule.core.config.QueryTaskExecutorConfig;
import org.zhong.zschedule.core.executor.DefaultQueryTaskExecutor;
import org.zhong.zschedule.core.executor.DefaultQueryTaskVariableExecutor;
import org.zhong.zschedule.core.executor.QueryTaskExecutor;
import org.zhong.zschedule.core.loader.ScheduledExecutorServiceLoader;
import org.zhong.zschedule.core.task.QueryTask;
import org.zhong.zschedule.spring.component.GeneralQueryTaskExecutorComponent;
import org.zhong.zschedule.spring.component.QueryTaskExecutorComponent;
import org.zhong.zschedule.spring.config.DefaultQueryTaskExecutorConfig;
import org.zhong.zschedule.spring.loader.DefaultScheduledExecutorServiceLoader;
import org.zhong.zschedule.spring.task.DefaultQueryTask;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class QueryTaskBeanPostProcessor implements BeanPostProcessor,BeanFactoryAware {

    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    private Map<String,QueryTaskExecutorComponent> beans = Maps.newHashMap();

    @PostConstruct
    public void init(){
        Map<String,QueryTaskExecutorComponent> queryTaskExecutorComponentBeans = configurableListableBeanFactory.getBeansOfType(QueryTaskExecutorComponent.class);
        if (Util.valid(queryTaskExecutorComponentBeans)) {
            for (QueryTaskExecutorComponent component : queryTaskExecutorComponentBeans.values()) {
                beans.put(component.getId(), component);
                ((BeanDefinitionRegistry) configurableListableBeanFactory).registerBeanDefinition(component.getId(), new RootBeanDefinition(DefaultQueryTaskExecutor.class));
            }
        }
        if (!queryTaskExecutorComponentBeans.containsKey("queryTaskExecutor")) {
            beans.put("queryTaskExecutor", new GeneralQueryTaskExecutorComponent(){
                @Override
                public String getId() {
                    return "queryTaskExecutor";
                }
            });
            ((BeanDefinitionRegistry) configurableListableBeanFactory).registerBeanDefinition("queryTaskExecutor", new RootBeanDefinition(DefaultQueryTaskExecutor.class));
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        if (beans.containsKey(s)) {
            QueryTaskExecutorComponent queryTaskExecutorComponent = beans.get(s);
            return doBuild(queryTaskExecutorComponent);
        }
        return o;
    }

    private QueryTaskExecutor doBuild(QueryTaskExecutorComponent queryTaskExecutorComponent) {
        QueryTaskExecutorConfig queryTaskExecutorConfig =
                configurableListableBeanFactory.getBean(DefaultQueryTaskExecutorConfig.class);
        ScheduledExecutorServiceLoader scheduledExecutorServiceLoader =
                configurableListableBeanFactory.getBean(DefaultScheduledExecutorServiceLoader.class);
        QueryTask queryTask =
                configurableListableBeanFactory.getBean(DefaultQueryTask.class);
        try{
            queryTaskExecutorConfig =
                    configurableListableBeanFactory.getBean(queryTaskExecutorComponent.getQueryTaskExecutorConfigClass());
            scheduledExecutorServiceLoader =
                    configurableListableBeanFactory.getBean(queryTaskExecutorComponent.getScheduledExecutorServiceLoaderClass());
            queryTask =
                    configurableListableBeanFactory.getBean(queryTaskExecutorComponent.getQueryTaskClass());
        }catch (BeansException e){
            e.printStackTrace();
        }
        return new DefaultQueryTaskExecutor(
                queryTaskExecutorConfig, scheduledExecutorServiceLoader, queryTask
        );
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.configurableListableBeanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }
}
