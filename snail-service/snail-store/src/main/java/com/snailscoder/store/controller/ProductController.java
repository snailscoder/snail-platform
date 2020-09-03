package com.snailscoder.store.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.snailscoder.store.entity.Product;
import com.snailscoder.store.service.IProductService;
import com.snailscoder.store.vo.ProductVO;
import com.snailscoder.store.wrapper.ProductWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import com.snailscoder.common.cache.CacheNames;
import com.snailscoder.core.boot.ctrl.BladeController;
import com.snailscoder.core.mp.support.Condition;
import com.snailscoder.core.mp.support.Query;
import com.snailscoder.core.tool.api.R;
import com.snailscoder.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

/**
 * @author: snailscoder
 * @date: 2020/8/19 下午7:22
 */
@RestController
@RequestMapping("/product")
@AllArgsConstructor
@Api(value = "店铺商品", tags = "商品接口")
public class ProductController extends BaseStoreController implements CacheNames {

	private IProductService productService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入notice")
	public R<ProductVO> detail(@RequestParam("id") Long id) {
		Product product = productService.getById(id);
		return R.data(ProductWrapper.build().entityVO(product));
	}

	/**
	 * 分页 用户表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入product")
	public R<IPage<ProductVO>> list(Product product, Query query) {
		IPage<Product> pages = productService.page(Condition.getPage(query), Condition.getQueryWrapper(product));
		return R.data(ProductWrapper.build().pageVO(pages));
	}

	/**
	 * 自定义分页 用户表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入product")
	public R<IPage<ProductVO>> page(ProductVO product, Query query) {
		IPage<ProductVO> pages = productService.selectProductPage(Condition.getPage(query), product);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入商品详细信息")
	public R save(@RequestBody Product product) {
		return R.status(productService.save(product));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "传入商品详细信息")
	public R update(@RequestBody Product product) {
		return R.status(productService.updateById(product));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增或修改", notes = "传入商品详细信息")
	public R submit(@RequestBody Product product) {
		return R.status(productService.saveOrUpdate(product));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "逻辑删除", notes = "传入notice")
	public R remove(@ApiParam(value = "主键集合") @RequestParam String ids) {
		boolean temp = productService.deleteLogic(Func.toLongList(ids));
		return R.status(temp);
	}

}
