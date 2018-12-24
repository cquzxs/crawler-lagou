package com.ssh.dao.basic.impl;

import com.ssh.dao.common.api.ICommonDao;
import com.ssh.dao.basic.api.ISalaryCompareViewDao;
import com.ssh.entity.SalaryCompareView;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository("salaryCompareViewDao")
public class SalaryCompareViewDaoImpl implements ISalaryCompareViewDao{
    @Resource(name="commonDao")
    private ICommonDao commonDao;

    @Override
    public boolean addList(List<SalaryCompareView> list) {
        boolean state=false;
        try {
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            for (SalaryCompareView s:list) {
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
            List<SalaryCompareView> list=this.selectAll();
            for (SalaryCompareView s:list) {
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
    public List<SalaryCompareView> selectAll() {
        List<SalaryCompareView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql="from SalaryCompareView";
            Query<SalaryCompareView> query= session.createQuery(hql);
            list=query.list();
            session.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<SalaryCompareView> selectByJobName(String jobName,String isSchool) {
        List<SalaryCompareView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql=" from SalaryCompareView where jobName=:jobName and isSchool=:isSchool order by avgSarary desc ";
            Query<SalaryCompareView> query= session.createQuery(hql);
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

    @Override
    public List<SalaryCompareView> selectAvgSalary() {
        List<SalaryCompareView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql="select new SalaryCompareView(s.jobName,s.cityName,CAST(avg(s.avgSarary) as integer),s.isSchool) from SalaryCompareView s group by s.jobName,s.cityName,s.isSchool";
            Query<SalaryCompareView> query= session.createQuery(hql);
            list=query.list();
            session.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<SalaryCompareView> selectAvgSalaryByJobName(String isSchool) {
        List<SalaryCompareView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql="select new SalaryCompareView(s.jobName,CAST(avg(s.avgSarary) as integer) as a) from SalaryCompareView s where isSchool=:isSchool group by s.jobName order by a desc";
            Query<SalaryCompareView> query= session.createQuery(hql);
            query.setParameter("isSchool",Integer.parseInt(isSchool));
            list=query.list();
            session.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }
}
