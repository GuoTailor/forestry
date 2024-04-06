package org.gyh.forestry.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.postgis.jdbc.geometry.Point;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * create by GYH on 2024/3/30
 */
public class GisPointSerializer extends JsonSerializer<Point> {
    @Override
    public void serialize(Point point, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Map<String, Double> s = new HashMap<>();
        s.put("x", point.getX());
        s.put("y", point.getY());
        s.put("z", point.getZ());
        jsonGenerator.writeObject(s);
    }
}

