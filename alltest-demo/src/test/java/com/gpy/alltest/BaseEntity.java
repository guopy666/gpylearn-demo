package com.gpy.alltest;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库实体基类
 * @author Meron
 * @date 2021-11-01 18:27
 */
@Data
public class BaseEntity implements Serializable {
    /**
     * 主键，使用雪花算法
     */
    protected String id;

    /**
     * 创建时间
     */
    protected Date creatorTime;

    /**
     * 创建用户ID
     */
    protected String creatorUserId;

    /**
     * 修改时间
     */
    protected Date lastModifyTime;

    /**
     * 修改用户ID
     */
    protected String lastModifyUserId;

    /**
     * 删除标志, 0:正常, 1:删除
     */
    protected Integer deleteMark=0;

    /**
     * 删除时间
     */
    protected Date deleteTime;

    /**
     * 删除用户ID
     */
    protected String deleteUserId;

    public Integer getDeleteMark() {
        if(deleteMark == null){
            deleteMark = 0;
        }
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        if(deleteMark == null){
            deleteMark = 0;
        }
        this.deleteMark = deleteMark;
    }
}
