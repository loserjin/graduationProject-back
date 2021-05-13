package com.canteen.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.canteen.entity.Menucomponent;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 毕业设计
 * @since 2021-03-14
 */
public interface MenuMapper extends BaseMapper<Menu> {

//    @Results({
//            @Result(column = "menuId", property = "menuId"),
//            @Result(column = "menuId", property = "menucomponents",
//                    many = @Many(select = "com.canteen.mapper.MenucomponentMapper.selectByMenuId"))
//    })
//    @Select("SELECT * FROM menu " +
//            "WHERE 1=1")
//    List<Menu> queryClass(@Param("menuId") Integer menuId,@Param("departmentfloorId")Integer departmentfloorId,@Param("menuName")String menuName);

    IPage<Menu> queryClass(Page<Menu> page,
                           @Param("menuId") Integer menuId,
                           @Param("departmentId")Integer departmentId,
                           @Param("departmentfloorId")Integer departmentfloorId,
                           @Param("menuName")String menuName,
                           @Param("typeId")Integer typeId);
}
