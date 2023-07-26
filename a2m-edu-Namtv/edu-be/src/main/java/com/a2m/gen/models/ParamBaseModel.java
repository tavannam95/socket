package com.a2m.gen.models;

import java.util.Date;

/**
 *
 * @author doanhq
 * @version $Id$
 */
public class ParamBaseModel {

	public Long key;

	public String crudType;

	public Boolean searchStatus;

	public Integer loginUserId;

	public Integer pageNo;

	public Integer page;

	public Integer rows;

	public String searchText;

	public Date insDate;

	public Date updDate;

	public String insUid;

	public String updUid;

	public Boolean getAll;

	public Boolean deleteFlag;

	/**
	 * <strong>checkKey</strong><br>
	 * <br>
	 *
	 * @param key
	 * @return
	 */
	public boolean checkKey(Integer key) {
		return true;
	}

	/**
	 * <strong>checkParentKey</strong><br>
	 * <br>
	 *
	 * @param key
	 * @return
	 */

	public boolean checkParentKey(Integer key) {
		return true;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getCrudType() {
		return crudType;
	}

	public void setCrudType(String crudType) {
		this.crudType = crudType;
	}

	public Integer getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(Integer loginUserId) {
		this.loginUserId = loginUserId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Boolean getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(Boolean searchStatus) {
		this.searchStatus = searchStatus;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getInsUid() {
		return insUid;
	}

	public void setInsUid(String insUid) {
		this.insUid = insUid;
	}

	public String getUpdUid() {
		return updUid;
	}

	public void setUpdUid(String updUid) {
		this.updUid = updUid;
	}

	public Boolean getGetAll() {
		return getAll;
	}

	public void setGetAll(Boolean getAll) {
		this.getAll = getAll;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
