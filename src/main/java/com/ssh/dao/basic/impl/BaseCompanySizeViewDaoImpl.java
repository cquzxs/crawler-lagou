package com.ssh.dao.basic.impl;

import com.ssh.dao.basic.api.IBaseCompanySizeViewDao;
import com.ssh.dao.common.api.ICommonDao;
import com.ssh.model.entity.BaseCompanySizeView;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository("baseCompanySizeViewDao")
public class BaseCompanySizeViewDaoImpl implements IBaseCompanySizeViewDao{
    @Resource(name="commonDao")
    private ICommonDao commonDao;

    @Override
    public boolean addList(List<BaseCompanySizeView> list) {
        boolean state=false;
        try {
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            for (BaseCompanySizeView s:list) {
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
            List<BaseCompanySizeView> list=this.selectAll();
            for (BaseCompanySizeView s:list) {
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
    public List<BaseCompanySizeView> selectAll() {
        List<BaseCompanySizeView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql="from BaseCompanySizeView";
            Query<BaseCompanySizeView> query= session.createQuery(hql);
            list=query.list();
            session.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<BaseCompanySizeView> selectByJobName(String jobName,String isSchool) {
        List<BaseCompanySizeView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql=" from BaseCompanySizeView where jobName=:jobName and isSchool=:isSchool order by cityName";
            Query<BaseCompanySizeView> query= session.createQuery(hql);
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
