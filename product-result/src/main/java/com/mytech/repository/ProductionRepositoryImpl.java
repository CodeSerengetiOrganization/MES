package com.mytech.repository;

import org.springframework.web.context.annotation.RequestScope;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-06
 * @description :
 */
@RequestScope
public class ProductionRepositoryImpl {
    @PersistenceContext
    private EntityManager em;
    //1. Create



    //2. Retrieve


   //5. other methods



/*    //6. other method for test
    public void saveForTest(String tabelName){
        System.out.println(Thread.currentThread().getName()+"--Reposotiry--" +
                "tableName:"+tabelName);
    }//saveForTest*/

    //7 to delete
/*    @Transactional
    public ProductionResult save(String tableName,ProductionResult result)  {
        String sql="INSERT INTO "+tableName+" (f_part_number,f_station_name,f_result,f_create_time,f_modify_time,f_comment) " +
                "VALUES(?2,?3,?4,?5,?6,?7)";
        Query query = em.createNativeQuery(sql);
//        query.setParameter(1,tableName);
        query.setParameter(2,result.getBarcode());
        query.setParameter(3,result.getStationName());
        query.setParameter(4,result.getResult());
        query.setParameter(5,result.getCreateTime());
        query.setParameter(6,result.getModifyTime());
        query.setParameter(7,result.getComment());
        int updateResult = query.executeUpdate();
//    System.out.println("updateResult:"+updateResult);
//    if(updateResult!=1) throw new RepositorySaveFailException("save operation failed. Expected executeUpdate return value:[{}].Actual return value:[{}]",1,updateResult);
        if(updateResult!=1) throw new RuntimeException(String.format("save operation failed. Expected executeUpdate return value:[{}].Actual return value:[{}]",1,updateResult));

        return result;
    }//save*/

/*    List<ProductionResult> findAll(String tableName){
        Preconditions.checkArgument(Strings.isNullOrEmpty(tableName),"table name is null or empty");
        ArrayList<ProductionResult> results = Lists.newArrayList();
        String sql="select * from "+tableName;
        Query query = em.createNativeQuery(sql);
        return query.getResultList();
    }//findAll*/

}//ProductionRepositoryImpl
