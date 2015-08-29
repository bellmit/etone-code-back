package com.symbol.app.mantoeye.dto.dimenManager;

import java.util.List;

import com.symbol.app.mantoeye.entity.dimenManager.ProductDimensGroup;


/**
 * @author Administrator
 * 
 */
public class ProductGroup extends ProductDimensGroup {

	private String checkBox;
	private String operate;
	private int parentID;
	private List<ProductGroup> children2;
	private boolean autoExec;

	public String getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public List<ProductGroup> getChildren2() {
		return children2;
	}

	public void setChildren2(List<ProductGroup> children2) {
		this.children2 = children2;
	}

	public boolean isAutoExec() {
		return autoExec;
	}

	public void setAutoExec(boolean autoExec) {
		this.autoExec = autoExec;
	}

}
