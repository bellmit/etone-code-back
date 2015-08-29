package com.symbol.app.mantoeye.service.dimenManager;

import java.io.File;
import java.util.List;

import com.symbol.app.mantoeye.entity.dimenManager.Def;
import com.symbol.app.mantoeye.entity.dimenManager.Dimen;
import com.symbol.app.mantoeye.entity.dimenManager.DimenGroup;







public interface DimenManager <D,G>{



	public abstract Dimen getDimen(int id);
	public abstract List<G> getDimenDefList();
	public abstract Def getDimenDef(int id);
	
	public abstract  DimenGroup getDimenGroup(int id);
	
	
	public abstract List<G> getDimenGroupList();
	
	public abstract List<?> getDimenList();
	public abstract List<?> getDimenList(int groupId);
	public abstract List<D> getDimenList(String query, int pageNo, int pageSize);
	public abstract List<D> getDimenList(int groupId, String query, int pageNo, int pageSize);
	public abstract int getDimenCount(int groupId, String query);
	public abstract int getDimenCount(String query);
	
	public abstract int getDimenGroupCount();
	public abstract List<G> getGroupChildList();

	
	public abstract void deleteDimen(int id);
	public abstract void deleteDimenDef(int id);
	public abstract void deleteDimenGroup(int id);
	public void deleteDimenMap(int groupId, String dimensIds);
	public void saveDimenMap(int groupId, int dimenId);
	
	public abstract int saveDimenDef(D dto);

	public abstract void saveDimen(D dto);

	public abstract void saveDimenGroup(G Group);


	public abstract void updateDimenDef(D dto);

	public abstract void updateDimen(D dto);

	public abstract void updateDimenGroup(G Group);

	public abstract void importDoc(File file);

}