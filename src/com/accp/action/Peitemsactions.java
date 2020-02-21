package com.accp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.accp.biz.*;
import com.accp.entity.peitems;
import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class Peitemsactions
 */
@WebServlet("/test")
public class Peitemsactions extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Peitemsbiz dao=new Peitemsbiz();
    private ItemtypesBiz biz = new ItemtypesBiz();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Peitemsactions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  response.setContentType("text/html");
		  request.setCharacterEncoding("utf-8");
		  response.setCharacterEncoding("utf-8");
		  String m=request.getParameter("m");
		   try {
			   Method method=this.getClass().getDeclaredMethod(m,HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this,request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	private void show2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/peitemsa/test?m=show1");
	}
	private void show1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("DATA", dao.queryAll());
		request.setAttribute("type", biz.queryAll());
		request.getRequestDispatcher("/WEB-INF/view/show1.jsp").forward(request, response);
	}
	private void type1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(biz.queryAll()));
	}
	private void cx(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(JSON.toJSONString(dao.querytypeid(Integer.parseInt(request.getParameter("typeid")))));
		request.setAttribute("DATA", dao.querytypeid(Integer.parseInt(request.getParameter("typeid"))));
		request.setAttribute("type", biz.queryAll());
		request.getRequestDispatcher("/WEB-INF/view/show1.jsp").forward(request, response);
	}
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/add.jsp").forward(request, response);
	}
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("DATA",dao.queryid((Object)Integer.parseInt(request.getParameter("id"))));
		request.setAttribute("type", biz.queryAll());
		request.getRequestDispatcher("/WEB-INF/view/update.jsp").forward(request, response);
	}
	private void sc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dao.dele((Object)request.getParameter("id"));
	}
	private void xg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		peitems p=new peitems(Integer.parseInt(request.getParameter("id")),new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8"),Integer.parseInt(request.getParameter("type")),Integer.parseInt(request.getParameter("necessary")),request.getParameter("ref"),Float.parseFloat(request.getParameter("price")),new String(request.getParameter("info").getBytes("iso-8859-1"),"utf-8"));
		dao.update(p);
		show2(request, response);
	}
}
