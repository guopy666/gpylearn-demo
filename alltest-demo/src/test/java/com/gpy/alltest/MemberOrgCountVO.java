package com.gpy.alltest;

import lombok.Data;

/**
 * @ClassName MemberOrgCountVO
 * @Description
 * @Author guopy
 * @Date 2021/12/14 15:48
 */
@Data
public class MemberOrgCountVO {
    public String wuYeOrgId;
    public String wuYeOrgName;
    public String countTime;
    public int countMember = 0;

    public MemberOrgCountVO() {
    }

    public String getWuYeOrgId() {
        return this.wuYeOrgId;
    }

    public String getWuYeOrgName() {
        return this.wuYeOrgName;
    }

    public String getCountTime() {
        return this.countTime;
    }

    public int getCountMember() {
        return this.countMember;
    }

    public void setWuYeOrgId(String wuYeOrgId) {
        this.wuYeOrgId = wuYeOrgId;
    }

    public void setWuYeOrgName(String wuYeOrgName) {
        this.wuYeOrgName = wuYeOrgName;
    }

    public void setCountTime(String countTime) {
        this.countTime = countTime;
    }

    public void setCountMember(int countMember) {
        this.countMember = countMember;
    }


}
