package com.model2.mvc.service.product;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;



public interface ProductService {
	
	public Product addProduct(Product productVO) throws Exception;
	
	public Product getProduct(int prod_no) throws Exception;
	
	public Map<String, Object> getProductList(Search searchVO) throws Exception;
	
	public Product updateProduct(Product prodVO) throws Exception;
	
}