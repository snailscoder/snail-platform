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
package com.snailscoder.upms.wrapper;

import com.snailscoder.core.mp.support.BaseEntityWrapper;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.core.tool.utils.BeanUtil;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.core.tool.utils.SpringUtil;
import com.snailscoder.upms.feign.IDictClient;
import com.snailscoder.upms.entity.User;
import com.snailscoder.upms.service.IUserService;
import com.snailscoder.upms.vo.UserVO;

import java.util.List;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author snailscoder
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

	private static final IUserService userService;

	private static final IDictClient dictClient;

	static {
		userService = SpringUtil.getBean(IUserService.class);
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = BeanUtil.copy(user, UserVO.class);
		List<String> roleName = userService.getRoleName(user.getRoleId());
		userVO.setRoleName(Func.join(roleName));
		R<String> dict = dictClient.getValue("sex", Func.toInt(user.getSex()));
		if (dict.isSuccess()) {
			userVO.setSexName(dict.getData());
		}
		return userVO;
	}

}
