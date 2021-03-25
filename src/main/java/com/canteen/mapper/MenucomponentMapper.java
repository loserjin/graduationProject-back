package com.canteen.mapper;

import com.canteen.entity.Menucomponent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 毕业设计
 * @since 2021-03-23
 */
public interface MenucomponentMapper extends BaseMapper<Menucomponent> {
//    @Select("SELECT * FROM menucomponent WHERE menuId = #{menuId}")
//    List<Menucomponent> queryMenucomponentList(Integer menuId);
}
