package com.symbol.app.mantoeye.entity.dimenManager;

import java.util.List;


public interface DimenGroup {

	
	public abstract int getGroupId();

	public abstract String getGroupName();

	public abstract int getParent();

	public abstract List<DimenGroup> getChildren();

	public Class dimenClass();
	
	public Class mappingClass();
}