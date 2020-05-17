package com.lagou.edu.service.impl;

import com.lagou.edu.dao.MyAuthCodeDao;
import com.lagou.edu.dao.MyTokenDao;
import com.lagou.edu.pojo.MyAuthCode;
import com.lagou.edu.pojo.MyToken;
import com.lagou.edu.service.EmailService;
import com.lagou.edu.service.UserService;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MyTokenDao myTokenDao;
    @Autowired
    private MyAuthCodeDao myAuthCodeDao;
    @Reference
    private EmailService  emailService;

    @Override
    public Boolean sendCodeToEmailAndSave(String email, String code) {
        Boolean isSuccess = emailService.sendCodeToEmail(email, code);
        if (!isSuccess) {
            return false;
        }
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, 10);
        MyAuthCode myAuthCode = new MyAuthCode();
        myAuthCode.setEmail(email);
        myAuthCode.setCode(code);
        myAuthCode.setCreatetime(now);
        myAuthCode.setExpiretime(calendar.getTime());
        myAuthCodeDao.save(myAuthCode);
        return true;
    }

    @Override
    public Boolean register(String email, String pwd, String code) {
        MyAuthCode authCodeCondition = new MyAuthCode();
        authCodeCondition.setCode(code);
        authCodeCondition.setEmail(email);
        Example<MyAuthCode> example = Example.of(authCodeCondition);
        List<MyAuthCode> authCodeList = myAuthCodeDao.findAll(example, new Sort(Sort.Direction.DESC, "id"));
        if (authCodeList != null && !authCodeList.isEmpty()) {
            MyAuthCode authCode = authCodeList.get(0);
            Date expireTime = authCode.getExpiretime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(expireTime);
            calendar.add(Calendar.MINUTE, 10);
            if (System.currentTimeMillis() > calendar.getTime().getTime()) {
                System.out.print("token过期了");
                return false;
            }
            String token = email + ":" + pwd;
            MyToken myToken = new MyToken();
            myToken.setEmail(email);
            myToken.setToken(DigestUtils.md5DigestAsHex(token.getBytes()));
            myTokenDao.save(myToken);
            return true;
        }
        return false;
    }

    @Override
    public Boolean isRegistered(String email) {
        MyToken condition = new MyToken();
        condition.setEmail(email);
        Example<MyToken> example = Example.of(condition);
        Optional<MyToken> one = myTokenDao.findOne(example);
        if (one.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public String getEmailByToken(String token) {
        MyToken condition = new MyToken();
        condition.setToken(token);
        Example<MyToken> example = Example.of(condition);
        Optional<MyToken> one = myTokenDao.findOne(example);

        if (one.isPresent()) {
            return one.get().getEmail();
        }
        return "";
    }
}
