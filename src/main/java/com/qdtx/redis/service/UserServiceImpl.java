package com.qdtx.redis.service;

import java.util.List;
 
import javax.annotation.Resource;
 
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qdtx.redis.bean.User;
import com.qdtx.redis.dao.IUserDao;
 
/**
 * UserService实现类
 * @author 
 *
 */
@Service
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao mUserDao;
	
	@Override
	@CacheEvict(value = { "saveUser"}, allEntries = true)
	public void saveUser(String name, String sex, Integer age) {
		User user = new User(name, sex, age);
		mUserDao.saveUser(user);
	}
 
	@Override
	@Cacheable("getAllUser")
	public List<User> getAllUser() {
		return mUserDao.getAllUser();
	}
 
	@Override
	@Cacheable("getUserById")
	public User getUserById(Integer id) {
		return mUserDao.getById(id);
	}
 
	@Override
	@CacheEvict(value = { "getAllUser", "getUserById" }, allEntries = true)
	public void renameUser(String name, Integer id) {
		mUserDao.rename(new User(id, name));
	}
 
	@Override
	@CacheEvict(value = { "getAllUser", "getUserById" }, allEntries = true)
	public void deleteUserById(Integer id) {
		mUserDao.deleteById(id);
	}
 
}
