package com.mimota.service;

import com.mimota.pojo.Product;
import com.mimota.util.common.PageInfo;
import com.mimota.util.common.ServerResponse;
import com.mimota.vo.ProductDetailVo;

import java.util.Map;

public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(String productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(String productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProduct(String productName, String productId, int pageNum, int pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(String productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, String categoryId, int pageNum, int pageSize, String orderBy);



}
