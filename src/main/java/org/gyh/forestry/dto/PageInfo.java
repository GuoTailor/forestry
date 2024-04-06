package org.gyh.forestry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * create by GYH on 2022/11/21
 */
@Data
@ToString(callSuper = true)
public class PageInfo<T> extends ResponseInfo<List<T>> {
    @Schema(description = "总条数")
    private Long total;

    @Schema(description = "当前页")
    private Integer page;

    @Schema(description = "页大小")
    private Integer pageSize;

    public static <T> PageInfo<T> ok(Long total, Integer page, Integer pageSize, List<T> data) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setPage(page);
        pageInfo.setTotal(total);
        pageInfo.setPageSize(pageSize);
        pageInfo.setData(data);
        pageInfo.setCode(ResponseInfo.OK_CODE);
        pageInfo.setMsg("成功");
        return pageInfo;
    }

    public static <T> PageInfo<T> ok(Long total, PageReq page, List<T> data) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setPage(page.getPage());
        pageInfo.setTotal(total);
        pageInfo.setPageSize(page.getPageSize());
        pageInfo.setData(data);
        pageInfo.setCode(ResponseInfo.OK_CODE);
        pageInfo.setMsg("成功");
        return pageInfo;
    }


}
