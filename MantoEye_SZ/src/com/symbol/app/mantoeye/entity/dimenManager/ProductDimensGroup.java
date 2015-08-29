package com.symbol.app.mantoeye.entity.dimenManager;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tbProductDimensGroup")
public class ProductDimensGroup implements DimenGroup  {

	protected int groupId;
	protected String groupName;
	protected int parent;
	protected List<DimenGroup> children;
	
	
	/* (non-Javadoc)
	 * @see com.symbol.modules.common.entity.Dimen#getGroupId()
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	/* (non-Javadoc)
	 * @see com.symbol.modules.common.entity.Dimen#getGroupName()
	 */
	@Column
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	/* (non-Javadoc)
	 * @see com.symbol.modules.common.entity.Dimen#getParent()
	 */
//	@ManyToOne(targetEntity=ProductDimensGroup.class)
//	@JoinColumn(name="parent")
	@Column
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.common.entity.Dimen#getChildren()
	 */
	@OneToMany(mappedBy="groupId",targetEntity=ProductDimensGroup.class)
	@JoinColumn(name="parent")
	public List<DimenGroup> getChildren() {
		return children;
	}
	public void setChildren(List<DimenGroup> children) {
		this.children = children;
	}
	
	
	public Class dimenClass() {
		// TODO Auto-generated method stub
		return ProductDimen.class;
	}
	
	public   Class mappingClass(){
		return ProductDimensGroupMapping.class;
	}
	
}

