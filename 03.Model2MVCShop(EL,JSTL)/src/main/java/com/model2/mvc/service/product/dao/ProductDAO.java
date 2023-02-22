package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.*;


public class ProductDAO {
	
	public ProductDAO(){
	}

	public void insertProduct(Product productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into product values (seq_product_prod_no.NEXTVAL,?,?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.executeUpdate();
		
		con.close();
	}

	public Product findProduct(int productNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from product where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, productNo);

		ResultSet rs = stmt.executeQuery();

		Product productVO = null;
		while (rs.next()) {
			productVO = new Product();
			productVO.setProdNo(Integer.parseInt(rs.getString("prod_no")));
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setProdDetail(rs.getString("prod_detail"));
			productVO.setManuDate(rs.getString("manufacture_day"));	
		    productVO.setPrice(Integer.parseInt(rs.getString("price")));
			productVO.setFileName(rs.getString("image_file"));
			productVO.setRegDate(rs.getDate("reg_date"));
		}
		
		con.close();

		return productVO;
	}

	public Map<String,Object> getProductList(Search search) throws Exception {
		Map<String , Object>  map = new HashMap<String, Object>();
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from product ";
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("")) {
				sql += " where prod_no like '" + search.getSearchKeyword()
						+ "%'";
			} else if (search.getSearchCondition().equals("1")&& !search.getSearchKeyword().equals("")) {
				sql += " where prod_NAME like '" + search.getSearchKeyword()
						+ "%'";
			}else if (search.getSearchCondition().equals("2")&& !search.getSearchKeyword().equals("")) {
				sql += " where price like '" + search.getSearchKeyword()
				+ "%'";
	}
		}
		sql += " order by prod_no";

		System.out.println("UserDAO::Original SQL :: " + sql); 
		
		int totalCount = getTotalCount(sql); //getTotalCount 실행 => count(*)
		System.out.println("UserDAO :: totalCount  :: " + totalCount); //totalcount출력
		
		sql = makeCurrentPageSql(sql, search); //현재페이지 정보를 준다 쿼리와 search 패스
		PreparedStatement pStmt = con.prepareStatement(sql); // 쿼리 준비
		ResultSet rs = pStmt.executeQuery(); //쿼리 실행 후 rs에 저장
		
		System.out.println(search);
		

		

		List<Product> list = new ArrayList<Product>();
	
		while(rs.next()){
				Product pvo = new Product();
				pvo.setProdNo(rs.getInt("prod_no"));
				pvo.setProdName(rs.getString("prod_name"));
				pvo.setProdDetail(rs.getString("prod_detail"));
				pvo.setManuDate(rs.getString("manufacture_day"));
				pvo.setPrice(rs.getInt("price"));
				pvo.setFileName(rs.getString("image_file"));
				pvo.setRegDate(rs.getDate("REG_DATE"));
				
				list.add(pvo);
		}
		
		map.put("totalCount", new Integer(totalCount));
		map.put("list", list);
		rs.close();
		pStmt.close();
		con.close();
		
		return map;
	}

	private String makeCurrentPageSql(String sql, Search search) {
		// TODO Auto-generated method stub
		sql = 	"SELECT * "+ 
				"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
								" 	FROM (	"+sql+" ) inner_table "+
								"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
	
		System.out.println("UserDAO :: make SQL :: "+ sql);	
	
		return sql;
	}

	private int getTotalCount(String sql) throws Exception {
		// TODO Auto-generated method stub
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable"; //
		System.out.println(sql);
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1); 
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount; 
	}

	public void updateProduct(Product productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "update PRODUCT set prod_name=?,prod_detail=?,"
				+ "manufacture_day=?,"
				+ "price=?, image_file=? where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		
		con.close();
	}
}