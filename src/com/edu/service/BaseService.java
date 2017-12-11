package com.edu.service;

import javax.annotation.Resource;

import com.fh.dao.DaoSupport;
import com.fh.util.Logger;

public class BaseService {
	protected Logger logger = Logger.getLogger(this.getClass());
	@Resource(name = "daoSupport")
	protected DaoSupport dao;
}
