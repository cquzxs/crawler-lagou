package com.ssh.dao.impl;

import com.ssh.dao.api.IBaseCreateTimeViewDao;
import com.ssh.dao.api.ICommonDao;
import com.ssh.entity.BaseCreateTimeView;
import com.ssh.entity.SalaryCompareView;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository("baseCreateTimeViewDao")
public class BaseCreateTimeViewDaoImpl implements IBaseCreateTimeViewDao{
    @Resource(name="commonDao")
    private ICommonDao commonDao;

    @Override
    public boolean addList(List<BaseCreateTimeView> list) {
        boolean state=false;
        try {
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            for (BaseCreateTimeView s:list) {
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
            List<BaseCreateTimeView> list=this.selectAll();
            for (BaseCreateTimeView s:list) {
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
    public List<BaseCreateTimeView> selectAll() {
        List<BaseCreateTimeView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql="from BaseCreateTimeView";
            Query<BaseCreateTimeView> query= session.createQuery(hql);
            list=query.list();
            session.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<BaseCreateTimeView> selectCount() {
        List<BaseCreateTimeView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql="select new BaseCreateTimeView(s.jobName,CAST(count(*) as integer),s.createTime,s.isSchool) from BaseCreateTimeView s group by s.jobName,s.createTime,s.isSchool";
            Query<BaseCreateTimeView> query= session.createQuery(hql);
            list=query.list();
            session.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<BaseCreateTimeView> selectByJobName(String jobName,String isSchool) {
        List<BaseCreateTimeView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql=" from BaseCreateTimeView where jobName=:jobName and isSchool=:isSchool order by createTime";
            Query<BaseCreateTimeView> query= session.createQuery(hql);
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
