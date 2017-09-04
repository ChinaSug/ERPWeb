package com.abs.ps.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.core.paging.IPaging;
import com.abs.ps.dao.UserDao;
import com.abs.ps.domain.MenuItem;
import com.abs.ps.domain.User;
import com.abs.ps.domain.UserPriv;
import com.abs.ps.util.DateHelper;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.UserDto;
import com.abs.ps.web.dto.UserPrivDto;

public class UserDaoImpl extends AbsDaoSupport implements UserDao {
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 
	
	public User getUserByUserId(String id) {
		List<User> userList = this.getHibernateTemplate().find("from User u where u.userId=?",id);
		if (userList != null && userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}
	
	public User getUserById(Long id) {
		List<User> userList = this.getHibernateTemplate().find("from User u where u.oid=?",id);
		if (userList != null && userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}
	
	public List<User> findUserByCenterId(String id) {
		return this.getHibernateTemplate().find("from User u where u.orgOid=? and u.status=? ",id,"1");
	}
	
	public List<User> getAllUsers() {
		return this.getHibernateTemplate().find("from User");
	}
	
	public IPaging findUserWithPaging(int pageNumber, int pageSize, String centerCode, String entOid) {
		Object[] values = null;
		String hql = "from User";
		if (!StringHelper.isEmpty(centerCode) && StringHelper.isEmpty(entOid)) {
			hql = "from User u where u.orgOid=? ";
			 values = new Object[1];
			 values[0] = Long.parseLong(centerCode);
		} else if (!StringHelper.isEmpty(centerCode) && !StringHelper.isEmpty(entOid)) {
			hql = "from User u where u.orgOid=? and u.entOid=?";
			 values = new Object[2];
			 values[0] = Long.parseLong(centerCode);
			 values[1] = Long.parseLong(entOid);
		}
		
		
		return this.queryByPage(pageNumber, pageSize, values, hql);		
	}
	
	public User saveUser(User user) {
		this.getHibernateTemplate().saveOrUpdate(user);
		this.getHibernateTemplate().flush();
		return user;
	}
	
	public void deleteUserById(String ids) {
		String sql = "DELETE FROM T_USER WHERE OID IN (" + ids + ")";
		try {
			this.deleteBySql(sql);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	
	public List<MenuItem> findMenuItemList(boolean isAdmin) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.* FROM T_MENU_ITEM T WHERE T.MENU_CODE IS NOT NULL AND T.IS_SHOW != 'N' ");
		if (!isAdmin) {
			sql.append(" AND T.IS_ADMIN <> 'Y' ");
		}
		sql.append(" ORDER BY T.SEQ");
		
		return (List<MenuItem>) jdbcTemplate.query(sql.toString(), new Object[] { }, new BeanPropertyRowMapper(MenuItem.class));  
		
	}
	
	public void deletePriv(String userOid) {
		String sql = "DELETE FROM T_USER_PRIV WHERE USER_OID=?";
		jdbcTemplate.update(sql, Long.valueOf(userOid));
	}
	
	public void saveUserPriv(List<UserPriv> userPrivs) {
		this.getHibernateTemplate().saveOrUpdateAll(userPrivs);
	}
	
	public List<UserPrivDto> findUserPrivByUserId(Long userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.MENU_OID FROM T_USER_PRIV T WHERE T.USER_OID=? ORDER BY T.SEQ");
		Object params[] = {userId};
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), params);
		List<UserPrivDto> userPrivDtos = new ArrayList<UserPrivDto>();
		while(rs.next()) {
			UserPrivDto userPrivDto = new UserPrivDto();
			Long menuOid = rs.getLong(1);
			if (menuOid != null) {
				userPrivDto.setMenuOid(menuOid.longValue());
			}
			userPrivDto.setUserOid(userId.longValue());
			userPrivDtos.add(userPrivDto);
		}
		return userPrivDtos;
	}

	/**
	 * 获取超过一个月为登录的用户列表，根据最后登录时间顺序排序
	 */
	@Override
	public List<UserDto> findUserByLastLoginTime(Long orgOid) {
		
		Calendar c = Calendar.getInstance();  
        c.setTime(new Date()); 
        c.add(Calendar.MONTH, -1);
		
		StringBuffer sql = new StringBuffer("SELECT U.OID, U.USER_ID, U.USER_NAME, U.IS_ADMIN, U.LAST_LOGIN_TIME");
		sql.append(" FROM T_USER U");
		sql.append(" WHERE U.LAST_LOGIN_TIME < ? AND U.ORG_OID = ? ");
		sql.append(" ORDER BY U.LAST_LOGIN_TIME DESC");
		
		Object[] param = new Object[2];
		param[0] = DateHelper.convert2String(c.getTime(), "yyyy-MM-dd");
		param[1] = orgOid;
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), param);
		List<UserDto> dtoList = new ArrayList<UserDto>();
		while(rs.next()) {
			UserDto dto = new UserDto();
			dto.setId(rs.getLong(1));
			dto.setUserId(rs.getString(2));
			dto.setUserName(rs.getString(3));
			dto.setAdmin("1".equals(rs.getString(4)));
			dto.setLastLoginTime(DateHelper.convert2String(rs.getDate(5), DateHelper.DATETIME_FORMATE));
			
			dtoList.add(dto);
		}
        return dtoList;
	}

	public IPaging getPagingWithParam(int pageNumber, int pageSize, Map<String, String> valueMap) {
		List<Object> valueList = new ArrayList<Object>(4);
		StringBuffer hql = new StringBuffer(" from User u where 1=1 ");
		
		if (valueMap != null) {
			if (!StringHelper.isEmpty(valueMap.get("userType"))) {
				hql.append(" and u.isAdmin = ? ");
				if (FilterUtil.filterString2Boolean(valueMap.get("userType"))) {
					valueList.add(true);
				} else {
					valueList.add(false);
				}
			}
			
			
			if (!StringHelper.isEmpty(valueMap.get("orgOid"))) {
				hql.append(" and u.orgOid = ? ");
				valueList.add(FilterUtil.filterString2Long(valueMap.get("orgOid")));
			}
			
			if (!StringHelper.isEmpty(valueMap.get("entOid"))) {
				hql.append(" and u.entOid = ? ");
				valueList.add(FilterUtil.filterString2Long(valueMap.get("entOid")));
			}
		}
		
		return this.queryByPage(pageNumber, pageSize, valueList.toArray(), hql.toString());
	}
}
