package com.mimota.service;


import com.mimota.util.common.ServerResponse;
import com.mimota.vo.CartVo;

/**
 * Created by geely
 */
public interface ICartService {
    ServerResponse<CartVo> add(String userId, String productId, Integer count);
    ServerResponse<CartVo> update(String userId, String productId, Integer count);
    ServerResponse<CartVo> deleteProduct(String userId, String productIds);

    ServerResponse<CartVo> list(String userId);
    ServerResponse<CartVo> selectOrUnSelect(String userId, String productId, Integer checked);
    ServerResponse<Long> getCartProductCount(String userId);
}
