package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.*;

public class AddProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Product prodVO = new Product();
		
		String tmp_date=request.getParameter("manuDate");
		String date = tmp_date.replaceAll("-","");
	
		prodVO.setProdName(request.getParameter("prodName"));
		prodVO.setProdDetail(request.getParameter("prodDetail"));
		prodVO.setManuDate(date);
		prodVO.setPrice(Integer.parseInt(request.getParameter("price")));
		prodVO.setFileName(request.getParameter("fileName"));
		
		System.out.println(prodVO);
		
		ProductService service = new ProductServiceImpl();
		service.addProduct(prodVO);
		HttpSession session = request.getSession(true);
		session.setAttribute("prod", prodVO);
		
		return "redirect:/product/readProduct.jsp"; //��ġ�� ����ѹ� // �ٽú����ҵ�
	}
	
}
