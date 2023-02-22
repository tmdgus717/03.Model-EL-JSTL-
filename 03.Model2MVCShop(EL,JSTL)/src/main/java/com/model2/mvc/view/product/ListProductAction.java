package com.model2.mvc.view.product;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.*;

public class ListProductAction  extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//System.out.println(request.getParameter("detailForm"));
		//System.out.println("menu : "+request.getParameter("menu"));
		Search search=new Search();
		int page=1;
		if(request.getParameter("currentPage") != null) //page값이 들어오면
			page=Integer.parseInt(request.getParameter("currentPage"));  //page 변환
		
		search.setCurrentPage(page);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize")); //3
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));//5
		search.setPageSize(pageSize);
		
		
		ProductService service = new ProductServiceImpl();
		Map<String,Object> map = service.getProductList(search);
		Page resultPage	= 
				new Page( page, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize); 

		System.out.println("ListUserAction ::"+resultPage); //resultPage
	
		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		request.setAttribute("resultPage", resultPage);
		return "forward:/product/listProduct.jsp";
	}

}
