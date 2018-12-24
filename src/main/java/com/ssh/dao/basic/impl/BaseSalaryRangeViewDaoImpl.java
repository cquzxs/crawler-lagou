package com.ssh.dao.basic.impl;

import com.ssh.dao.basic.api.IBaseSalaryRangeViewDao;
import com.ssh.dao.common.api.ICommonDao;
import com.ssh.model.entity.BaseSalaryRangeView;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Repository("baseSalaryRangeViewDao")
public class BaseSalaryRangeViewDaoImpl implements IBaseSalaryRangeViewDao{
    @Resource(name="commonDao")
    private ICommonDao commonDao;

    @Override
    public boolean addList(List<BaseSalaryRangeView> list) {
        boolean state=false;
        try {
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            for (BaseSalaryRangeView s:list) {
                session.save(s);
            }
            session.getTransaction().commit();
            state=true;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return state;
    }

    @Override
    public boolean deleteAll() {
        boolean state=false;
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            List<BaseSalaryRangeView> list=this.selectAll();
            for (BaseSalaryRangeView s:list) {
                session.delete(s);
            }
            session.getTransaction().commit();
            state=true;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return state;
    }

    @Override
    public List<BaseSalaryRangeView> selectAll() {
        List<BaseSalaryRangeView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql="from BaseSalaryRangeView";
            Query<BaseSalaryRangeView> query= session.createQuery(hql);
            list=query.list();
            session.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<BaseSalaryRangeView> selectByJobName(String jobName,String isSchool) {
        List<BaseSalaryRangeView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql=" from BaseSalaryRangeView where jobName=:jobName and isSchool=:isSchool";
            Query<BaseSalaryRangeView> query= session.createQuery(hql);
            query.setParameter("jobName",jobName);
            query.setParameter("isSchool",Integer.parseInt(isSchool));
            //query.setMaxResults(20);
            list=query.list();
            session.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }
}
