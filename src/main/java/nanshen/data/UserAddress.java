/*
 * @(#)UserAddress.java, 2014-12-16.
 *
 * Copyright 2014 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * 用户地址信息数据表
 *
 * @author WANG Minghao
 */
@Table("UserAddress")
public class UserAddress {

    /** 数据库主键 */
    @Id
    private long id;

    /** 用户ID */
    @Column
    private long userId;

    /** 地址的一级地区ID */
    @Column
    private int level1;

    /** 地址的一级地区名字 */
    private String region1 = "";

    /** 地址的二级地区ID */
    @Column
    private int level2;

    /** 地址的二级地区名字 */
    private String region2 = "";

    /** 地址的三级地区ID */
    @Column
    private int level3;

    /** 地址的三级地区名字 */
    private String region3 = "";

    /** 地址的四级地区ID */
    @Column
    private int level4;

    /** 地址的四级地区名字 */
    private String region4 = "";

    /** 街道等详细地址信息 */
    @Column
    private String address = "";

    /** 邮编 */
    @Column
    private String code = "";

    /** 姓名 */
    @Column
    private String name = "";

    /** 电话 */
    @Column
    private String phone = "";

    /** 地址生成时间 */
    @Column
    private Date createTime = new Date();

    @Column
    private int displayOrder;

    /** 该地址用户是否可见 */
    @Column
    private boolean display;

    /** 身份证信息ID */
    @Column
    private long IDCardId;

    /** 地址是否被校验过 */
    @Column
    private boolean verified;

    public UserAddress(String address, int level1, int level2, int level3, String name,
                       String phone, long userId) {
        this.address = address;
        this.createTime = new Date();
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
        this.name = name;
        this.phone = phone;
        this.userId = userId;
    }

    public UserAddress() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getLevel1() {
        return level1;
    }

    public void setLevel1(int level1) {
        this.level1 = level1;
    }

    public String getRegion1() {
        return region1;
    }

    public void setRegion1(String region1) {
        this.region1 = region1;
    }

    public int getLevel2() {
        return level2;
    }

    public void setLevel2(int level2) {
        this.level2 = level2;
    }

    public String getRegion2() {
        return region2;
    }

    public void setRegion2(String region2) {
        this.region2 = region2;
    }

    public int getLevel3() {
        return level3;
    }

    public void setLevel3(int level3) {
        this.level3 = level3;
    }

    public String getRegion3() {
        return region3;
    }

    public void setRegion3(String region3) {
        this.region3 = region3;
    }

    public int getLevel4() {
        return level4;
    }

    public void setLevel4(int level4) {
        this.level4 = level4;
    }

    public String getRegion4() {
        return region4;
    }

    public void setRegion4(String region4) {
        this.region4 = region4;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public long getIDCardId() {
        return IDCardId;
    }

    public void setIDCardId(long IDCardId) {
        this.IDCardId = IDCardId;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

}
