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
package com.snailscoder.desk.wrapper;

import com.snailscoder.core.mp.support.BaseEntityWrapper;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.core.tool.utils.BeanUtil;
import com.snailscoder.core.tool.utils.SpringUtil;
import com.snailscoder.desk.entity.Notice;
import com.snailscoder.desk.vo.NoticeVO;
import com.snailscoder.upms.feign.IDictClient;

/**
 * Notice包装类,返回视图层所需的字段
 *
 * @author snailscoder
 */
public class NoticeWrapper extends BaseEntityWrapper<Notice, NoticeVO> {

	private static IDictClient dictClient;

	static {
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static NoticeWrapper build() {
		return new NoticeWrapper();
	}

	@Override
	public NoticeVO entityVO(Notice notice) {
		NoticeVO noticeVO = BeanUtil.copy(notice, NoticeVO.class);
		R<String> dict = dictClient.getValue("notice", noticeVO.getCategory());
		if (dict.isSuccess()) {
			String categoryName = dict.getData();
			noticeVO.setCategoryName(categoryName);
		}
		return noticeVO;
	}

}
