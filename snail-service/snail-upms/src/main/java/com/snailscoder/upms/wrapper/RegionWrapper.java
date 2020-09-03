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
package com.snailscoder.upms.wrapper;

import com.snailscoder.core.mp.support.BaseEntityWrapper;
import com.snailscoder.core.tool.node.ForestNodeMerger;
import com.snailscoder.core.tool.node.INode;
import com.snailscoder.core.tool.utils.BeanUtil;
import com.snailscoder.core.tool.utils.SpringUtil;
import com.snailscoder.upms.entity.Region;
import com.snailscoder.upms.service.IRegionService;
import com.snailscoder.upms.vo.RegionVO;

import java.util.List;
import java.util.Objects;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author snailscoder
 */
public class RegionWrapper extends BaseEntityWrapper<Region, RegionVO> {

	private static final IRegionService regionService;

	static {
		regionService = SpringUtil.getBean(IRegionService.class);
	}

	public static RegionWrapper build() {
		return new RegionWrapper();
	}

	@Override
	public RegionVO entityVO(Region region) {
		RegionVO regionVO = Objects.requireNonNull(BeanUtil.copy(region, RegionVO.class));
		Region parentRegion = regionService.getById(region.getParentCode());
		regionVO.setParentName(parentRegion.getName());
		return regionVO;
	}

	public List<INode> listNodeLazyVO(List<INode> list) {
		return ForestNodeMerger.merge(list);
	}

}
