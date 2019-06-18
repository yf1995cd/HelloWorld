package cn.yf.customer.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.JdbcUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.yf.customer.domain.Customer;
import cn.yf.customer.domain.PageBean;


public class CustomerDao {
	private QueryRunner qRunner=new TxQueryRunner();
	private static Connection connection=null;	
	
	public void add(Customer c) throws Exception {
		String sqlString="INSERT INTO CUSTOMER VALUES(?,?,?,?,?,?,?)";
		Object[] params= {c.getCid(),c.getCname(),c.getGender(),
				c.getBirthday(),c.getCellphone(),c.getEmail(),
				c.getDescription()};
//		connection=JdbcUtils.getConnection();
	try {
		qRunner.update( sqlString, params);
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
		
	}	
	
	public PageBean<Customer> findall(int pc,int ps) {
		try {
		PageBean<Customer> pb=new PageBean<Customer>();
		pb.setPc(pc);
		pb.setPs(ps);
		String sql="select count(*) from customer ";
		Number num=(Number)qRunner.query(sql, new ScalarHandler()); 
		int tr=num.intValue();
		pb.setTr(tr);
		sql="select * from customer order by cname limit ? , ?";
		Object[] params= {(pc-1)*ps, ps};
		List<Customer> beanlist=qRunner.query(sql, 
				new BeanListHandler<Customer>(Customer.class), params);
		pb.setBeanList(beanlist);
		return pb;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
}
	
	
	
@Test
public void fun1() {
	Customer customer=new Customer();
	customer.setCid(CommonUtils.uuid());
	customer.setBirthday("2015-02-02");
	customer.setCellphone("234233");
	customer.setCname("¹¬±¾Ò»ÀÉ");
	customer.setDescription("¹þàç");
	customer.setEmail("gongbyl@163.com");
	customer.setGender("ÄÐ");
	try {
		add(customer);
		System.out.println("³É¹¦");
	} catch (Exception e) {
		e.printStackTrace();
	} 
}

public Customer load(String cidString) {
	String sql="select * from customer where cid=?";
	try {
		return qRunner.query(sql, new BeanHandler<Customer>(Customer.class), cidString);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return null;
}

public void edit(Customer c) {
	String sql="update customer set cname=?,gender=?,birthday=?,cellphone=?,"
			+ "email=?,description=? where cid=?";
			Object[] params= {c.getCname(),c.getGender(),
					c.getBirthday(),c.getCellphone(),c.getEmail(),
					c.getDescription(),c.getCid()};
			try {
				qRunner.update(sql, params);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
}
@Test
public void fun3() {
	int count=0;
	for (int i = 0; i <200; i++) {
		Customer c=new Customer();
		c.setCid(CommonUtils.uuid());
		c.setCname("itcast_"+i);
		c.setGender(i%2==0 ? "ÄÐ":"Å®");
		c.setBirthday("2014-01-01");
		c.setCellphone("12345"+i);
		c.setEmail("itcast_"+i+"@163.com");
		c.setDescription("woshi"+i);
		try {
			add(c);
			count++;
			
			System.out.println(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

//public PageBean<Customer> query(Customer criteria,int pc,int ps) {
//	try {
//	StringBuilder sql=new StringBuilder("select * from customer where 1=1 ");
//	String cname=criteria.getCname();
//	List<Object> params=new ArrayList<Object>();
//	if (cname!=null && !cname.trim().isEmpty()) {
//		sql.append("and cname like ? ");
//		params.add("%"+cname+"%");
////		params.add(cname);
//	}
//	String gender=criteria.getGender();
//	if (gender!=null && !gender.trim().isEmpty()) {
//		sql.append(" and gender = ? ");
//		params.add(gender);
//	}
//	String cellphone=criteria.getCellphone();  
//	if (cellphone!=null && !cellphone.trim().isEmpty()) {
//		sql.append(" and cellphone like ? ");
//		params.add("%"+cellphone+"%");
//	}
//	String email=criteria.getEmail();
//	if (email!=null && !email.trim().isEmpty()) {
//		sql.append(" and email like ? ");
//		params.add("%"+email+"%");
//	}
//	List<Customer> lcu= qRunner.query(sql.toString(), new BeanListHandler<Customer>(Customer.class), params.toArray());
//	return lcu;
//	}catch (Exception e) {
//		throw new RuntimeException(e);
//		}
//}

public PageBean<Customer> query(Customer criteria,int pc,int ps) {
	try {
		PageBean<Customer> pb=new PageBean<Customer>();
		pb.setPc(pc);
		pb.setPs(ps);
	StringBuilder cntSql=new StringBuilder("select count(*) from customer ");
	StringBuilder whereSql=new StringBuilder(" where 1=1 ");
	String cname=criteria.getCname();
	List<Object> params=new ArrayList<Object>();
	if (cname!=null && !cname.trim().isEmpty()) {
		whereSql.append("and cname like ? ");
		params.add("%"+cname+"%");
	}
	String gender=criteria.getGender();
	if (gender!=null && !gender.trim().isEmpty()) {
		whereSql.append(" and gender = ? ");
		params.add(gender);
	}
	String cellphone=criteria.getCellphone();  
	if (cellphone!=null && !cellphone.trim().isEmpty()) {
		whereSql.append(" and cellphone like ? ");
		params.add("%"+cellphone+"%");
	}
	String email=criteria.getEmail();
	if (email!=null && !email.trim().isEmpty()) {
		whereSql.append(" and email like ? ");
		params.add("%"+email+"%");
	}
	Number num=(Number)qRunner.query(cntSql.append(whereSql).toString(), 
			new ScalarHandler(),params.toArray());
	int tr=num.intValue();
	pb.setTr(tr);
	
	StringBuilder sql=new StringBuilder("select * from customer");
	StringBuilder limitSql=new StringBuilder(" limit ? ,? ");
	params.add((pc-1)*ps);
	params.add(ps);
	List<Customer> beanList=qRunner.query(sql.append(whereSql).append(limitSql).toString(), 
			new BeanListHandler<Customer>(Customer.class),
			params.toArray());
	pb.setBeanList(beanList);
	return pb;
	}catch (Exception e) {
		throw new RuntimeException(e);
		}
}

@Test
public void fun4() {
	Customer customer=new Customer();
//	customer.setCid(CommonUtils.uuid());
//	customer.setBirthday("2015-02-02");
//	customer.setCellphone("234233");
//	customer.setCname("¹¬±¾Ò»ÀÉ");
//	customer.setDescription("¹þàç");
//	customer.setEmail("gongbyl@163.com");
	customer.setGender("ÄÐ");
	try {
		PageBean<Customer> lc=query(customer,1,10);
		for (Customer customer2 : lc.getBeanList()) {
			System.out.println(customer2.toString());
		}
		System.out.println(lc.toString());
	} catch (Exception e) {
		e.printStackTrace();
	} 
}
}