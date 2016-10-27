package com.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IMService {
@Autowired
private IMDao iMDao;
public boolean loginCheck(String account,String password){
	return iMDao.loginCheck(account, password);
}
}
