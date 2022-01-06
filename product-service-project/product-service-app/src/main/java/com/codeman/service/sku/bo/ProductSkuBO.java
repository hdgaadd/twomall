package com.codeman.service.sku.bo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hdgaadd
 * Created on 2022/01/06
 */
@Data
public class ProductSkuBO {

    /**
     * sku 编号
     */
    private Integer id;
    /**
     * 商品编号
     */
    private Integer spuId;
    /**
     * 状态
     *
     * 1-正常
     * 2-禁用
     */
    private Integer status;
    /**
     * 图片地址
     */
    private String picUrl;
    /**
     * 规格值编号数组
     */
    private List<Integer> attrValueIds;
    /**
     * 价格，单位：分
     */
    private Integer price;
    /**
     * 库存数量
     */
    private Integer quantity;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date updateTime;
    /**
     * 是否删除
     */
    private Integer deleted;

}
