package com.qiu.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/24 下午5:32
 */
public class ServerPortal extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //我们的Token
    private static final String token = "tokensh";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServerPortal() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        String code = request.getParameter("code");
        String state = request.getParameter("state");
       /* String signature = "testestestsignature";
        String timestamp = new String(String.valueOf(new Date()));
        String nonce = "once";
        String echostr = "echostr";*/
        System.out.println("signature:" + signature);
        System.out.println("timestamp:" + timestamp);
        System.out.println("nonce:" + nonce);
        System.out.println("echostr:" + echostr);
        System.out.println("code:" + code);
        System.out.println("state:" + state);
        PrintWriter pw = response.getWriter();
        pw.append(echostr+" "+code+" "+state);
        pw.flush();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
