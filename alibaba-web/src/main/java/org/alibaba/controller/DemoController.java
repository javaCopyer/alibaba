package org.alibaba.controller;

import javax.annotation.Resource;

import org.alibaba.common.PageResult;
import org.alibaba.common.QueryForPage;
import org.alibaba.common.annotation.LogAdd;
import org.alibaba.controller.common.ResBean;
import org.alibaba.dao.pojo.Users;
import org.alibaba.service.UsersService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/demo")
@Api(value="demo", description="测试")
public class DemoController {
	@Resource
	private UsersService userService;
	
	
	@RequestMapping(value="testService", method=RequestMethod.GET)
	@ApiOperation(value="支付宝APP",httpMethod="POST")
 	public ResBean testService(Users users) throws Exception {
 		ResBean resBean = new ResBean();
 		QueryForPage qp = new QueryForPage(1, 5, "id asc", null);
 		PageResult pageResult = userService.queryByPage(qp);
 		resBean.setDatas(pageResult);
 		return resBean;
 	}
}
