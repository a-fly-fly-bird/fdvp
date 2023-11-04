package com.tzz.server;

import com.tzz.entity.*;
import com.tzz.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserTransactionService {

    @Autowired
    private UserTransactionRepo userTransactionRepo;
    @Autowired
    private StocksRepo stocksRepo;
    @Autowired
    private FundsRepo fundsRepo;
    @Autowired
    private BondsRepo bondsRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserStockInvestRepo userStockInvestRepo;
    @Autowired
    private UserFundInvestRepo userFundInvestRepo;
    @Autowired
    private UserBondInvestRepo userBondInvestRepo;

    public UserTransactionEntity addTransaction(UserTransactionEntity usertransaction) {

        String userTransactionProductCodeCur = usertransaction.getProductCode();
        Date usertransactionDate = usertransaction.getTransactionDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String transformDate = simpleDateFormat.format(usertransactionDate);
        double transactionPriceCur = 0;
        ProductType productType = usertransaction.getProductType();
        System.out.println(productType);
        int userId = usertransaction.getUserId();
        UserEntity userEntity = userRepo.findById(userId).get();
        double balanceCur = userEntity.getBalance();
        switch (productType) {
            case STOCK:
                StocksEntity stocksEntity = stocksRepo.findByStockCodeAndDate(userTransactionProductCodeCur, transformDate);
                transactionPriceCur = stocksEntity.getAdjClose() * usertransaction.getTransactionAmount();
                balanceCur = userEntity.getBalance() - transactionPriceCur;
                if(balanceCur<0) {
                    System.out.println("余额不足");
                    return null;
                }
                System.out.println("买股票啦");
                UserStockInvestEntity userStockInvestEntity = userStockInvestRepo.findByUserIDANDStockCode(usertransaction.getUserId(), usertransaction.getProductCode());
                if (userStockInvestEntity == null) {
                    UserStockInvestEntity userStockInvestEntityTmp = new UserStockInvestEntity();
                    userStockInvestEntityTmp.setUserid(userId);
                    userStockInvestEntityTmp.setStockCode(usertransaction.getProductCode());
                    userStockInvestEntityTmp.setStockName(usertransaction.getProductCode());
                    userStockInvestEntityTmp.setBuyPrice(stocksEntity.getAdjClose());
                    userStockInvestEntityTmp.setBuyDate(usertransaction.getTransactionDate());
                    userStockInvestEntityTmp.setPositionNumber(usertransaction.getTransactionAmount());
                    userStockInvestRepo.save(userStockInvestEntityTmp);
                } else {
                    System.out.println("更新现有股票操作");
                    userStockInvestEntity.setBuyPrice(stocksEntity.getAdjClose());
                    userStockInvestEntity.setBuyDate(usertransaction.getTransactionDate());
                    int oldTransactionAmount = userStockInvestEntity.getPositionNumber();
                    userStockInvestEntity.setPositionNumber(usertransaction.getTransactionAmount() + oldTransactionAmount);
                    userStockInvestRepo.save(userStockInvestEntity);
                }
                break;

            case FUND:
                FundsEntity fundsEntity = fundsRepo.findByFundCodeAndDate(userTransactionProductCodeCur, transformDate);
                transactionPriceCur = fundsEntity.getAdjClose() * usertransaction.getTransactionAmount();
                balanceCur = userEntity.getBalance() - transactionPriceCur;
                if(balanceCur<0) {
                    System.out.println("余额不足");
                    return null;
                }

                System.out.println("买基金啦");

                UserFundInvestEntity userFundInvestEntity = userFundInvestRepo.findByUserIDANDFundCode(usertransaction.getUserId(), usertransaction.getProductCode());
                if (userFundInvestEntity == null) {
                    UserFundInvestEntity userFundInvestEntityTmp = new UserFundInvestEntity();
                    userFundInvestEntityTmp.setUserid(userId);
                    userFundInvestEntityTmp.setFundCode(usertransaction.getProductCode());
                    userFundInvestEntityTmp.setFundName(usertransaction.getProductCode());
                    userFundInvestEntityTmp.setBuyPrice(fundsEntity.getAdjClose());
                    userFundInvestEntityTmp.setBuyDate(usertransaction.getTransactionDate());
                    userFundInvestEntityTmp.setPositionNumber(usertransaction.getTransactionAmount());
                    userFundInvestRepo.save(userFundInvestEntityTmp);
                } else {

                    System.out.println("更新现有基金操作");
                    userFundInvestEntity.setBuyPrice(fundsEntity.getAdjClose());
                    userFundInvestEntity.setBuyDate(usertransaction.getTransactionDate());
                    int oldTransactionAmount = userFundInvestEntity.getPositionNumber();
                    userFundInvestEntity.setPositionNumber(usertransaction.getTransactionAmount() + oldTransactionAmount);
                    userFundInvestRepo.save(userFundInvestEntity);
                }
                break;

            case BOND:

                BondsEntity bondsEntity = bondsRepo.findByBondCodeAndDate(userTransactionProductCodeCur, transformDate);
                transactionPriceCur = bondsEntity.getAdjClose() * usertransaction.getTransactionAmount();
                balanceCur = userEntity.getBalance() - transactionPriceCur;
                if(balanceCur<0) {
                    System.out.println("余额不足");
                    return null;
                }

                System.out.println("买债券啦");

                UserBondInvestEntity userBondInvestEntity = userBondInvestRepo.findByUserIDANDBondCode(usertransaction.getUserId(), usertransaction.getProductCode());
                if (userBondInvestEntity == null) {
                    UserBondInvestEntity userBondInvestEntityTmp = new UserBondInvestEntity();
                    userBondInvestEntityTmp.setUserid(userId);
                    userBondInvestEntityTmp.setBondCode(usertransaction.getProductCode());
                    userBondInvestEntityTmp.setBondName(usertransaction.getProductCode());
                    userBondInvestEntityTmp.setBuyPrice(bondsEntity.getAdjClose());
                    userBondInvestEntityTmp.setBuyDate(usertransaction.getTransactionDate());
                    userBondInvestEntityTmp.setPositionNumber(usertransaction.getTransactionAmount());
                    userBondInvestRepo.save(userBondInvestEntityTmp);
                } else {

                    System.out.println("更新现有债券操作");
                    userBondInvestEntity.setBuyPrice(bondsEntity.getAdjClose());
                    userBondInvestEntity.setBuyDate(usertransaction.getTransactionDate());
                    int oldTransactionAmount = userBondInvestEntity.getPositionNumber();
                    userBondInvestEntity.setPositionNumber(usertransaction.getTransactionAmount() + oldTransactionAmount);
                    userBondInvestRepo.save(userBondInvestEntity);
                }
                break;

        }


        userEntity.setBalance(balanceCur);
        userRepo.save(userEntity);
        usertransaction.setBalance(balanceCur);
        usertransaction.setTransactionPrice(transactionPriceCur);
        System.out.println("金融买入交易进行中...");
        System.out.println(usertransaction.getProductCode());
        return userTransactionRepo.save(usertransaction);
    }


    public UserTransactionEntity sellTransaction(UserTransactionEntity usertransaction) {

        String userTransactionProductCodeCur = usertransaction.getProductCode();
        Date usertransactionDate = usertransaction.getTransactionDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String transformDate = simpleDateFormat.format(usertransactionDate);
        double transactionPriceCur = 0;
        ProductType productType = usertransaction.getProductType();
        System.out.println(productType);
        int userId = usertransaction.getUserId();
        switch (productType) {
            case STOCK:
                StocksEntity stocksEntity = stocksRepo.findByStockCodeAndDate(userTransactionProductCodeCur, transformDate);
                transactionPriceCur = stocksEntity.getAdjClose() * usertransaction.getTransactionAmount();
                UserStockInvestEntity userStockInvestEntity = userStockInvestRepo.findByUserIDANDStockCode(usertransaction.getUserId(), usertransaction.getProductCode());

                if(userStockInvestEntity==null||userStockInvestEntity.getPositionNumber()==0||userStockInvestEntity.getPositionNumber() < usertransaction.getTransactionAmount())
                {
                    System.out.println("股票余量不足");
                    return null;
                }
                System.out.println("卖股票啦");

                userStockInvestEntity.setUserid(userId);
                userStockInvestEntity.setStockCode(usertransaction.getProductCode());
                userStockInvestEntity.setStockName(usertransaction.getProductCode());
                userStockInvestEntity.setPositionNumber(userStockInvestEntity.getPositionNumber() - usertransaction.getTransactionAmount());
                userStockInvestRepo.save(userStockInvestEntity);

                break;

            case FUND:
                FundsEntity fundsEntity = fundsRepo.findByFundCodeAndDate(userTransactionProductCodeCur, transformDate);
                transactionPriceCur = fundsEntity.getAdjClose() * usertransaction.getTransactionAmount();
                UserFundInvestEntity userFundInvestEntity = userFundInvestRepo.findByUserIDANDFundCode(usertransaction.getUserId(), usertransaction.getProductCode());

                if(userFundInvestEntity==null||userFundInvestEntity.getPositionNumber()==0||userFundInvestEntity.getPositionNumber() < usertransaction.getTransactionAmount())
                {
                    System.out.println("基金余量不足");
                    return null;
                }
                System.out.println("卖基金啦");

                userFundInvestEntity.setUserid(userId);
                userFundInvestEntity.setFundCode(usertransaction.getProductCode());
                userFundInvestEntity.setFundName(usertransaction.getProductCode());
                userFundInvestEntity.setPositionNumber(userFundInvestEntity.getPositionNumber() - usertransaction.getTransactionAmount());
                userFundInvestRepo.save(userFundInvestEntity);
                break;

            case BOND:
                BondsEntity bondsEntity = bondsRepo.findByBondCodeAndDate(userTransactionProductCodeCur, transformDate);
                transactionPriceCur = bondsEntity.getAdjClose() * usertransaction.getTransactionAmount();
                UserBondInvestEntity userBondInvestEntity = userBondInvestRepo.findByUserIDANDBondCode(usertransaction.getUserId(), usertransaction.getProductCode());

                if(userBondInvestEntity==null||userBondInvestEntity.getPositionNumber()==0||userBondInvestEntity.getPositionNumber() < usertransaction.getTransactionAmount())
                {
                    System.out.println("债券余量不足");
                    return null;
                }
                System.out.println("卖债券啦");
                userBondInvestEntity.setUserid(userId);
                userBondInvestEntity.setBondCode(usertransaction.getProductCode());
                userBondInvestEntity.setBondName(usertransaction.getProductCode());
                userBondInvestEntity.setPositionNumber(userBondInvestEntity.getPositionNumber() - usertransaction.getTransactionAmount());
                userBondInvestRepo.save(userBondInvestEntity);
                break;

        }

        UserEntity userEntity = userRepo.findById(userId).get();
        double balanceCur = userEntity.getBalance() + transactionPriceCur;
        userEntity.setBalance(balanceCur);
        userRepo.save(userEntity);
        usertransaction.setBalance(balanceCur);
        usertransaction.setTransactionPrice(transactionPriceCur);
        System.out.println("金融卖出交易进行中...");
        System.out.println(usertransaction.getProductCode());
        return userTransactionRepo.save(usertransaction);
    }

    public List<UserTransactionEntity> getUserTransactionDetail(int userId){
        System.out.println("正在查询用户交易记录");
        if(!userTransactionRepo.findAllByUserId(userId).isEmpty()){
            return userTransactionRepo.findAllByUserId(userId);
        }
        else return null;
    }
}
