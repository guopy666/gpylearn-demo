package com.gpy.alltest;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品购物车表
 *
 * @author mizr
 * @since 2021-11-06
 */
@Data
public class GoodsCartEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * spu表主键ID goods_spu#id
     */
    private String spuId;

    /**
     * sku表主键ID goods_sku#id
     */
    private String skuId;

    /**
     * 商品数量
     */
    private Integer count;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 实付金额 包含购物车 freight
     */
    private BigDecimal payPrice;

    /**
     * 供价方式结算时一件商品的供价
     */
    private BigDecimal supplyPrice;

    /**
     * 规格属性明细，JSON格式:{\"颜色\":\"红色\",\"版本\":\"128GB\"}
     */
    private String specDetails;

    /**
     * 小订单id
     */
    private String orderId;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 店铺ID
     */
    private String storeId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品主图
     */
    private String goodsImage;

    /**
     * 商品详情
     */
    private String goodsDetails;

    /**
     * 加购来源：1正常下单，2抢购秒杀，3赠品
     */
    private Integer sourceFrom;

    /**
     * 加购来源ID，正常下单为0，抢购秒杀赠品等填写具体关联id
     */
    private Integer sourceFromId;

    /**
     * 是否显示在用户的购物车列表中，0：显示，1：不显示
     */
    private Integer display;

    /**
     * 是否可评价 0:不可坪价 1: 可评价 ，订单完成更新该字段为可评价
     */
    private Integer canEvaluation;

    /**
     * 评价次数
     */
    private Integer evaluation;

    /**
     * 0:未生成订单,1:已生成订单
     */
    private Integer cartStatus;

    /**
     * 0:未申请售后,1:已申请售后
     */
    private Integer isApply;

    /**
     * 运费 0代表包邮
     */
    private BigDecimal freight;

    /**
     * 重量（克）
     */
    private Integer weight;

    /**
     * 购物车状态 0:正常 1:缺货 2:下架
     */
    private Integer cartGoodsStatus;

    /**
     * 售后状态 0:售后申请中; 1:退款中; 2:商家已拒绝; 3:取消申请; 4:等待退货; 5退款完成; 6:售后失败;7:退货物流中;
     */
    private Integer afterSaleStatus;

    /**
     * 是否启用规格, 0:单规格, 1:多规格
     */
    private Integer isEnableSpec= 0;

    /**
     * 修改管理员ID
     */
    protected String lastModifyManagerId;

    /**
     * 是否能申请售后：1-能，0-不能
     * 记录特殊产品流程设定：
     * 1、已申请售后成功的用户，不能再次申请售后
     *    ---> 处理方案：用户售后单变更为售后成功时，更新 canApply = 0 ，代表不再支持售后申请
     * 2、如果商家拒绝后，重新修改提交售后，他再次撤销售后，订单无法再次申请
     *    ---> 处理方案：对于存在商家拒绝记录（次数refuseTimes）记录，如果发起取消，那么更新 canApply = 0 ，代表不再支持售后申请
     * 3、用户正常申请售后并撤销，售后订单状态改为“取消售后”，用户可重新申请售后，无限制
     *    ---> 默认就支持
     */
    private Integer canApply= 0;

    /**
     * 售后单被商家拒绝次数
     * @author 大潘
     * @since 2021-11-30
     * @return
     **/
    private Integer refuseTimes= 0;

}
