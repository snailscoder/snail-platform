package com.snailscoder.store.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.snailscoder.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品表实体类
 *
 * @author snailscoder
 * @since 2020-08-19
 */
@Data
@TableName("snail_product_sku")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProductSKU对象", description = "商品SKU")
public class ProductSku extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id")
	private Long id;
	/**
	 * 店铺ID
	 */
	@ApiModelProperty(value = "店铺ID")
	private Long storeId;

	@ApiModelProperty(value = "商品ID")
	private Long productId;
	/**
	 * 规格名称
	 */
	@ApiModelProperty(value = "规格名称")
	private String skuName;
	/**
	 * 代理价
	 */
	@ApiModelProperty(value = "代理价")
	private BigDecimal agentPrice;
	/**
	 * 零售价
	 */
	@ApiModelProperty(value = "零售价")
	private BigDecimal retailPrice;

	/**
	 * 成本价
	 */
	@ApiModelProperty(value = "成本价")
	private BigDecimal costPrice;
}
