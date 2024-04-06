package org.gyh.forestry.typehandler;

import net.postgis.jdbc.geometry.Point;
import org.apache.ibatis.type.MappedTypes;

/**
 * create by GYH on 2024/3/24
 */
@MappedTypes(Point.class)
public class PointTypeHandler extends AbstractGeometryTypeHandler<Point> {
}
