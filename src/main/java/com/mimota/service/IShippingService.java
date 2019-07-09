package com.mimota.service;

import com.github.pagehelper.PageInfo;
import com.mimota.pojo.Shipping;
import com.mimota.util.common.ServerResponse;

/**
 * Created by geely
 */
public interface IShippingService {

    ServerResponse add(String userId, Shipping shipping);
    ServerResponse<String> del(String userId, String shippingId);
    ServerResponse update(String userId, Shipping shipping);
    ServerResponse<Shipping> select(String userId, String shippingId);
    ServerResponse<PageInfo> list(String userId, int pageNum, int pageSize);

}
