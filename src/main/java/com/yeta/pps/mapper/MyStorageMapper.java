package com.yeta.pps.mapper;

import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.StorageOrderVo;
import com.yeta.pps.vo.StorageResultOrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YETA
 * @date 2018/12/11/15:59
 */
public interface MyStorageMapper {

    //收/发货单

    int addStorageOrder(StorageOrderVo storageOrderVo);

    int redDashed(StorageOrderVo storageOrderVo);

    int findCountStorageOrder(StorageOrderVo storageOrderVo);

    List<StorageOrderVo> findAllPagedStorageOrder(@Param(value = "storageOrderVo") StorageOrderVo storageOrderVo,
                                                  @Param(value = "pageVo")PageVo pageVo);

    StorageOrderVo findStorageOrderById(StorageOrderVo storageOrderVo);

    //其他入/出库单

    int addStorageResultOrder(StorageResultOrderVo storageResultOrderVo);

    int redDashedStorageResultOrder(StorageResultOrderVo storageResultOrderVo);

    int findCountStorageResultOrder(StorageResultOrderVo storageResultOrderVo);

    List<StorageResultOrderVo> findAllPagedStorageResultOrder(@Param(value = "vo") StorageResultOrderVo storageResultOrderVo,
                                                              @Param(value = "pageVo")PageVo pageVo);

    StorageResultOrderVo findStorageResultOrderDetailById(StorageResultOrderVo storageResultOrderVo);
}
