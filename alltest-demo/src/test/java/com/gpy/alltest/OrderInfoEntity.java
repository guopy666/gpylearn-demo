package com.gpy.alltest;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表
 *
 * @author mizr
 * @since 2021-11-06
 */
@Data
public class OrderInfoEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单名称
     */
    private String orderName;

    /**
     * 商品总价格
     */
    private BigDecimal goodsAmount;

    /**
     * 买家留言
     */
    private String msg;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单状态;0:已关闭(已取消), 10:待付款,11:付款确认中 20:已付款, 30:已发货, 40:已收货, 50:已完成
     */
    private Integer orderStatus;

    /**
     * 订单上一状态
     */
    private Integer orderPrestatus;

    /**
     * 付款完成时间
     */
    private LocalDateTime paidTime;

    /**
     * 提交支付请求时间
     */
    private LocalDateTime payRequestTime;

    /**
     * 快递公司名
     */
    private String shipName;

    /**
     * 快递单号
     */
    private String shipCode;

    /**
     * 发货时间
     */
    private LocalDateTime shipTime;

    /**
     * 运费
     */
    private BigDecimal shipPrice;

    /**
     * 最终支付价钱,totalPrice=goods_amount+ship_price
     */
    private BigDecimal totalPrice;

    /**
     * 详细地址 {\"areaId\":\"1\",\"userName\":\"张三\",\"mobile\":\"154784595478\",\"detail\":\"河南省 郑州市 郑东新区 龙子湖街道 人保大厦\"}
     */
    private String addrDetail;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 支付渠道
     */
    private String channel;

    /**
     * pay_info主键ID 支付单号 待支付订单列表可用该字段分组查询
     */
    private String payOrder;

    /**
     * 实际支付价格
     */
    private BigDecimal payPrice;

    /**
     * 当前小区
     */
    private String orgId;

    /**
     * 小区名称
     */
    private String orgName;

    /**
     * 快速支付的订单参数from@refid
     */
    private String quickParam;

    /**
     * 订单备注
     */
    private String remarkers;

    /**
     * 店铺id
     * */
    private String storeId;

    private String storeName;

    /**
     * 是否在app端订单列表 中显示 0:显示 1:不显示
     * */
    private Integer showOrder;

    /**
     * 快递公司中文名
     * */
    private String shipNameCh;

    /**
     * 下单用户手机号
     * */
    private String userMobile;

    /**
     * 最近一次管理端用户修改者ID
     * */
    private String lastModifyManagerId;

    /**
     * 订单来源  wxapp 小程序  saaszxjf 物业缴费
     * */
    private String srcFrom;

}
