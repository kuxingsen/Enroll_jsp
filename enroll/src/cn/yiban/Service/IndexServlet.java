package cn.yiban.Service;

import cn.yiban.Bean.AppContent;
import cn.yiban.YBAPI.DemoApi;
import cn.yiban.open.Authorize;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 傻逼 on 2018/1/24.
 */
@WebServlet(name = "IndexServlet")
public class IndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        HttpSession httpSession = request.getSession();
        //System.out.println(request.getPathInfo());
        /*
        *获取授权凭证token
         */
        Authorize au = new Authorize(AppContent.AppID,AppContent.AppSecret);
        String code = request.getParameter("code");
        if(code == null||code.isEmpty())//先判断是否已授权 code为授权令牌
        {
            //System.out.println("????????");
            String url=au.forwardurl(AppContent.BACK_URL,"what",Authorize.DISPLAY_TAG_T.WEB);
            //System.out.println(url);
            response.sendRedirect(url);
        }
        else{
            //System.out.println(code);
            String back = au.querytoken(code,AppContent.BACK_URL);
            JSONObject json = JSONObject.fromObject(back);
            //System.out.println(json);
            //printWriter.print(json);
            //String access_token = json.get("access_token").toString();//获取授权凭证
            httpSession.setAttribute("access_token",json.get("access_token").toString());
            //System.out.println(httpSession.getAttribute("access_token"));
            httpSession.setAttribute("yb_userid",json.get("userid").toString());
            //JSONObject json2= JSONObject.fromObject(DemoApi.MSG(json.get("access_token").toString(),"10967192","测试消息接口","user"));
            request.getRequestDispatcher("/toPrepare").forward(request,response);
            /*
            System.out.println("Index.........");
            JSONObject json1= JSONObject.fromObject(DemoApi.Addfriend(json.get("access_token").toString(),"10967292","test"));
            System.out.println(json1);
            //JSONObject json2= JSONObject.fromObject(DemoApi.MSG(json.get("access_token").toString(),"10967192","测试消息接口","user"));
            // System.out.println(json2);
            request.getRequestDispatcher("/Add").forward(request,response);
            */
            /*
            String NEWs = DemoApi.News_Push(access_token,1,2);
            JSONObject json2 = JSONObject.fromObject(NEWs);
            printWriter.print(json2);
            */
        }

    }
}
