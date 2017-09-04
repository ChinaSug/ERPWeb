package com.abs.ps.domain;

import java.io.Serializable;

public class UserPriv implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3318469906185135065L;
	private Long oid;
	private Long userOid;
	private Long menuOid;
	private int seq;
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public Long getUserOid() {
		return userOid;
	}
	public void setUserOid(Long userOid) {
		this.userOid = userOid;
	}
	public Long getMenuOid() {
		return menuOid;
	}
	public void setMenuOid(Long menuOid) {
		this.menuOid = menuOid;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}

	
}
