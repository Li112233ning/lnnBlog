package com.lnn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnn.domin.entity.Link;
import org.springframework.stereotype.Repository;

/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-25 22:11:01
 */
@Repository
public interface LinkMapper extends BaseMapper<Link> {

}

