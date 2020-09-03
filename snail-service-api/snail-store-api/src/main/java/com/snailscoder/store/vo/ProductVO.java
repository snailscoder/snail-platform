package com.snailscoder.store.vo;

import com.snailscoder.store.entity.Product;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品表视图实体类
 *
 * @author snailscoder
 * @since 2020-08-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProductVO对象", description = "商品表视图实体")
public class ProductVO extends Product {
	private static final long serialVersionUID = 1L;

}
