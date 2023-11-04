package com.tzz.repo;

import com.tzz.entity.BondsEntity;
import com.tzz.entity.FundsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BondsRepo extends PagingAndSortingRepository<BondsEntity, Integer> {

    // 查询当日Bonds表的所有数据，传入Pageable分页参数
    Page<BondsEntity> findALLByDateAndCloseNotNull(Pageable pageable, Date date);

    // 根据债券代码查询债券的历史纪录
    List<BondsEntity> findALLByBondCodeOrderByDate(String bondCode);

    // 根据日期和债券代码查询债券信息
    @Query(value = "from BondsEntity  where  date=?1 and bondCode in ?2")
    List<BondsEntity> getBondsEntitiesByFundCodeAndDate(Date date, List<String> bondCodeList);

    @Query(value="select * from bonds where bonds.bond_code = ?1 and bonds.date = ?2", nativeQuery=true)
    BondsEntity findByBondCodeAndDate(String bondCode, String date);

    // 根据日期区间段和债券代码查询债券信息
    @Query(value = "select * from bonds where bonds.bond_code=?1 and bonds.date between ?2 and ?3", nativeQuery = true)
    List<BondsEntity> getBondInfoByPeriod(String bondCode, Date date1, Date date2);
}
