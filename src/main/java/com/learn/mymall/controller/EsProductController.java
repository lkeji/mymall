package com.learn.mymall.controller;

import com.github.pagehelper.Page;
import com.learn.mymall.common.CommonPage;
import com.learn.mymall.common.CommonResult;
import com.learn.mymall.elasticsearch.document.EsProduct;
import com.learn.mymall.service.EsProductServiceI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: likj
 * @create: 2020-04-24 19:42
 * @description: ES搜索商品管理
 * @program: mymall
 */
@RestController
@Api(tags={"ES搜索商品管理"})
@RequestMapping("/esProduct")
public class EsProductController {


    @Autowired
    private EsProductServiceI esProductServiceI;

    @ApiOperation(value = "导入所有数据库中商品到ES")
    @RequestMapping(value = "/importAll", method = RequestMethod.POST)
    public CommonResult<Integer> importAllList() {
        int count = esProductServiceI.importAll();
        return CommonResult.success(count);
    }

    @ApiOperation(value = "根据id删除商品")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult<Object> delete(@PathVariable Long id) {
        esProductServiceI.delete(id);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id批量删除商品")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public CommonResult<Object> delete(@RequestParam("ids") List<Long> ids) {
        esProductServiceI.delete(ids);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id创建商品")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<EsProduct> create(@PathVariable Long id) {
        EsProduct esProduct = esProductServiceI.create(id);
        if (esProduct != null) {
            return CommonResult.success(esProduct);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "简单搜索")
    @RequestMapping(value = "/search/simple", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductServiceI.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }









}
