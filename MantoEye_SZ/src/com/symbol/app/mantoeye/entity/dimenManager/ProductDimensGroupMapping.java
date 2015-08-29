package com.symbol.app.mantoeye.entity.dimenManager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tbProductDimensGroupMapping")
public class ProductDimensGroupMapping  implements DimensMapping{

	private int recordId ;
	private int dimensId;
	private int groupId;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int id) {
		this.recordId = id;
	}
	@Column
	public int getDimensId() {
		return dimensId;
	}
	public void setDimensId(int dimensId) {
		this.dimensId = dimensId;
	}
	
	@Column
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	
	
}
