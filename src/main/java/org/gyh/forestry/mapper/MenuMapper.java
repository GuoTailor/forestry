package org.gyh.forestry.mapper;

import org.apache.ibatis.annotations.Param;
import org.gyh.forestry.domain.Menu;
import org.gyh.forestry.domain.vo.MenuVO;
import org.gyh.forestry.domain.vo.MenuWithRole;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author javaboy
 * @since 2024-01-03
 */
public interface MenuMapper {

    List<MenuVO> getMenusByUserId(@Param("userId") Integer userId);

    List<MenuVO> getMenusByParentId(@Param("parentId") Integer parentId);

    List<MenuWithRole> getAllMenusWithRole();

    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<MenuVO> getAllMenus();

    List<Integer> getMidsByRid(Integer rid);
}
