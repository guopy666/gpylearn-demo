package com.gpy.alltest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName AppMyOrderInfoVo
 * @Description
 * @Author guopy
 * @Date 2021/11/8 16:03
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppMyOrderInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String orderName;

    private BigDecimal goodsAmount;

    private String msg;

    private String orderNo;

    private String creatorTime;

    private String payOrder;

    private Integer orderStatus;

    private String paidTime;

    private String payRequestTime;

    private String shipName;

    private String shipCode;

    private String shipTime;

    private BigDecimal shipPrice;

    private BigDecimal payPrice;

    private BigDecimal totalPrice;

    private String channel;

    private Integer goodsCount;

    private String addrDetail;

    private String remarkers;

    private String storeId;

    private String storeName;

    private List<AppMyOrderInfoGoodsVo> goodsInfoRsList;

}
