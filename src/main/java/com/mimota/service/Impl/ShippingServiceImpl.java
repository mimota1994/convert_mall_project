package com.mimota.service.Impl;

import com.google.common.collect.Maps;
import com.mimota.dao.table.ShippingTable;
import com.mimota.pojo.Shipping;
import com.mimota.service.IShippingService;
import com.mimota.util.PageUtil;
import com.mimota.util.common.PageInfo;
import com.mimota.util.common.Pair;
import com.mimota.util.common.ServerResponse;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by geely
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {


    @Autowired
    private ShippingTable shippingTable;

    public ServerResponse add(String userId, Shipping shipping){
        shipping.setUserId(userId);
        Key<Shipping> key = shippingTable.insert(shipping);
        if(key.getId() != null){
            Map result = Maps.newHashMap();
            result.put("shippingId",key.getId());
            return ServerResponse.createBySuccess("新建地址成功",result);
        }
        return ServerResponse.createByErrorMessage("新建地址失败");
    }

    public ServerResponse<String> del(String userId,String shippingId){
        int resultCount = shippingTable.deleteByShippingIdUserId(userId,shippingId);
        if(resultCount > 0){
            return ServerResponse.createBySuccess("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }


    public ServerResponse update(String userId, String shipping, Map<String, String> conditions){
        int rowCount = shippingTable.updateByShippingIdUserId(userId, shipping, conditions);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新地址成功");
        }
        return ServerResponse.createByErrorMessage("更新地址失败");
    }

    public ServerResponse<Shipping> select(String userId, String shippingId){
        Shipping shipping = shippingTable.selectByShippingIdUserId(userId,shippingId);
        if(shipping == null){
            return ServerResponse.createByErrorMessage("无法查询到该地址");
        }
        return ServerResponse.createBySuccess(shipping);
    }


    public ServerResponse<PageInfo> list(String userId, int pageNum, int pageSize){
//        PageHelper.startPage(pageNum,pageSize);
        final int offset = PageUtil.skip(pageNum, pageSize);

        Pair<Long, List<Shipping>> pair = shippingTable.search(userId, offset, pageSize);

        return ServerResponse.createBySuccess(new PageInfo<List<Shipping>>(pageNum,pageSize,pair.getFirst(), pair.getSecond()));
    }

}
