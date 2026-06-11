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
public class AppUnPayOrderInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String payOrder;

    private Long waitPayTime;

    private BigDecimal totalFee;

    private BigDecimal shipPrice;

    private String creatorTime;

    private BigDecimal goodsAmount;

    private Integer goodsTotalCount;


    private List<AppMyOrderInfoVo> orderInfoVoList;

}
