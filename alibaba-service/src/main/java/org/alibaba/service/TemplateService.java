package org.alibaba.service;

import java.util.List;

import org.alibaba.common.PageResult;
import org.alibaba.common.QueryForPage;
import org.alibaba.dao.pojo.Template;

public interface TemplateService {
	//插入-全部字段
	int insert(Template record);
	//插入-部分字段
	int insertSelective(Template record);
	//删除-主键
	int deleteByPrimaryKey(Integer id);
	//查询-主键
	Template selectByPrimaryKey(Integer id);
	//查询-条件
	List<Template> selectByExample(Template record);
	//查询-分页
	PageResult selectByPage(QueryForPage qp);
	//修改-主键-部分字段
	int updateByPrimaryKeySelective(Template record);
	
}
