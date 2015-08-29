/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.model.TestUser.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-2 下午08:47:15 
 *   LastChange: 2013-11-2 下午08:47:15 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * z.z.w.project.test.model.TestUser.java
 */
@Entity
@Table(name = "testuser", schema = "")
public class TestUser implements java.io.Serializable {

	/**
	 * <br>
	 * Created on: 2013-11-2 下午09:07:16
	 */
	private static final long serialVersionUID = -5269740607901488963L;
	private int id = 0;
	private String name = "";

	/**
	 * <br>
	 * Created on: 2013-11-3 下午09:28:35
	 */
	public TestUser() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestUser [id=" + id + ", name=" + name + "]";
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
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
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
		TestUser other = (TestUser) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午09:28:57
	 * 
	 * @return the id
	 */
	@Id
	@Column
	public int getId() {
		return id;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午09:28:57
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午09:28:57
	 * 
	 * @return the name
	 */
	@Column
	public String getName() {
		return name;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午09:28:57
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
