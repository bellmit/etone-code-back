/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.model.TbResourceRote.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-12 上午11:23:10 
 *   LastChange: 2013-11-12 上午11:23:10 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * z.z.w.project.test.model.TbResourceRote.java
 */
@Entity
public class TbResourceRote implements Serializable {

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:25:04
	 */
	private static final long serialVersionUID = -7508859988548282983L;
	private long nmResourceRoteid;
	private TbResourceRote tbResourceRote;

	private Set<TbResourceRote> tbResourceRotes = new HashSet<TbResourceRote>();
	private String vcNote;

	private String vcRotePage;

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:27:36
	 */
	public TbResourceRote() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TbResourceRote other = (TbResourceRote) obj;
		if (nmResourceRoteid != other.nmResourceRoteid)
			return false;
		if (tbResourceRote == null) {
			if (other.tbResourceRote != null)
				return false;
		} else if (!tbResourceRote.equals(other.tbResourceRote))
			return false;
		if (tbResourceRotes == null) {
			if (other.tbResourceRotes != null)
				return false;
		} else if (!tbResourceRotes.equals(other.tbResourceRotes))
			return false;
		if (vcNote == null) {
			if (other.vcNote != null)
				return false;
		} else if (!vcNote.equals(other.vcNote))
			return false;
		if (vcRotePage == null) {
			if (other.vcRotePage != null)
				return false;
		} else if (!vcRotePage.equals(other.vcRotePage))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:27:44
	 * 
	 * @return the nmResourceRoteid
	 */
	@Id
	@GeneratedValue
	public long getNmResourceRoteid() {
		return nmResourceRoteid;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:27:44
	 * 
	 * @return the tbResourceRote
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "intPRRId")
	public TbResourceRote getTbResourceRote() {
		return tbResourceRote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:27:44
	 * 
	 * @return the tbResourceRotes
	 */
	@OneToMany(mappedBy = "tbResourceRote", cascade = CascadeType.ALL)
	public Set<TbResourceRote> getTbResourceRotes() {
		return tbResourceRotes;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:27:44
	 * 
	 * @return the vcNote
	 */
	@Column(nullable = false)
	public String getVcNote() {
		return vcNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:27:44
	 * 
	 * @return the vcRotePage
	 */
	public String getVcRotePage() {
		return vcRotePage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (nmResourceRoteid ^ (nmResourceRoteid >>> 32));
		result = prime * result
				+ ((tbResourceRote == null) ? 0 : tbResourceRote.hashCode());
		result = prime * result
				+ ((tbResourceRotes == null) ? 0 : tbResourceRotes.hashCode());
		result = prime * result + ((vcNote == null) ? 0 : vcNote.hashCode());
		result = prime * result
				+ ((vcRotePage == null) ? 0 : vcRotePage.hashCode());
		return result;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:27:44
	 * 
	 * @param nmResourceRoteid
	 *            the nmResourceRoteid to set
	 */
	public void setNmResourceRoteid(long nmResourceRoteid) {
		this.nmResourceRoteid = nmResourceRoteid;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:27:44
	 * 
	 * @param tbResourceRote
	 *            the tbResourceRote to set
	 */
	public void setTbResourceRote(TbResourceRote tbResourceRote) {
		this.tbResourceRote = tbResourceRote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:27:44
	 * 
	 * @param tbResourceRotes
	 *            the tbResourceRotes to set
	 */
	public void setTbResourceRotes(Set<TbResourceRote> tbResourceRotes) {
		this.tbResourceRotes = tbResourceRotes;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:27:44
	 * 
	 * @param vcNote
	 *            the vcNote to set
	 */
	public void setVcNote(String vcNote) {
		this.vcNote = vcNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:27:44
	 * 
	 * @param vcRotePage
	 *            the vcRotePage to set
	 */
	public void setVcRotePage(String vcRotePage) {
		this.vcRotePage = vcRotePage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TbResourceRote [nmResourceRoteid=" + nmResourceRoteid
				+ ", tbResourceRote=" + tbResourceRote + ", tbResourceRotes="
				+ tbResourceRotes + ", vcNote=" + vcNote + ", vcRotePage="
				+ vcRotePage + "]";
	}

}
