package com.abs.ps.web;

import com.abs.ps.web.dto.UserDto;

public class SessionValidation {
	
	public SessionValidation() {}
	
	private Object obj;
	public SessionValidation(Object obj) {
		this.obj = obj;
	};
	
	public Boolean isValid() {
		if (obj instanceof UserDto) {
			UserDto userDto = (UserDto) obj;
			if (userDto.getOrgOid() <= 0) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
	
	public Boolean isValid(Object obj) {
		if (obj instanceof UserDto) {
			UserDto userDto = (UserDto) obj;
			if (userDto.getOrgOid() <= 0) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
}
