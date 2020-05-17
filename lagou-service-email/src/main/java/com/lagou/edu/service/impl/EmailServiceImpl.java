package com.lagou.edu.service.impl;

import org.apache.dubbo.config.annotation.Service;

import com.lagou.edu.service.EmailService;
@Service
public class EmailServiceImpl implements EmailService{

	@Override
	public Boolean sendCodeToEmail(String email, String code) {
		System.out.println("sendCodeToEmail success");
		return true;
	}

}
