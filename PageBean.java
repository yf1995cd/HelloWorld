package cn.yf.customer.domain;

import java.util.List;

public class PageBean<T> {
	private int pc;  //��ǰҳ�� page code
//	private int tp;  //��ҳ��total page
	private int tr;  //�ܼ�¼��total record
	private int ps;  //ÿҳ��¼��page size 
	private List<T> beanList;  //��ǰҳ�ļ�¼
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
