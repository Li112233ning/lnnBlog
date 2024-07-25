package com.lnn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lnn.domin.ResponseResult;
import com.lnn.domin.entity.Category;

import java.util.Locale;

public interface CategoryService extends IService<Category> {


    ResponseResult getCategoryList();

}