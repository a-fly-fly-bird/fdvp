package com.tzz.server;

import com.tzz.entity.BondsEntity;
import com.tzz.entity.FundsEntity;
import com.tzz.repo.BondsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BondsService {
    @Autowired
    private BondsRepo bondsRepo;

    // 根据基金代码查询基金的历史数据
    public List<BondsEntity> getBondsDetailsByBondsCode(String bondCode) {
        if(!bondsRepo.findALLByBondCodeOrderByDate(bondCode).isEmpty()){
            System.out.println("查询成功");
            List<BondsEntity> bondsList = bondsRepo.findALLByBondCodeOrderByDate(bondCode);
            bondsList = bondsList.stream()
                    .filter(s->s.getClose()!=null)
                    .collect(Collectors.toList());
            return bondsList;
        }
        System.out.println("查询失败");
        return null;
    }

    // 查询当日所有基金数据
    public Page<BondsEntity> getBondsPage(Integer pageNumber, Integer pageSize, Date date) {
        Pageable pageRequest = BuildPageRequest(pageNumber, pageSize);
        // 从数据库查询所数据
        Page<BondsEntity> bondsPage = bondsRepo.findALLByDateAndCloseNotNull(pageRequest, date);


        return bondsPage;
    }

    public static Pageable BuildPageRequest(Integer pageNumber, Integer pageSize) {
        return PageRequest.of(pageNumber-1, pageSize);
    }

    // 根据日期和基金代码查询基金信息
    public List<BondsEntity>  getBondsEntitiesByBondCodeAndDate(Date date,  List<String> bondCodeList) {

        return bondsRepo.getBondsEntitiesByFundCodeAndDate(date, bondCodeList)
                .stream()
                .filter(s->s.getClose()!=null)
                .collect(Collectors.toList());
    }

    // 根据日期区间段和债券代码查询债券信息
    public List<BondsEntity> getBondInfoByPeriod(String bondCode, Date date1, Date date2){
        return bondsRepo.getBondInfoByPeriod(bondCode, date1, date2);
    }

}
