/*
 *  Created by ZhongWenjie on 2019-02-24 12:37
 */

package org.zhong.zschedule;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

public class ZScheduleRegistry implements BeanDefinitionRegistryPostProcessor {

    private final static String packageName = ZScheduleRegistry.class.getPackage().getName() + ".spring";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        new ClassPathBeanDefinitionScanner(registry).scan(packageName);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
