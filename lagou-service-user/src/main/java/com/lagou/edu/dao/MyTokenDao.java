package com.lagou.edu.dao;

import com.lagou.edu.pojo.MyToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyTokenDao extends JpaRepository<MyToken,Long> {
}
