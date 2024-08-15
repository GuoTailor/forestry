package org.gyh.forestry.typehandler;

import net.postgis.jdbc.geometry.MultiPolygon;
import org.apache.ibatis.type.MappedTypes;

/**
 * create by GYH on 2024/8/14
 */
@MappedTypes(MultiPolygon.class)
public class MultiPolygonTypeHandler extends AbstractGeometryTypeHandler<MultiPolygon> {
}
