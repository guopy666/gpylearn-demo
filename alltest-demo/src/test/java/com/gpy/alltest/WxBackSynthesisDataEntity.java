package com.gpy.alltest;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 小程序运营后台首页数据
 *
 * @author guopy
 * @since 2021-11-25
 */
@Data
@Accessors(chain = true)
public class WxBackSynthesisDataEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 小区ID
     */
    private String orgId;

    /**
     * 小区名称
     */
    private String orgName;

    /**
     * 统计日期yyyy-MM-dd
     */
    private String countDate;

    /**
     * 小区新增用户
     */
    private Integer newUserCount;

    /**
     * 小区累计用户
     */
    private Integer totalUserCount;

    /**
     * 小区累计下单用户
     */
    private Integer totalPayUserCount;


}
