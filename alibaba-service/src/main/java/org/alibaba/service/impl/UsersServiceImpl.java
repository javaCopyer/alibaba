package org.alibaba.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.alibaba.common.PageResult;
import org.alibaba.common.QueryForPage;
import org.alibaba.dao.mapper.UsersMapper;
import org.alibaba.dao.pojo.Users;
import org.alibaba.dao.pojo.UsersExample;
import org.alibaba.dao.pojo.UsersExample.Criteria;
import org.alibaba.service.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
@Service
public class UsersServiceImpl implements UsersService {
	@Resource
	private UsersMapper usersMapper;
	
	@Override
	@Transactional
	public int updateByPrimaryKey(Users record1, Users record2) {
		
		usersMapper.updateByPrimaryKey(record1);
		int i = 1 / 0; //手动制造异常，测试事务
		usersMapper.updateByPrimaryKey(record2);
		return 1;
	}
	@Override
	public List<Users> queryUsersByPage() {
		PageHelper.startPage(1, 3);
		List<Users> list = usersMapper.selectByExample(new UsersExample());
		return list;
	}	
	
	@Override
	public PageResult queryByPage(QueryForPage qp) {
		UsersExample example = new UsersExample();
		Criteria criteria = example.createCriteria();
		Users users = (Users) qp.getObj(); //查询条件实体
		
		StringBuffer sb = new StringBuffer();
		sb.append(qp.getOrderBy()).append(qp.getPageCondition());
		example.setOrderByClause(sb.toString());
		List<Users> list = usersMapper.selectByExample(example);
		int totalRows = (int) countByExample(example);
		return new PageResult(qp.getCurrentPage(), qp.getPageLimit(), totalRows , list);
	}
	@Override
	public long countByExample(UsersExample example) {
		return usersMapper.countByExample(example);
	}

}
