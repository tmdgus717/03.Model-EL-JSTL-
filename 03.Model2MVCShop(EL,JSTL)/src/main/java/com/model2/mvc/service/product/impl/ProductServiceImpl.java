package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.domain.*;

public class ProductServiceImpl implements ProductService {

	private ProductDAO productDAO;
	
	public ProductServiceImpl() {
		productDAO = new ProductDAO(); //다오 인스턴스 생성
	}
	@Override
	public Product addProduct(Product productVO) throws Exception {
		productDAO.insertProduct(productVO);
		return productVO;
	}

	@Override
	public Product getProduct(int prod_no) throws Exception {
		// TODO Auto-generated method stub
		return productDAO.findProduct(prod_no);
	}

	@Override
	public Map<String, Object> getProductList(Search searchVO) throws Exception {
		// TODO Auto-generated method stub
		return productDAO.getProductList(searchVO);
	}

	@Override
	public Product updateProduct(Product prodVO) throws Exception {
		// TODO Auto-generated method stub
		productDAO.updateProduct(prodVO);;
		return prodVO;
	}

}
