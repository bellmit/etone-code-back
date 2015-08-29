package com.symbol.app.mantoeye.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * TbFeedback entity. 反馈信息表
 * 
 * @author ranhualin
 */
@Entity
@Table(name = "tbFeedback")
public class TbFeedback implements java.io.Serializable {

	// Fields
	private String id;// 主键id
	private String vcTitle;// 标题
	private String clContent;// 内容
	private String vcCreater;// 创建者
	private Date dtCreateDate;// 插入时间
	private String vcParentId;// 父文章id
	private String vcIp;// IP
	private Date dtLastUpdate;// 最后回复时间
	private Long intReplys;// 回复量

	// Constructors
	/** default constructor */
	public TbFeedback() {
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return this.id;
	}

	@Column(name = "vcTitle")
	public String getVcTitle() {
		return this.vcTitle;
	}

	@Column(name = "clContent")
	public String getClContent() {
		return this.clContent;
	}

	@Column(name = "vcCreater")
	public String getVcCreater() {
		return this.vcCreater;
	}

	@Column(name = "dtCreateDate")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDtCreateDate() {
		return this.dtCreateDate;
	}

	@Column(name = "vcParentId")
	public String getVcParentId() {
		return this.vcParentId;
	}

	@Column(name = "vcIp")
	public String getVcIp() {
		return this.vcIp;
	}

	@Column(name = "dtLastUpdate")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDtLastUpdate() {
		return this.dtLastUpdate;
	}

	@Column(name = "intReplys")
	public Long getIntReplys() {
		return this.intReplys;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setVcTitle(String vcTitle) {
		this.vcTitle = vcTitle;
	}

	public void setClContent(String clContent) {
		this.clContent = clContent;
	}

	public void setVcCreater(String vcCreater) {
		this.vcCreater = vcCreater;
	}

	public void setDtCreateDate(Date dtCreateDate) {
		this.dtCreateDate = dtCreateDate;
	}

	public void setVcParentId(String vcParentId) {
		this.vcParentId = vcParentId;
	}

	public void setVcIp(String vcIp) {
		this.vcIp = vcIp;
	}

	public void setDtLastUpdate(Date dtLastUpdate) {
		this.dtLastUpdate = dtLastUpdate;
	}

	public void setIntReplys(Long intReplys) {
		this.intReplys = intReplys;
	}
}
