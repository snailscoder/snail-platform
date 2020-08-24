package com.snailscoder.store.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snailscoder.core.mybatis.base.TenantEntity;
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
@TableName("snails_product")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Product对象", description = "商品表")
public class Product extends TenantEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 店铺ID
	 */
	@ApiModelProperty(value = "店铺ID")
	private Long storeId;
	/**
	 * 产品名称
	 */
	@ApiModelProperty(value = "产品名称")
	private String name;
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
	 * 商品主图
	 */
	@ApiModelProperty(value = "商品主图")
	private String mainImg;
	/**
	 * 商品描述
	 */
	@ApiModelProperty(value = "商品描述")
	private String intro;
}
