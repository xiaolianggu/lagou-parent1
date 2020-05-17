package com.lagou.edu.service;


public interface EmailService {
	public Boolean sendCodeToEmail(String email , String code);
}
