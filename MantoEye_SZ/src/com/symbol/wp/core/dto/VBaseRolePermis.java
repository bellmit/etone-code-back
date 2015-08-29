package com.symbol.wp.core.dto;

import com.symbol.wp.core.entity.TbBaseRolePermis;

public class VBaseRolePermis extends TbBaseRolePermis implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3188211132474708384L;
	
	private Byte btPcreate;
	private Byte btPread;
	private Byte btPupdate;
	private Byte btPdelete;
	public Byte getBtPcreate() {
		return btPcreate;
	}
	public void setBtPcreate(Byte btPcreate) {
		this.btPcreate = btPcreate;
	}
	public Byte getBtPread() {
		return btPread;
	}
	public void setBtPread(Byte btPread) {
		this.btPread = btPread;
	}
	public Byte getBtPupdate() {
		return btPupdate;
	}
	public void setBtPupdate(Byte btPupdate) {
		this.btPupdate = btPupdate;
	}
	public Byte getBtPdelete() {
		return btPdelete;
	}
	public void setBtPdelete(Byte btPdelete) {
		this.btPdelete = btPdelete;
	}
	
	

}
