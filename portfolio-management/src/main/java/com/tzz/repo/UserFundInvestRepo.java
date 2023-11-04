package com.tzz.repo;

import com.tzz.entity.UserFundInvestEntity;
import com.tzz.entity.UserStockInvestEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFundInvestRepo extends PagingAndSortingRepository<UserFundInvestEntity, Integer> {

    // 查询用户基金投资情况
    List<UserFundInvestEntity> findAllByUserid(Integer userId);

    @Query(value="select * from user_fund_invest where user_fund_invest.userid = ?1 and user_fund_invest.fund_code = ?2", nativeQuery=true)
    UserFundInvestEntity findByUserIDANDFundCode(int userId, String fundCode);
}
