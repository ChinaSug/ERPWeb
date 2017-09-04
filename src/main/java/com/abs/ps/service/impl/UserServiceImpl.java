package com.abs.ps.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.abs.core.paging.IPaging;
import com.abs.core.util.MailUtils;
import com.abs.ps.app.pojo.MenuJson;
import com.abs.ps.dao.UserDao;
import com.abs.ps.domain.MenuItem;
import com.abs.ps.domain.User;
import com.abs.ps.domain.UserPriv;
import com.abs.ps.service.OrgService;
import com.abs.ps.service.UserService;
import com.abs.ps.util.DateHelper;
import com.abs.ps.util.JsonUtils;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.MenuItemDto;
import com.abs.ps.web.dto.OrganizationDto;
import com.abs.ps.web.dto.UserDto;
import com.abs.ps.web.dto.UserPrivDto;
import com.abs.ps.web.dto.json.JMenuContentDto;
import com.abs.ps.web.dto.json.JMenuDto;
import com.abs.ps.web.dto.json.JMenuItemDto;

public class UserServiceImpl implements UserService {
	Logger logger=Logger.getLogger(UserServiceImpl.class.getName()); 
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	private OrgService orgService;
	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}
	
	private MailUtils mailUtils;
	public void setMailUtils(MailUtils mailUtils) {
		this.mailUtils = mailUtils;
	}
	
	public String isValidUser(UserDto userDto) {
		logger.debug("isValidUser for " + userDto.getUserId());
		// User user = userDao.getUserByUserId(userDto.getUserId().toUpperCase());
		User user = userDao.getUserByUserId(userDto.getUserId());
		
		
		if (user == null) {
			return QueueConstants.LOGIN_INVALID_USERID; 
		} else if (!user.getPassword().equals(userDto.getPassword())) {
			return QueueConstants.LOGIN_INVALID_PASSWORD;
		} else if (!"1".equals(user.getStatus())) {
			return QueueConstants.USER_UNAVAILABLE;
		}
//		System.out.println("try to check if expire");
		if (user.getIsTrial() != null && user.getIsTrial().booleanValue()) {
			OrganizationDto orgDto = orgService.getOrganizationById(user.getOrgOid());
			Date expireDate = orgDto.getExpireDate();
			if (orgDto != null && expireDate != null && DateHelper.compareDate(new Date(), expireDate, DateHelper.DATE_FORMATE) > 0 ) {
				return QueueConstants.USER_EXPIRE;
			}
		}
		user.setLastLoginTime(new Date());
		userDao.saveUser(user);
		
//		System.out.println("isValidUser completed."); 
		return QueueConstants.LOGIN_SUCCESS;
		
	}
	
	public List<UserDto> findUserByCenterId(String id) {
		List<User> users = userDao.findUserByCenterId(id);
		List<UserDto> userDtos = new ArrayList<UserDto>();
		if (users != null) {
			for (User user : users) {
				if (!user.getIsAdmin().booleanValue() && (user.getIsEndPoint() == null || user.getIsEndPoint() != null && !user.getIsEndPoint().booleanValue())) {
					UserDto userDto = new UserDto();
					userDto.setId(user.getOid().longValue());
					userDto.setUserId(user.getUserId());
					userDto.setUserName(user.getUserName());
					userDtos.add(userDto);
				}
			}
		}
		return userDtos;
	}
	
	public UserDto getUserById(Long id) {
		User user = userDao.getUserById(id);
		UserDto userDto = new UserDto();
		userDto.setId(user.getOid());
		userDto.setAdmin(user.getIsAdmin().booleanValue());
		userDto.setPassword(user.getPassword());
		userDto.setStatus(user.getStatus());
		userDto.setUserId(user.getUserId());
		userDto.setUserName(user.getUserName());
		userDto.setEmail(user.getEmail());
		userDto.setOrgOid(user.getOrgOid());
		userDto.setMobileNum(user.getMobileNum());
		userDto.setSex(user.getSex());
		userDto.setNickName(user.getNickName());
		userDto.setTrial(user.getIsTrial() == null?false:user.getIsTrial().booleanValue());
		if (user.getEntOid() != null) {
			userDto.setEntOid(user.getEntOid());
		}
		userDto.setImgUrl(user.getImgUrl());
		return userDto;
	}
	
	public UserDto getUserByUserId(String userid) {
		User user = userDao.getUserByUserId(userid.toUpperCase());
		UserDto userDto = null;
		if (user != null) {
			userDto = new UserDto();
			userDto.setId(user.getOid());
			userDto.setAdmin(user.getIsAdmin().booleanValue());
			userDto.setPassword(user.getPassword());
			userDto.setStatus(user.getStatus());
			userDto.setUserId(user.getUserId());
			userDto.setUserName(user.getUserName());
			userDto.setEmail(user.getEmail());
			userDto.setMobileNum(user.getMobileNum());
			userDto.setOrgOid(user.getOrgOid());
			userDto.setRoleType(user.getRoleType());
			userDto.setSex(user.getSex());
			userDto.setImgUrl(user.getImgUrl());
			userDto.setNickName(user.getNickName());
			if (user.getEntOid() != null) {
				userDto.setEntOid(user.getEntOid());
			}
			userDto.setTrial(user.getIsTrial() == null?false:user.getIsTrial().booleanValue());
			userDto.setOrgName(orgService.getOrgById(user.getOrgOid()).getOrgName());
		}
		return userDto;
	}
	
	public IPaging findUserWithPaging(int pageNumber, int pageSize, String centerCode, String entOid){
		return userDao.findUserWithPaging(pageNumber, pageSize, centerCode, entOid);
	}
	
	public User saveUser(UserDto userDto) {
		User user = null;
		if (userDto.getId() > 0) { 
			user = userDao.getUserById(Long.valueOf(userDto.getId()));
		} else {
			user = new User();
			user.setIsAdmin(userDto.isAdmin());
			user.setOrgOid(userDto.getOrgOid());
		}
		user.setUserId(userDto.getUserId());
		user.setUserName(userDto.getUserName());
		user.setPassword(userDto.getPassword());
		user.setStatus(userDto.getStatus());
		user.setCreateDate(new Date());
		user.setLastModifyDate(new Date());
		user.setEmail(userDto.getEmail());
		user.setMobileNum(userDto.getMobileNum());
		user.setIsTrial(userDto.isTrial());
		user.setSex(userDto.getSex());
		user.setNickName(userDto.getNickName());
		if (userDto.getEntOid() > 0) {
			user.setEntOid(userDto.getEntOid());
		}
		
		user.setImgUrl(userDto.getImgUrl());
		return userDao.saveUser(user);
	}
	
	public void deleteUserById(String[] ids) {
		userDao.deleteUserById(StringHelper.join(",", ids));
	}	
	
	public List<MenuItemDto> findMenuItemList(boolean isAdmin, String urlPrefix) {
		List<MenuItem> menuItems =userDao.findMenuItemList(isAdmin);
		List<MenuItemDto> menuItemDtos = new ArrayList<MenuItemDto>();
		
		for (MenuItem menuItem : menuItems) {
			MenuItemDto menuItemDto = new MenuItemDto();
			menuItemDto.setId(menuItem.getOid().longValue());
			if (menuItem.getIsAdmin() != null) {
				menuItemDto.setAdmin(menuItem.getIsAdmin().booleanValue());
			}
			if (menuItem.getMainMenuOid() != null) {
				menuItemDto.setMainMenuOid(menuItem.getMainMenuOid().longValue());
			}
			menuItemDto.setMenuCode(menuItem.getMenuCode());
			menuItemDto.setMenuName(menuItem.getMenuName());
			if (menuItem.getIsShow() != null) {
				menuItemDto.setShow(menuItem.getIsShow().booleanValue());
			}
			menuItemDto.setClassName(menuItem.getClassName());
			if (!StringHelper.isEmpty(menuItem.getUrl())) {
				menuItemDto.setUrl(urlPrefix + menuItem.getUrl());
			}
			if (menuItem.getIsSingle() != null) {
				menuItemDto.setSingle(menuItem.getIsSingle().booleanValue());
			}
			menuItemDto.setSeq(menuItem.getSeq());
			menuItemDtos.add(menuItemDto);
		}
		return menuItemDtos;
	}
	
	public void saveUserPriv(String[] privs, String userOid) {
		List<UserPriv> userPrivList = new ArrayList<UserPriv>();
		for (String privOid : privs) {
			if (!StringHelper.isEmpty(privOid)) {
				UserPriv userPriv = new UserPriv();
				userPriv.setMenuOid(Long.valueOf(privOid));
				userPriv.setUserOid(Long.valueOf(userOid));
				userPriv.setSeq(0);
				userPrivList.add(userPriv);
			}
		}
		if (!userPrivList.isEmpty()) {
			userDao.deletePriv(userOid);
			userDao.saveUserPriv(userPrivList);
		}
	}
	
	
	
	public List<Long> findUserPrivOidByUserId(Long userId) {
		List<UserPrivDto> userPrivDtos = userDao.findUserPrivByUserId(userId);
		List<Long> privOids = new ArrayList<Long>();
		for (UserPrivDto userPrivDto : userPrivDtos) {
			privOids.add(userPrivDto.getMenuOid());
		}
		return privOids;
	}
	
	private List<MenuItemDto> setMenuName2List(List<MenuItemDto> menuItemDtos) {
		Map<Long, String> map = new HashMap<Long, String>();
		for (MenuItemDto menuItemDto : menuItemDtos) {
			if (StringHelper.isEmpty(menuItemDto.getUrl()) && menuItemDto.getMainMenuOid() <= 0) {
				map.put(menuItemDto.getId(), menuItemDto.getSeq() + menuItemDto.getMenuName());
//				map.put(Long.parseLong(menuItemDto.getId() + "101") , String.valueOf(menuItemDto.getSeq()));
			}
		}
		for (MenuItemDto menuItemDto : menuItemDtos) {
			if (!StringHelper.isEmpty(menuItemDto.getUrl())) {
				menuItemDto.setMainMenuName(map.get(menuItemDto.getMainMenuOid()));
				//menuItemDto.setSeq(Integer.valueOf(map.get(Long.parseLong(menuItemDto.getMainMenuOid() + "101"))));
			}
		}
		return menuItemDtos;
	}
	
	public Map<String, List<MenuItemDto>> assembleUserMenu(Long userId, String urlPrefix) {
		List<MenuItemDto> menuItemDtos = findMenuItemList(true,urlPrefix);
		menuItemDtos = setMenuName2List(menuItemDtos);
		List<Long> userPrivOids = findUserPrivOidByUserId(userId);
		Map<String, List<MenuItemDto>> map = new HashMap<String, List<MenuItemDto>>();
		
		for (MenuItemDto menuItemDto : menuItemDtos) {
			// ignore this case
			if (StringHelper.isEmpty(menuItemDto.getUrl()) && menuItemDto.getMainMenuOid() > 0) {
				continue;
			}
			if (userPrivOids.contains(menuItemDto.getId())) {
				List<MenuItemDto> list = map.get(menuItemDto.getMainMenuName());
				if (list == null) {
					list = new ArrayList<MenuItemDto>();
					map.put(menuItemDto.getMainMenuName(), list);
				}
				
				list.add(menuItemDto);
			}
		}
		return map;
	}
	
	public String assembleUserMenu2Json(Long userId, String urlPrefix) {
		List<MenuItemDto> menuItemDtos = findMenuItemList(true, urlPrefix);
		menuItemDtos = setMenuName2List(menuItemDtos);
		List<Long> userPrivOids = findUserPrivOidByUserId(userId);
		Map<String, List<MenuItemDto>> map = new HashMap<String, List<MenuItemDto>>();
		
		for (MenuItemDto menuItemDto : menuItemDtos) {
			// ignore this case
			if (StringHelper.isEmpty(menuItemDto.getUrl()) && menuItemDto.getMainMenuOid() > 0) {
				continue;
			}
			if (userPrivOids.contains(menuItemDto.getId())) {
				List<MenuItemDto> list = map.get(menuItemDto.getMainMenuName());
				if (list == null) {
					list = new ArrayList<MenuItemDto>();
					map.put(menuItemDto.getMainMenuName(), list);
				}
				
				list.add(menuItemDto);
			}
		}
		String jsonStr = "";
		if (map != null) {
			JMenuDto jMenu = new JMenuDto();
			jMenu.setId("menu");
			
			Iterator<String> keyIt = map.keySet().iterator();
			List<String> keyList = new ArrayList<String>();
			while (keyIt.hasNext()) {
				String key = keyIt.next();
				if (!StringHelper.isEmpty(key)) {
					keyList.add(key);
				}
			}
			Collections.sort(keyList);
			Iterator<String> it = keyList.iterator();

			List<JMenuContentDto> menuContents = new ArrayList<JMenuContentDto>();
			while(it.hasNext()) {
					String mainMenuName = it.next();
				List<MenuItemDto> menus = map.get(mainMenuName);
				
				mainMenuName = mainMenuName.substring(1, mainMenuName.length());
				
				JMenuContentDto menuContent = new JMenuContentDto();
				menuContent.setText(mainMenuName);
				menuContent.setCollapsed(true);
				menuContents.add(menuContent);
				List<JMenuItemDto> menuItems = new ArrayList<JMenuItemDto>();
				for (MenuItemDto menuItemDto : menus) {
					if (menuItemDto.getMainMenuOid() < 1 || (menuItemDto.getUrl() == null || menuItemDto.getUrl().equals(""))) {
						continue;
					}
						
					JMenuItemDto item = new JMenuItemDto();
					item.setId(menuItemDto.getMenuCode());
					item.setText(menuItemDto.getMenuName());
					item.setHref(menuItemDto.getUrl());
					menuItems.add(item);
				}
				
				menuContent.setItems(menuItems);
				
			}
			jMenu.setMenu(menuContents);
			jsonStr = JsonUtils.toJSONString(jMenu);
		}
		
		return jsonStr;
	}
	
	
	public List<MenuJson> assembleUserMenu4Json(Long userId, String urlPrefix) {
		List<MenuJson> menuJsonList = new ArrayList<MenuJson>();
		
		List<MenuItemDto> menuItemDtos = findMenuItemList(true, urlPrefix);
		menuItemDtos = setMenuName2List(menuItemDtos);
		List<Long> userPrivOids = findUserPrivOidByUserId(userId);
		Map<String, List<MenuJson>> map = new HashMap<String, List<MenuJson>>();
		
		for (MenuItemDto menuItemDto : menuItemDtos) {
			// ignore this case
			if (StringHelper.isEmpty(menuItemDto.getUrl()) && menuItemDto.getMainMenuOid() > 0) {
				continue;
			}
			if (userPrivOids.contains(menuItemDto.getId())) {
				
				
				List<MenuJson> list = map.get(menuItemDto.getMainMenuName());
				if (list == null) {
					list = new ArrayList<MenuJson>();
					map.put(menuItemDto.getMainMenuName(), list);
				}
				MenuJson subMenuJson = new MenuJson();
				subMenuJson.setMenuCode(menuItemDto.getMenuCode());
				subMenuJson.setMenuName(menuItemDto.getMenuName());
				subMenuJson.setMainMenu(false);
				subMenuJson.setUrl(menuItemDto.getUrl());
				
				list.add(subMenuJson);
			}
		}
		
		Iterator<String> menuKeys = map.keySet().iterator();
		while (menuKeys.hasNext()) {
			String mainMenuName = menuKeys.next();
			List<MenuJson> subMenuJsonList = map.get(mainMenuName);
			
			MenuJson menuJson = new MenuJson();
			menuJson.setMainMenu(true);
			menuJson.setMenuName(mainMenuName);
			menuJson.setSubMenus(subMenuJsonList);
			menuJsonList.add(menuJson);
		}
		
		return menuJsonList;
	}
	
	
	public boolean isUserDeletable(Long userOid) {
		return true;
	}
	
	public void changePassword(String userId, String newPwd) {
		User user = userDao.getUserByUserId(userId.toUpperCase());
		if (user != null) {
			user.setPassword(newPwd);
			userDao.saveUser(user);
		}
	}
	
	public void changeNickName(String userId, String newNickName) {
		User user = userDao.getUserByUserId(userId.toUpperCase());
		if (user != null) {
			user.setNickName(newNickName);
			userDao.saveUser(user);
		}
	}
	
	public void changeMobileNum(String userId, String newValue) {
		User user = userDao.getUserByUserId(userId.toUpperCase());
		if (user != null) {
			user.setMobileNum(newValue);
			userDao.saveUser(user);
		}
	}
	
	public void changeMailAddr(String userId, String newValue) {
		User user = userDao.getUserByUserId(userId.toUpperCase());
		if (user != null) {
			user.setEmail(newValue);
			userDao.saveUser(user);
		}
	}
	
	
	private void saveUserDefaultPrive(long userOid) {
		String[] privs = {"5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
		List<UserPriv> userPrivList = new ArrayList<UserPriv>();
		for (String privOid : privs) {
			if (!StringHelper.isEmpty(privOid)) {
				UserPriv userPriv = new UserPriv();
				userPriv.setMenuOid(Long.valueOf(privOid));
				userPriv.setUserOid(Long.valueOf(userOid));
				userPriv.setSeq(0);
				userPrivList.add(userPriv);
			}
		}
		userDao.saveUserPriv(userPrivList);
	}

	@Override
	public List<UserDto> findUserByLastLoginTime(Long orgOid) {
		return userDao.findUserByLastLoginTime(orgOid);
	}
	
	
}
