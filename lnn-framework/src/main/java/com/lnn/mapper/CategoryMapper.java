package com.lnn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnn.domin.entity.Category;
import org.springframework.stereotype.Repository;

/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-25 11:09:55
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}

