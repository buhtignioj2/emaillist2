package com.cafe24.emaillist.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.emaillist.dao.EmaillistDao;
import com.cafe24.emaillist.vo.EmaillistVo;
import com.cafe24.mvc.util.WebUtil;

@WebServlet("/el")
public class EmaillistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding( "utf-8" );
		
		String actionName = request.getParameter( "a" );
		
		if( "form".equals(actionName) ) {
			// forwarding
			WebUtil.forward(request, response, "/WEB-INF/views/form.jsp" );
		} else if( "add".equals(actionName) ) {
			
			String firstName = request.getParameter( "first-name" );
			String lastName = request.getParameter( "last-name" );
			String email = request.getParameter( "email" );

			EmaillistVo vo = new EmaillistVo();
			vo.setEmail(email);
			vo.setFirstName(firstName);
			vo.setLastName(lastName);
			
			EmaillistDao dao = new EmaillistDao();
			dao.insert(vo);
			
			WebUtil.redirect(request, response, "/emaillist2/el" );
			
		} else  {
			/* default action (list) 처리 */
			EmaillistDao dao = new EmaillistDao();
			List<EmaillistVo> list = dao.getList();
			
			request.setAttribute( "list", list );
			
			// forwarding
			WebUtil.forward(request, response, "/WEB-INF/views/index.jsp" );
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
