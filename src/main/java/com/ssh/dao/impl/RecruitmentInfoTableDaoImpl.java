package com.ssh.dao.impl;


import com.ssh.dao.api.ICommonDao;
import com.ssh.dao.api.IRecruitmentInfoTableDao;
import com.ssh.entity.BaseCompanySizeView;
import com.ssh.entity.BaseSalaryRangeView;
import com.ssh.entity.RecruitmentInfo;
import com.ssh.entity.SalaryCompareView;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository("recruitmentInfoTableDao")
public class RecruitmentInfoTableDaoImpl implements IRecruitmentInfoTableDao {
    @Resource(name="commonDao")
    private ICommonDao commonDao;

    @Override
    public boolean addRecruitmentInfoList(List<RecruitmentInfo> recruitmentInfoList) {
        boolean state=false;
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            for(RecruitmentInfo model:recruitmentInfoList){
                session.save(model);
            }
            session.getTransaction().commit();
            state=true;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return state;
    }

    @Override
    public boolean deleteAllRecruitmentInfo() {
        List<RecruitmentInfo> list=this.selectAllRecruitmentInfo();
        boolean state=false;
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            for(RecruitmentInfo model:list){
                session.delete(model);
            }
            session.getTransaction().commit();
            state=true;
        }catch (Exception e){
            //exception log
        }
        return state;
    }

    @Override
    public List<RecruitmentInfo> selectAllRecruitmentInfo() {
        Session session=this.commonDao.getSession();
        String hql="from RecruitmentInfo";
        Query<RecruitmentInfo> query=session.createQuery(hql);
        return query.list();
    }

    @Override
    public List<BaseCompanySizeView> selectBaseCompanySizeViewCount() {
        List<BaseCompanySizeView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql="select new BaseCompanySizeView(s.cityName,s.jobName,s.companySize,CAST(count(*) as integer),s.followsCount) from RecruitmentInfo s group by s.jobName,s.cityName,s.companySize,s.followsCount";
            Query<BaseCompanySizeView> query= session.createQuery(hql);
            list=query.list();
            session.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<BaseSalaryRangeView> selectBaseSalaryRangeViewCount() {
        List<BaseSalaryRangeView> list=new ArrayList<>();
        try{
            Session session=this.commonDao.getSession();
            session.beginTransaction();
            String hql="select new BaseSalaryRangeView(s.jobName,s.salaryRange,CAST(count(*) as integer),s.followsCount) from RecruitmentInfo s group by s.jobName,s.salaryRange,s.followsCount order by s.jobName desc";
            Query<BaseSalaryRangeView> query= session.createQuery(hql);
            list=query.list();
            session.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<String> getJobList() {
        Session session=this.commonDao.getSession();
        String hql="select distinct jobName from SalaryCompareView";//数据量比RecruitmentInfo少很多，减少查询时间
        Query query=session.createQuery(hql);
        List<String> jobList=query.list();
        return jobList;
    }

    @Override
    public List<RecruitmentInfo> selectInfoWithLimit(String job, int maxResult, int firstResult,String isSchool,String isFuzzyQuery) {
        Session session=this.commonDao.getSession();
        String hql="from RecruitmentInfo where jobName= :job and followsCount=:isSchool";
        if("1".equals(isFuzzyQuery)){
            hql="from RecruitmentInfo where jobName like '%"+job+"%'"+" and followsCount=:isSchool";//模糊查询
        }
        Query<RecruitmentInfo> query=session.createQuery(hql);

        if(maxResult>=0){   //实现sql中的limit
            query.setMaxResults(maxResult);
        }
        if(firstResult>=0){
            query.setFirstResult(firstResult);
        }
        if(!"1".equals(isFuzzyQuery)){
            query.setParameter("job",job);
        }
        //query.setParameter("job",job);
        query.setParameter( "isSchool",Integer.parseInt(isSchool) );

        return query.list();
    }

    @Override
    public int selectTotalCount(String job,String isSchool,String isFuzzyQuery) {
        Session session=this.commonDao.getSession();
        String hql="select CAST(count(*) as integer) from RecruitmentInfo where jobName= :job and followsCount=:isSchool";
        if("1".equals(isFuzzyQuery)){
            hql="select CAST(count(*) as integer) from RecruitmentInfo where jobName like '%"+job+"%'"+" and followsCount=:isSchool";
        }
        Query<Integer> query=session.createQuery(hql);
        if(!"1".equals(isFuzzyQuery)){
            query.setParameter("job",job);
        }
        //query.setParameter("job",job);
        query.setParameter( "isSchool",Integer.parseInt(isSchool) );
        List<Integer> list=query.list();
        //System.out.println(list.get(0));
        return list.get(0);
    }
}
