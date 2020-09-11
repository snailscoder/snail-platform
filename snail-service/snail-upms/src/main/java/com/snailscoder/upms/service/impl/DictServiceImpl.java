/*
 * Copyright (c) 2018-2028, snailscoder (huaxin803@gmail.com).
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
package com.snailscoder.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.snailscoder.core.mp.base.BaseServiceImpl;
import com.snailscoder.core.tool.utils.Func;
import com.snailscoder.core.tool.utils.StringPool;
import com.snailscoder.upms.entity.Dict;
import com.snailscoder.upms.mapper.DictMapper;
import com.snailscoder.upms.service.IDictService;
import com.snailscoder.upms.vo.DictVO;
import com.snailscoder.upms.wrapper.DictWrapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.snailscoder.common.cache.CacheNames.DICT_LIST;
import static com.snailscoder.common.cache.CacheNames.DICT_VALUE;

/**
 * 服务实现类
 *
 * @author snailscoder
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements IDictService {

	@Override
	public List<DictVO> tree() {
		return DictWrapper.build().listNodeVO(this.list());
	}

	@Override
	@Cacheable(cacheNames = DICT_VALUE, key = "#code+'_'+#dictKey")
	public String getValue(String code, Integer dictKey) {
		LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Dict::getCode,code);
		wrapper.eq(Dict::getDictKey,dictKey);
		Dict dict = this.getOne(wrapper);
		return Func.toStr(dict.getDictValue(), StringPool.EMPTY);
	}

	@Override
	@Cacheable(cacheNames = DICT_LIST, key = "#code")
	public List<Dict> getList(String code) {
		LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Dict::getCode,code);
		wrapper.ge(Dict::getDictKey,0);
		return this.list(wrapper);
	}

	@Override
	@CacheEvict(cacheNames = {DICT_LIST, DICT_VALUE}, allEntries = true)
	public boolean submit(Dict dict) {
		LambdaQueryWrapper<Dict> lqw = Wrappers.<Dict>query().lambda().eq(Dict::getCode, dict.getCode()).eq(Dict::getDictKey, dict.getDictKey());
		Integer cnt = baseMapper.selectCount((Func.isEmpty(dict.getId())) ? lqw : lqw.notIn(Dict::getId, dict.getId()));
		if (cnt > 0) {
			throw new ApiException("当前字典键值已存在!");
		}
		return saveOrUpdate(dict);
	}
}
