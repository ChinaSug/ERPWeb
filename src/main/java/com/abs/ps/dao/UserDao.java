package com.abs.ps.dao;

import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.domain.MenuItem;
import com.abs.ps.domain.User;
import com.abs.ps.domain.UserPriv;
import com.abs.ps.web.dto.UserDto;
import com.abs.ps.web.dto.UserPrivDto;

public interface UserDao {
	public User getUserByUserId(String id);
	public User getUserById(Long id);
	public List<User> getAllUsers();
	public IPaging findUserWithPaging(int pageNumber, int pageSize, String centerCode, String entOid);
	public User saveUser(User user);
	public void deleteUserById(String ids);
	public List<User> findUserByCenterId(String id);
	public List<MenuItem> findMenuItemList(boolean isAdmin);
	public void saveUserPriv(List<UserPriv> userPrivs) ;
	public void deletePriv(String userOid);
	public List<UserPrivDto> findUserPrivByUserId(Long userId);
	public List<UserDto> findUserByLastLoginTime(Long orgOid);
} 
