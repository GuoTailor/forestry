package org.gyh.forestry.config;

import net.postgis.jdbc.geometry.Point;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * Created by gyh on 2021/2/10
 */
@Configuration
public class OtherConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer())
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer())
                .serializerByType(Point.class, new GisPointSerializer())
                .deserializerByType(Point.class, new GisPointDeserializer());
    }

}
