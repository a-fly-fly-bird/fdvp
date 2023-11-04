package com.tzz.repo;

import com.tzz.entity.UserBondInvestEntity;
import com.tzz.entity.UserFundInvestEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBondInvestRepo extends PagingAndSortingRepository<UserBondInvestEntity, Integer> {
    // 查询用户债券投资情况
    List<UserBondInvestEntity> findAllByUserid(Integer userId);

    @Query(value="select * from user_bond_invest where user_bond_invest.userid = ?1 and user_bond_invest.bond_code = ?2", nativeQuery=true)
    UserBondInvestEntity findByUserIDANDBondCode(int userId, String bondCode);
}
