package com.snailscoder.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.snailscoder.store.entity.Product;
import com.snailscoder.store.vo.ProductVO;

import java.util.List;

/**
 * @author: snailscoder
 * @date: 2020/8/19 下午7:58
 */
public interface ProductMapper extends BaseMapper<Product> {
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param product
	 * @return
	 */
	List<ProductVO> selectProductPage(IPage page, ProductVO product);
}
