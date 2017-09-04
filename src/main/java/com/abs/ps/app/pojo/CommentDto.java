package com.abs.ps.app.pojo;

public class CommentDto {
	private String commentOid;
	private String comment;
	private String createTime;
	private String userOid;
	private String userName;
	private String imgUrl;
	private String deletable;
	public String getCommentOid() {
		return commentOid;
	}
	public void setCommentOid(String commentOid) {
		this.commentOid = commentOid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUserOid() {
		return userOid;
	}
	public void setUserOid(String userOid) {
		this.userOid = userOid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getDeletable() {
		return deletable;
	}
	public void setDeletable(String deletable) {
		this.deletable = deletable;
	}
	
}
