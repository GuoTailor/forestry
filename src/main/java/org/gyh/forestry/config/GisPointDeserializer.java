package org.gyh.forestry.config;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import net.postgis.jdbc.geometry.Point;

import java.io.IOException;

/**
 * create by GYH on 2024/3/30
 */
public class GisPointDeserializer extends JsonDeserializer<Point> {

    @Override
    public Point deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectCodec codec = jsonParser.getCodec();
        TreeNode treeNode = codec.readTree(jsonParser);
        TreeNode treeNodeX = treeNode.get("x");
        TreeNode treeNodeY = treeNode.get("y");
        TreeNode treeNodeZ = treeNode.get("z");
        Double x = codec.treeToValue(treeNodeX, Double.class);
        Double y = codec.treeToValue(treeNodeY, Double.class);
        Double z = codec.treeToValue(treeNodeZ, Double.class);
        Point point = new Point();
        point.setX(x);
        point.setY(y);
        point.setZ(z);
        return point;
    }
}

