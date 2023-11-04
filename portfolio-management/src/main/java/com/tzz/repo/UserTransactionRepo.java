package com.tzz.repo;


import com.tzz.entity.UserEntity;
import com.tzz.entity.UserStockInvestEntity;
import com.tzz.entity.UserTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTransactionRepo extends JpaRepository<UserTransactionEntity, Integer> {
//
//    @Query(value="select * from user_transaction where user_transaction.user_id = ?1 and user_transaction.product_code = ?2", nativeQuery=true)
//    UserTransactionEntity findByUserIDANDproductCode(int userId,String productCode);
      List<UserTransactionEntity> findAllByUserId(int userId);

}
