package org.gyh.forestry.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.postgis.jdbc.geometry.Point;

/**
 * create by GYH on 2024/4/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonPoint {
    /**
     * The X coordinate of the point.
     * In most long/lat systems, this is the longitude.
     */
    public double x;

    /**
     * The Y coordinate of the point.
     * In most long/lat systems, this is the latitude.
     */
    public double y;

    /**
     * The Z coordinate of the point.
     * In most long/lat systems, this is a radius from the
     * center of the earth, or the height / elevation over
     * the ground.
     */
    public double z;

    public JsonPoint(Point point) {
        if (point != null) {
            this.x = point.getX();
            this.y = point.getY();
            this.z = point.getZ();
        }
    }

    public Point toPoint() {
        Point point = new Point();
        point.setX(x);
        point.setY(y);
        point.setZ(z);
        return point;
    }
}
