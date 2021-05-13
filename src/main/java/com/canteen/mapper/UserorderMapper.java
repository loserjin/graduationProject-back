package com.canteen.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.entity.Menu;
import com.canteen.entity.Userorder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 毕业设计
 * @since 2021-05-04
 */
public interface UserorderMapper extends BaseMapper<Userorder> {

//    IPage<Userorder> queryorderClass(Page<Userorder> page,
//                           @Param("openId") String openId,
//                           @Param("dailymenuCreatime")String dailymenuCreatime
//                          );
}
