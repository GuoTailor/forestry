package org.gyh.forestry.domain;

import lombok.Data;
import net.postgis.jdbc.geometry.MultiPolygon;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

/**
 * create by GYH on 2024/8/14
 */
@Data
public class Moxing {
    @Id
    private Integer gid;

    /**
     * 优势树
     */
    private String tree;

    /**
     * 县
     */
    private String prefecture;

    /**
     * 乡
     */
    private String village;

    /**
     * 村
     */
    private String hamlet;

    /**
     * 社
     */
    private String agency;

    /**
     * 高程
     */
    private BigDecimal elevation;

    private Integer bsm;

    private String qy;

    private String nljg;

    private String ybddj;

    private String szzc;

    private String nl;

    /**
     * 分类
     */
    private String classify;

    private String pd;

    private String gc;

    private String px;

    /**
     * 清理
     */
    private String clean;

    private Double p;

    private Short level;

    private String k;

    private Short d1;

    private Short d2;

    private Short d3;

    private Double f;

    private MultiPolygon geom;
}
