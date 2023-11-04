package com.tzz.server;

import com.tzz.entity.UserEntity;
import com.tzz.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;


    // 添加用户
    public UserEntity addUser(UserEntity u) {
        return userRepo.save(u);
    }

    public UserEntity login(String name, String password) {

        UserEntity userresult = userRepo.findByUsername(name);

        System.out.println(userresult);

        if (userresult != null && userresult.getPassWord().equals(password) && userresult.getName().equals(name))
            return userresult;
        else return null;

    }

    public UserEntity updateUser(int id, UserEntity newUserEntity) {


        if (userRepo.findById(id).isPresent()) {
            UserEntity olduserEntity = userRepo.findById(id).get();
            olduserEntity.setName(newUserEntity.getName());
            olduserEntity.setPassWord(newUserEntity.getPassWord());
            olduserEntity.setBalance(newUserEntity.getBalance());
            return userRepo.save(olduserEntity);
        } else {
            return null;
        }

    }

    // 根据用户id查询用户
    public UserEntity getUserById(int id) {
        if (userRepo.findById(id).isPresent()) {
            return userRepo.findById(id).get();
        } else {
            return null;
        }

    }

}
