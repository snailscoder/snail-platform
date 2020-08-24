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
package org.springblade.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snailscoder.core.mybatis.base.TenantEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_user_oauth")
public class UserOauth extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 第三方系统用户ID
	 */
	private String uuid;

	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户主键")
	private Long userId;

	@ApiModelProperty(value = "微信unionId")
	private String unionId;

	/**
	 * 所属店铺
	 */
	private Long storeId;

	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户头像
	 */
	private String avatar;
	/**
	 * 用户网址
	 */
	private String blog;
	/**
	 * 所在公司
	 */
	private String company;
	/**
	 * 位置
	 */
	private String location;
	/**
	 * 用户邮箱
	 */
	private String email;
	/**
	 * 用户备注（各平台中的用户个人介绍）
	 */
	private String remark;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 用户来源
	 */
	private String source;


}
