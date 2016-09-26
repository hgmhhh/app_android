package com.wemolian.app.wml.entity;

import java.util.List;

/**
 * 服务器返回的数据列表实体类，CommentList-->返回的数据是圈子时，会有评论列表
 * 
 * @author zhangyun
 *
 */
public class Data {

	private List<Comment> comment;

	private int Remark5;

	private String imagePath;

	private String externalUse;

	private String imagesText;

	private String imagesName;

	private int Id;

	private String circleid;

	private int state;

	private String addDate;

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public void setCommentList(List<Comment> comment) {
		this.comment = comment;
	}

	public List<Comment> getCommentList() {
		return this.comment;
	}

	public void setRemark5(int Remark5) {
		this.Remark5 = Remark5;
	}

	public int getRemark5() {
		return this.Remark5;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setExternalUse(String externalUse) {
		this.externalUse = externalUse;
	}

	public String getExternalUse() {
		return this.externalUse;
	}

	public void setImagesText(String imagesText) {
		this.imagesText = imagesText;
	}

	public String getImagesText() {
		return this.imagesText;
	}

	public void setImagesName(String imagesName) {
		this.imagesName = imagesName;
	}

	public String getImagesName() {
		return this.imagesName;
	}

	public void setId(int Id) {
		this.Id = Id;
	}

	public int getId() {
		return this.Id;
	}

	public void setCircleid(String circleid) {
		this.circleid = circleid;
	}

	public String getCircleid() {
		return this.circleid;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return this.state;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public String getAddDate() {
		return this.addDate;
	}
}
