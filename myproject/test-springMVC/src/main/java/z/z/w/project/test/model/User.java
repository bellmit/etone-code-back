/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.model.User.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-30 上午11:45:43 
 *   LastChange: 2013-10-30 上午11:45:43 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.model;

/**
 * z.z.w.project.test.model.User.java
 */
public class User {

	private int id;
	private String name;

	/**
	 * <br>
	 * Created on: 2013-10-30 上午11:46:00
	 */
	public User() {
		// TODO 2013-10-30上午11:46:00 User constructor
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
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
		User other = (User) obj;
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
	 * Created on: 2013-10-30 上午11:46:05
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * <br>
	 * Created on: 2013-10-30 上午11:46:05
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * <br>
	 * Created on: 2013-10-30 上午11:46:05
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * <br>
	 * Created on: 2013-10-30 上午11:46:05
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
