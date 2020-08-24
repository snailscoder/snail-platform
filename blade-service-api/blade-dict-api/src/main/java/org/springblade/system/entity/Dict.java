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
package org.springblade.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.snailscoder.core.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实体类
 *
 * @author Chill
 * @since 2018-12-24
 */
@Data
@TableName("blade_dict")
@ApiModel(value = "Dict对象", description = "Dict对象")
public class Dict extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 父主键
	 */
	@ApiModelProperty(value = "父主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	/**
	 * 字典码
	 */
	@ApiModelProperty(value = "字典码")
	private String code;

	/**
	 * 字典值
	 */
	@ApiModelProperty(value = "字典值")
	private Integer dictKey;

	/**
	 * 字典名称
	 */
	@ApiModelProperty(value = "字典名称")
	private String dictValue;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	 * 字典备注
	 */
	@ApiModelProperty(value = "字典备注")
	private String remark;
}
