/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.snailscoder.store.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.snailscoder.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 销售订单实体类
 *
 * @author snailscoder
 * @since 2020-08-20
 */
@Data
@TableName("snail_order")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Order对象", description = "销售订单")
public class Order extends BaseEntity {

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
	private String orderCode;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long buyerId;
	/**
	 * 销售人员ID
	 */
	@ApiModelProperty(value = "销售人员ID")
	private Long salerId;
	/**
	 * 销售团队ID
	 */
	@ApiModelProperty(value = "销售团队ID")
	private Long teamId;
	/**
	 * 收件地址ID
	 */
	@ApiModelProperty(value = "收件地址ID")
	private Long addressId;
	private BigDecimal orderAmount;
	private BigDecimal spreadAmount;
	/**
	 * 订单备注
	 */
	@ApiModelProperty(value = "订单备注")
	private String remark;


}
