package com.abs.ps.service;

import java.util.List;
import java.util.Map;

import com.abs.core.paging.IPaging;
import com.abs.ps.app.pojo.MenuJson;
import com.abs.ps.domain.User;
import com.abs.ps.web.dto.MenuItemDto;
import com.abs.ps.web.dto.UserDto;

public interface UserService {
	public String isValidUser(UserDto userDto);
	public IPaging findUserWithPaging(int pageNumber, int pageSize, String centerCode, String entOid) ;
	public User saveUser(UserDto userDto);
	public UserDto getUserById(Long id);
	public void deleteUserById(String[] ids);
	public List<UserDto> findUserByCenterId(String id);
	public UserDto getUserByUserId(String userid);
	public List<MenuItemDto> findMenuItemList(boolean isAdmin, String urlPrefix);
	public void saveUserPriv(String[] privs, String userOid) ;
	public List<Long> findUserPrivOidByUserId(Long userId);
	public Map<String, List<MenuItemDto>> assembleUserMenu(Long userId, String urlPrefix) ;
	public boolean isUserDeletable(Long userOid) ;
	public void changePassword(String userId, String newPwd) ;
	public List<MenuJson> assembleUserMenu4Json(Long userId, String urlPrefix);
	public String assembleUserMenu2Json(Long userId, String urlPrefix);
	public void changeNickName(String userId, String newNickName);
	public void changeMobileNum(String userId, String newValue);
	public void changeMailAddr(String userId, String newValue);
	public List<UserDto> findUserByLastLoginTime(Long orgOid);
}
