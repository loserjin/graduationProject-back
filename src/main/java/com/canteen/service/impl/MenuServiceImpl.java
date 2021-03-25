package com.canteen.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.entity.Menu;
import com.canteen.mapper.MenuMapper;
import com.canteen.service.MenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    public IPage<Menu> selectMenuPage(Page<Menu> page,
                                      @Param("menuId") Integer menuId,
                                      @Param("departmentfloorId")Integer departmentfloorId,
                                      @Param("menuName")String menuName,@Param("typeId")Integer typeId){
        MenuMapper menuMapper = null;
        return menuMapper.queryClass(page,menuId,departmentfloorId,menuName,typeId);
    }
}
