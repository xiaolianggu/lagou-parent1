package com.lagou.edu.dao;

import com.lagou.edu.pojo.MyAuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyAuthCodeDao extends JpaRepository<MyAuthCode,Long> {
}
