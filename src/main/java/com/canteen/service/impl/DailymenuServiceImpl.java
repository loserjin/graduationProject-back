package com.canteen.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.entity.Dailymenu;
import com.canteen.entity.Menu;
import com.canteen.mapper.DailymenuMapper;
import com.canteen.mapper.MenuMapper;
import com.canteen.service.DailymenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 毕业设计
 * @since 2021-03-14
 */
@Service
public class DailymenuServiceImpl extends ServiceImpl<DailymenuMapper, Dailymenu> implements DailymenuService {
    public IPage<Dailymenu> selectDailymenuPage(Page<Dailymenu> page,
                                                @Param("menuId") Integer menuId,
                                                @Param("departmentfloorId")Integer departmentfloorId,
                                                @Param("menuName")String menuName,
                                                @Param("typeId")Integer typeId,
                                                @Param("dailymenuCreatime")String dailymenuCreatime){
        DailymenuMapper dailymenuMapper=null;
        return dailymenuMapper.querydailyClass(page,menuId,departmentfloorId,menuName,typeId,dailymenuCreatime);
    }

}
