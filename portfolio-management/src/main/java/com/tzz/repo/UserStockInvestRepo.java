package com.tzz.repo;

import com.tzz.entity.UserStockInvestEntity;
import com.tzz.entity.UserTransactionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStockInvestRepo extends PagingAndSortingRepository<UserStockInvestEntity, Integer> {

    // 查询用户股票投资情况
    List<UserStockInvestEntity> findAllByUserid(Integer userId);

    @Query(value="select * from user_stock_invest where user_stock_invest.userid = ?1 and user_stock_invest.stock_code = ?2", nativeQuery=true)
    UserStockInvestEntity findByUserIDANDStockCode(int userId, String stockCode);

}
