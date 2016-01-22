package kr.co.ecore.web.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Member {
	
	String mid =  "";
	String mname =  "";
	String mcomment =  "";
	/**
	 * @return the mid
	 */
	public String getMid() {
		return mid;
	}
	/**
	 * @param mid the mid to set
	 */
	public void setMid(String mid) {
		this.mid = mid;
	}
	/**
	 * @return the mname
	 */
	public String getMname() {
		return mname;
	}
	/**
	 * @param mname the mname to set
	 */
	public void setMname(String mname) {
		this.mname = mname;
	}
	/**
	 * @return the mcomment
	 */
	public String getMcomment() {
		return mcomment;
	}
	/**
	 * @param mcomment the mcomment to set
	 */
	public void setMcomment(String mcomment) {
		this.mcomment = mcomment;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
