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
import com.snailscoder.core.mybatis.base.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_dept")
@ApiModel(value = "Dept对象", description = "Dept对象")
public class Dept extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 父主键
	 */
	@ApiModelProperty(value = "父主键")
	private Long parentId;

	/**
	 * 部门名
	 */
	@ApiModelProperty(value = "部门名")
	private String deptName;

	/**
	 * 部门全称
	 */
	@ApiModelProperty(value = "部门全称")
	private String fullName;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

}
