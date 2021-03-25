package com.canteen.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.entity.Dailymenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.canteen.entity.Menu;
import org.apache.ibatis.annotations.*;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 毕业设计
 * @since 2021-03-14
 */
public interface DailymenuMapper extends BaseMapper<Dailymenu> {
    IPage<Dailymenu> querydailyClass(Page<Dailymenu> page,
                                @Param("dailymenuId") Integer dailymenuId,
                                @Param("departmentfloorId")Integer departmentfloorId,
                                @Param("menuName")String menuName,
                                @Param("typeId")Integer typeId,
                                @Param("dailymenuCreatime")String dailymenuCreatime);

}
