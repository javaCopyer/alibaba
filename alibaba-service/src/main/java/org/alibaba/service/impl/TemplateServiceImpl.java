package org.alibaba.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.alibaba.common.PageResult;
import org.alibaba.common.QueryForPage;
import org.alibaba.dao.mapper.TemplateMapper;
import org.alibaba.dao.pojo.Template;
import org.alibaba.service.TemplateService;
import org.springframework.stereotype.Service;
@Service
public class TemplateServiceImpl implements TemplateService{
	@Resource
	private TemplateMapper templateMapper;
	@Override
	public int insert(Template record) {
		return templateMapper.insert(record);
	}

	@Override
	public int insertSelective(Template record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Template selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Template> selectByExample(Template record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResult selectByPage(QueryForPage qp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Template record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
