package com.gpy.alltest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName AppMyOrderInfoGoodsVo
 * @Description App端订单信息中商品信息值对象
 * @Author guopy
 * @Date 2021/11/8 16:03
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppMyOrderInfoGoodsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String goodsId;

    private String goodsName;

    private String goodsSpec;

    private Integer goodsCount;

    private BigDecimal goodsPrice;

    private String goodsImg;

    private String goodsCartId;

    private Boolean isApply;

    private Integer afterSaleStatus;

}
