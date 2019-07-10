package com.mimota.service;



import com.mimota.pojo.Category;
import com.mimota.util.common.ServerResponse;

import java.util.List;

/**
 * Created by geely
 */
public interface ICategoryService {
    ServerResponse addCategory(String categoryName, String parentId);
    ServerResponse updateCategoryName(String categoryId, String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(String categoryId);
    ServerResponse<List<String>> selectCategoryAndChildrenById(String categoryId);

}
