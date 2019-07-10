package com.mimota.util;


import com.mimota.util.common.PageConstants;

/**
 * Desc:
 *
 * @author zhonghua.wu
 * @date 2019/5/22 21:04
 */
public class PageUtil {

    private PageUtil() {
    }

    public static int legalPageSize(int pageSize) {
        if (pageSize < 0) {
            return PageConstants.DEFAULT_PAGE_SIZE;
        }
        return Math.min(PageConstants.MAX_PAGE_SIZE, pageSize);
    }

    public static int legalPageNo(int pageNo) {
        return Math.max(1, pageNo);
    }

    public static int skip(int legalPageNo, int legalPageSize) {
        return (legalPageNo - 1) * legalPageSize;
    }

//    public static Pagination legalPagination(Pagination pagination) {
//        if (pagination == null) {
//            pagination = Pagination.builder().pageNo(1).pageSize(10).build();
//        }
//        return pagination;
//    }

}
