/*
 *  Created by ZhongWenjie on 2019-01-25 9:52
 */

package org.zhong.zschedule.register;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.zhong.zschedule.Util;
import org.zhong.zschedule.component.DefaultQueryTaskExecutorComponent;
import org.zhong.zschedule.component.QueryTaskExecutorComponent;
import org.zhong.zschedule.config.QueryTaskExecutorConfig;
import org.zhong.zschedule.executor.DefaultQueryTaskExecutor;
import org.zhong.zschedule.executor.QueryTaskExecutor;
import org.zhong.zschedule.loader.ScheduledExecutorServiceLoader;
import org.zhong.zschedule.task.QueryTask;

import java.util.Map;

@Component
public class QueryTaskExecutorRegister implements BeanFactoryPostProcessor {

    public static final String DEFAULT = "default";

    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        this.configurableListableBeanFactory = configurableListableBeanFactory;
        configurableListableBeanFactory.registerSingleton(DEFAULT, doBuild(new DefaultQueryTaskExecutorComponent()));
        Map<String, QueryTaskExecutorComponent> beans = configurableListableBeanFactory.getBeansOfType(QueryTaskExecutorComponent.class);
        if (Util.valid(beans) ) {
            for (QueryTaskExecutorComponent component : beans.values()) {
                QueryTaskExecutor queryTaskExecutor = doBuild(component);
                configurableListableBeanFactory.registerSingleton(component.getId(), queryTaskExecutor);
            }
        }
    }

    private QueryTaskExecutor doBuild(QueryTaskExecutorComponent queryTaskExecutorComponent) {
        QueryTaskExecutorConfig queryTaskExecutorConfig =
                configurableListableBeanFactory.getBean(queryTaskExecutorComponent.getQueryTaskExecutorConfigClass());
        ScheduledExecutorServiceLoader scheduledExecutorServiceLoader = configurableListableBeanFactory.getBean(
                queryTaskExecutorComponent.getScheduledExecutorServiceLoaderClass()
        );
        QueryTask queryTask = configurableListableBeanFactory.getBean(
                queryTaskExecutorComponent.getQueryTaskClass()
        );
        return new DefaultQueryTaskExecutor(
                queryTaskExecutorConfig, scheduledExecutorServiceLoader, queryTask
        );
    }

}
