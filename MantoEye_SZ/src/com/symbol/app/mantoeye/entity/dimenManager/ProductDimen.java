package com.symbol.app.mantoeye.entity.dimenManager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tbProductDimens")
public class ProductDimen implements Dimen {

	private int dimensId;
	private String dimensName;
	private String dimensDesc;
	private int preDimens;
	
	/* (non-Javadoc)
	 * @see com.symbol.modules.common.entity.Dimen#getDimensId()
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getDimensId() {
		return dimensId;
	}
	public void setDimensId(int dimensId) {
		this.dimensId = dimensId;
	}
	
	/* (non-Javadoc)
	 * @see com.symbol.modules.common.entity.Dimen#getDimensName()
	 */
	@Column
	public String getDimensName() {
		return dimensName;
	}
	public void setDimensName(String dimensName) {
		this.dimensName = dimensName;
	}
	
	/* (non-Javadoc)
	 * @see com.symbol.modules.common.entity.Dimen#getDimensDesc()
	 */
	@Column
	public String getDimensDesc() {
		return dimensDesc;
	}
	public void setDimensDesc(String dimensDesc) {
		this.dimensDesc = dimensDesc;
	}
	
	@Column
	public int getPreDimens() {
		
		//return preDimens;
		return 0;
	}
	
	public void setPreDimens(int preDimens) {
		this.preDimens = preDimens;
	}
	
}
