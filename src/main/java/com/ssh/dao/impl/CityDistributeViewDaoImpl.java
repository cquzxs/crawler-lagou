package com.ssh.dao.impl;

import com.ssh.dao.api.ICityDistributeViewDao;
import com.ssh.dao.api.ICommonDao;
import com.ssh.entity.CityDistributeView;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository("cityDistributeViewDao")
public class CityDistributeViewDaoImpl implements ICityDistributeViewDao{

    @Resource(name="commonDao")
    private ICommonDao commonDao;

    @Override
    public boolean addList(List<CityDistributeView> list) {
        boolean state=false;
        try {
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            for (CityDistributeView s:list) {
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
            List<CityDistributeView> list=this.selectAll();
            for (CityDistributeView s:list) {
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
    public List<CityDistributeView> selectAll() {
        List<CityDistributeView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql="from CityDistributeView";
            Query<CityDistributeView> query= session.createQuery(hql);
            list=query.list();
            session.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<CityDistributeView> selectByJobName(String jobName,String isSchool) {
        List<CityDistributeView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql=" from CityDistributeView c where jobName=:jobName and isSchool=:isSchool order by c.count desc";
            Query<CityDistributeView> query= session.createQuery(hql);
            query.setParameter("jobName",jobName);
            query.setParameter("isSchool",Integer.parseInt(isSchool));
            list=query.list();
            session.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<CityDistributeView> selectCount() {
        List<CityDistributeView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql="select new CityDistributeView(s.jobName,s.cityName,CAST(count(*) as integer),s.isSchool) from CityDistributeView s group by s.jobName,s.cityName,s.isSchool";
            Query<CityDistributeView> query= session.createQuery(hql);
            list=query.list();
            session.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }
}
