package com.symbol.app.mantoeye.entity.dimenManager;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public interface Dimen {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public abstract int getDimensId();

	@Column
	public abstract String getDimensName();

	@Column
	public abstract String getDimensDesc();

}