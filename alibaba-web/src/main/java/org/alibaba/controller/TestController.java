package org.alibaba.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alibaba.common.redis.RedisSubscribeThread;
import org.alibaba.common.redis.RedisUtil;
import org.alibaba.common.util.MessageI18nUtil;
import org.alibaba.controller.common.ResBean;
import org.alibaba.controller.exception.SysException;
import org.alibaba.dao.mapper.User;
import org.alibaba.dao.mapper.UserXml;
import org.alibaba.dao.mapper.UsersMapper;
import org.alibaba.dao.pojo.Users;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestController {
	@Autowired
	private UsersMapper usersMapper;
	public TestController() {
		System.out.println("TestController is loading...");
	}
	
	//consumes 表示请求的contenttype为指定的类型 否则报状态码为415
	 @RequestMapping(value = "/testHello", consumes = {  
		        "application/JSON",  
		        "application/XML"  
		    })  
    public String testHello(Model model, @RequestParam(required = false) String name) {
		 model.addAttribute("testName", "zc");
        return "test";
    }
	 
	 @RequestMapping("/test")
    public String testHello(Model model) throws Exception {
		 model.addAttribute("testName", "zc");
		 return "test";
	 }
	 
	 
 	@RequestMapping(name="getjson")
	public User getJson(User user, HttpServletRequest request) throws Exception {
 		user.setName(MessageI18nUtil.getMessage(request, "password"));
 		return  user;
 	}
 	@RequestMapping("getxml")
 	public UserXml getXml(UserXml userXml) {
 		return userXml;
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
 	

// 	@ModelAttribute(name="modelAttribute")
//	public String getModelAttribute() {
//		String str = "modelAttribute";
//		return str;
//	}
	
	
	void login() throws Exception {
		throw new SysException("登录异常", "10111");
	}

}
