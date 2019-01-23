package org.alibaba.service;

import java.util.List;

import org.alibaba.common.PageResult;
import org.alibaba.common.QueryForPage;
import org.alibaba.dao.pojo.Users;
import org.alibaba.dao.pojo.UsersExample;

public interface UsersService {
	int updateByPrimaryKey(Users record1, Users record2);
	List<Users> queryUsersByPage();
	PageResult queryByPage(QueryForPage qp);
	long countByExample(UsersExample example);
}
