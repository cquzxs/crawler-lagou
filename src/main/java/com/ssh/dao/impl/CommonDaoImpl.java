package com.ssh.dao.impl;

import com.ssh.dao.api.ICommonDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository("commonDao")//spring之bean自动装配注解
public class CommonDaoImpl implements ICommonDao {

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public Session getSession() {
        return this.sessionFactory.openSession();
    }
}
