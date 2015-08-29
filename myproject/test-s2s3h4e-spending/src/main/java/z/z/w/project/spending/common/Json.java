/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.spending.common.Json.java
 *         Desc: JSON模型
 *  			   用户后台向前台返回的JSON对象
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-13 上午11:21:55 
 *   LastChange: 2013-11-13 上午11:21:55 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.common;

/**
 * z.z.w.project.spending.common.Json.java
 */
public class Json {
	private boolean success = false;
	private String msg = "";
	private Object obj = null;

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:22:41
	 */
	public Json() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Json [success=" + success + ", msg=" + msg + ", obj=" + obj
				+ "]";
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
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
		result = prime * result + ((obj == null) ? 0 : obj.hashCode());
		result = prime * result + (success ? 1231 : 1237);
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
		Json other = (Json) obj;
		if (msg == null) {
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		if (this.obj == null) {
			if (other.obj != null)
				return false;
		} else if (!this.obj.equals(other.obj))
			return false;
		if (success != other.success)
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:22:47
	 * 
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:22:47
	 * 
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:22:47
	 * 
	 * @return the obj
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:22:47
	 * 
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:22:47
	 * 
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:22:47
	 * 
	 * @param obj
	 *            the obj to set
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

}
