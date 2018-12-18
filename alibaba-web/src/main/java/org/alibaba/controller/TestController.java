package org.alibaba.controller;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.alibaba.common.annotation.LogAdd;
import org.alibaba.common.redis.RedisSubscribeThread;
import org.alibaba.common.redis.RedisUtil;
import org.alibaba.common.util.MessageI18nUtil;
import org.alibaba.controller.common.ResBean;
import org.alibaba.controller.exception.SysException;
import org.alibaba.dao.mapper.User;
import org.alibaba.dao.mapper.UserXml;
import org.alibaba.dao.mapper.UsersMapper;
import org.alibaba.dao.pojo.Users;
import org.alibaba.service.CommonService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/test")
public class TestController {
	@Resource
	private CommonService commonService;
	@Autowired
	private UsersMapper usersMapper;
	public TestController() {
		System.out.println("TestController is loading...");
	}
	
	//consumes 表示请求的contenttype为指定的类型 否则报状态码为415
	//Content type 'null' not supported
	 @RequestMapping(value = "/consumes123", consumes = {  
		        "application/JSON",  
		        "application/XML"  
		    })  
    public String testHello(Model model, @RequestParam(required = false) String name) {
		 model.addAttribute("testName", "zc");
        return "test";
    }
	 
	@RequestMapping("/test")
	@LogAdd(opt=LogAdd.OPT_TYPE_DEL, title="test")
    public String testHello(Model model) throws Exception {
		 model.addAttribute("testName", "zc");
		 return "test";
	 }
	 
	 
 	@RequestMapping(name="getjson")
	public User getJson(User user, HttpServletRequest request) throws Exception {
 		user.setName(MessageI18nUtil.getMessage(request, "password"));
 		return  user;
 	}
 	@RequestMapping("getview")
 	public ModelAndView getView(User user) {
 		ModelAndView mv = new ModelAndView("xmlview");
 		mv.addObject("user", user);
 		return mv;
 		
 	}
 	
 	@RequestMapping("testRedis")
 	public String testRedis(User user, HttpServletRequest request) throws Exception {
 		RedisUtil.set("jun", "clearlove");
 		return RedisUtil.get("jun");
 	}
 	
 	
 	@RequestMapping("publish")
 	public String publish() throws Exception {
 		String random = RandomStringUtils.randomAlphanumeric(6);
 		RedisUtil.publish("test", random);
 		return random;
 	}
 	@RequestMapping("startjob")
 	public String startjob() throws Exception {
 		RedisSubscribeThread thread = new RedisSubscribeThread();
 		thread.start();
 		
 		return "启动成功";
 		
 	}
 	
 	@RequestMapping(value="testService", method=RequestMethod.GET)
 	public ResBean testService(Users users) throws Exception {
 		ResBean resBean = new ResBean();
// 		Users users1 = new Users();
// 		users1.setUsername("user1");
// 		users1.setPassword("user1");
// 		Users users2 = new Users();
// 		users2.setUsername("user2");
// 		users2.setPassword("user2");
// 		List<Users> list = new ArrayList<Users>();
// 		list.add(users1);
// 		list.add(users2);
// 		usersMapper.batchInsert(list);
 		List<String> list = new ArrayList<String>();
 		list.add("zxq");
 		list.add("user1");
 		list.add("user2");
 		Map<String, Object> updateMap = new HashMap<String, Object>();
 		updateMap.put("password", "12345678");
 		updateMap.put("names", list);
 		usersMapper.batchUpdate(updateMap);
// 		UsersExample usersExample = new UsersExample();
// 		usersExample.createCriteria().andUsernameEqualTo(Username);
// 		List<Users> users = usersMapper.selectByExample(usersExample);
// 		resBean.setDatas(users);
 		return resBean;
 	}
 	
 	/**
 	 * @ModelAttribute注解会在同一个controller下的其他@RequestMapping之前执行
 	 * 放入到模型（Model）之中, view之中可以使用${modelAttribute}获取到其中的值
 	 * 或者在其他的@RequestMapping方法参数中使用@ModelAttribute(name="modelAttribute")
 	 * 他会将参数的值填充
 	 * @return
 	 */
 	@ModelAttribute(name="modelAttribute")
	public String getModelAttribute() {
		String str = "modelAttribute";
		return str;
	}
 	@RequestMapping("modelAttribute")
 	public UserXml getXml(UserXml userXml, @ModelAttribute(name="modelAttribute") String name) {
 		userXml.setName(name);
 		return userXml;
 	}
	
 	
	void login() throws Exception {
		throw new SysException("登录异常", "10111");
	}

}
