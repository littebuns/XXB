package com.example.demo.dao;

import com.example.demo.Dao.OpenstackDao;
import com.example.demo.Dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    OpenstackDao openstackDao;

    @Test
    public void test(){
        System.out.println(userDao.listAll());
    }

    @Test
    public void openstackTest(){
        openstackDao.save();
    }


}
