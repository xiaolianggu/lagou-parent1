package com.lagou.edu.service;

public interface UserService {
    public Boolean sendCodeToEmailAndSave(String email,String code);

    public Boolean register(String email,String pwd,String code);

    public Boolean isRegistered(String email);

    public String getEmailByToken(String token);
}
