package org.gyh.forestry;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.dialect.helper.PostgreSqlDialect;
import org.apache.ibatis.mapping.BoundSql;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotContribution;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotProcessor;
import org.springframework.beans.factory.aot.BeanRegistrationExcludeFilter;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.RegisteredBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

import java.util.concurrent.Executor;
import java.util.stream.Stream;

/**
 * create by GYH on 2024/6/11
 */
//@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints(PageHelperNativeConfiguration.PageHelperRuntimeHintsRegistrar.class)
public class PageHelperNativeConfiguration {

    @Bean
    public PageHelperBeanFactoryInitializationAotProcessor pageHelperBeanFactoryInitializationAotProcessor() {
        return new PageHelperBeanFactoryInitializationAotProcessor();
    }

    static class PageHelperRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            Stream.of(
                    PageHelper.class,
                    PostgreSqlDialect.class,
                    BoundSql.class
            ).forEach(x -> hints.reflection().registerType(x, MemberCategory.values()));
        }
    }

    static class PageHelperBeanFactoryInitializationAotProcessor
            implements BeanFactoryInitializationAotProcessor, BeanRegistrationExcludeFilter {

        @Override
        public BeanFactoryInitializationAotContribution processAheadOfTime(ConfigurableListableBeanFactory beanFactory) {
            return (context, code) -> {
                RuntimeHints hints = context.getRuntimeHints();
                hints.proxies().registerJdkProxy(Executor.class);
            };
        }

        @Override
        public boolean isExcludedFromAotProcessing(RegisteredBean registeredBean) {
            return false;
        }
    }
}
