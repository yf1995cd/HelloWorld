package cn.yf.customer.domain;

import java.util.List;

public class PageBean<T> {
	private int pc;  //当前页码 page code
//	private int tp;  //总页数total page
	private int tr;  //总记录数total record
	private int ps;  //每页记录数page size 
	private List<T> beanList;  //当前页的记录
	private String urlString;
	public String getUrlString() {
		return urlString;
	}
	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public int getTp() {
		int tp=tr/ps;
		return tr%ps==0 ? tp: tp+1;
	}
//	public void setTp(int tp) {
//		this.tp = tp;
//	}
	public int getTr() {
		return tr;
	}
	public void setTr(int tr) {
		this.tr = tr;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public List<T> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
	@Override
	public String toString() {
		return "PageBean [pc=" + pc + ",  tr=" + tr + ", ps=" + ps +"urlString="+urlString +"]";
	}
	

}
