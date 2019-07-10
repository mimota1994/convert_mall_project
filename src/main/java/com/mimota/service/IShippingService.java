package com.mimota.service;

import com.mimota.pojo.Shipping;
import com.mimota.util.common.PageInfo;
import com.mimota.util.common.ServerResponse;

import java.util.Map;

/**
 * Created by geely
 */
public interface IShippingService {

    ServerResponse add(String userId, Shipping shipping);
    ServerResponse<String> del(String userId, String shippingId);
    ServerResponse update(String userId, String shippingId, Map<String, String> conditions);
    ServerResponse<Shipping> select(String userId, String shippingId);
    ServerResponse<PageInfo> list(String userId, int pageNum, int pageSize);

}
