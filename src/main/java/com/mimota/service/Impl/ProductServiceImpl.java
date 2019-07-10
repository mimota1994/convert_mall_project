package com.mimota.service.Impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mimota.dao.table.CategoryTable;
import com.mimota.dao.table.ProductTable;
import com.mimota.pojo.Category;
import com.mimota.pojo.Product;
import com.mimota.service.ICategoryService;
import com.mimota.service.IProductService;
import com.mimota.util.DateTimeUtil;
import com.mimota.util.PageUtil;
import com.mimota.util.PropertiesUtil;
import com.mimota.util.common.*;
import com.mimota.vo.ProductDetailVo;
import com.mimota.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {


    @Autowired
    private ProductTable productTable;

    @Autowired
    private CategoryTable categoryTable;

    @Autowired
    private ICategoryService iCategoryService;

    public ServerResponse saveOrUpdateProduct(Product product){
        if(product != null)
        {
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray = ((product.getSubImages().split(",")));
                if(subImageArray.length > 0){
                    product.setMainImage(subImageArray[0]);
                }
            }

            if(product.getId() != null){
                UpdateResults result = productTable.updateByPrimaryKeySelective(product);
                if(result.getUpdatedCount() > 0){
                    return ServerResponse.createBySuccess("更新产品成功");
                }
            }else{
                Key<Product> key = productTable.insert(product);
                if(key != null){
                    return ServerResponse.createBySuccess("增加产品成功", key.getId());
                }
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }


    public ServerResponse<String> setSaleStatus(String productId,Integer status){
        if(productId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        UpdateResults result = productTable.updateStatusByPrimaryKey(productId, status);
        if(result.getUpdatedCount() > 0){
            return ServerResponse.createBySuccess("修改产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败");
    }


    public ServerResponse<ProductDetailVo> manageProductDetail(String productId){
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productTable.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));

        Category category = categoryTable.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId("0");//默认根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }



    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize){
        //startPage--start
        //填充自己的sql查询逻辑
        //pageHelper-收尾
//        PageHelper.startPage(pageNum,pageSize);
        int offset = PageUtil.skip(pageNum, pageSize);
        Pair<Long, List<Product>> pair = productTable.selectList(offset, pageSize);
        List<Product> productList = pair.getSecond();

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product productItem : productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }

        PageInfo<List<ProductListVo>> pageInfo = new PageInfo(pageNum, pageSize, pair.getFirst(), productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }



    public ServerResponse<PageInfo> searchProduct(String productName,String productId,int pageNum,int pageSize){
        int offset = PageUtil.skip(pageNum, pageSize);
        Pair<Long, List<Product>> pair = productTable.selectByNameAndProductId(productName,productId, offset, pageSize);

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product productItem : pair.getSecond()){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }

        return ServerResponse.createBySuccess(new PageInfo(pageNum, pageSize, pair.getFirst(), productListVoList));
    }


    public ServerResponse<ProductDetailVo> getProductDetail(String productId){
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productTable.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        if(product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }


    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,String categoryId,int pageNum,int pageSize,String orderBy){
        if(StringUtils.isBlank(keyword) && categoryId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<String> categoryIdList = new ArrayList<String>();

        if(categoryId != null){
            Category category = categoryTable.selectByPrimaryKey(categoryId);
            if(category == null && StringUtils.isBlank(keyword)){
                //没有该分类,并且还没有关键字,这个时候返回一个空的结果集,不报错
                List<ProductListVo> productListVoList = Lists.newArrayList();
                return ServerResponse.createBySuccess(new PageInfo(pageNum, pageSize, 0, productListVoList));
            }
            categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
        }

        //排序处理
//        if(StringUtils.isNotBlank(orderBy)){
//            if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
//                String[] orderByArray = orderBy.split("_");
//                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
//            }
//        }

        int offset = PageUtil.skip(pageNum, pageSize);
        Pair<Long, List<Product>> pair = productTable.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword,categoryIdList.size()==0?null:categoryIdList, offset, pageSize);

        List<Product> productList = pair.getSecond();

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product product : productList){
            ProductListVo productListVo = assembleProductListVo(product);
            productListVoList.add(productListVo);
        }

        return ServerResponse.createBySuccess(new PageInfo(pageNum, pageSize, pair.getFirst(), productListVoList));
    }


























}
