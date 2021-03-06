package com.codeman.rpc.sku.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hdgaadd
 * Created on 2022/01/06
 */
@Data
public class ProductSkuRespDTO {
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
/*    *//**
     * 规格值数组
     *
     * 需要设置 {@link ProductSkuDetailFieldEnum#ATTR} 才返回
     *//*
    private List<ProductAttrKeyValueRespDTO> attrs;
    *//**
     * 商品 SPU 信息
     *
     * 需要设置 {@link ProductSkuDetailFieldEnum#SPU} 才返回
     *
     * // TODO 芋艿，后续考虑怎么优化下，目前是内嵌了别的 dto
     *//*
    private ProductSpuRespDTO spu;*/
}
