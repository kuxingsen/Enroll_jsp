package cn.yiban.Service;

import cn.yiban.Bean.AppContent;
import cn.yiban.YBAPI.DemoApi;
import cn.yiban.open.Authorize;
import cn.yiban.open.common.User;
import cn.yiban.util.HTTPSimple;
import net.sf.json.JSONArray;
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
 * Created by Éµ±Æ on 2018/1/25.
 */
@WebServlet(name = "PrepareServlet",urlPatterns = {"/toPrepare"})
public class PrepareServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("yibanAPI",new DemoApi());
        httpSession.setAttribute("Method",new HTTPSimple());
        String user_id = (String)httpSession.getAttribute("yb_userid");
        String access_token = (String)httpSession.getAttribute("access_token");
        if(user_id==""||user_id.isEmpty())
        {
            Authorize au = new Authorize(AppContent.AppID,AppContent.AppSecret);
            String url=au.forwardurl(AppContent.BACK_URL,"what", Authorize.DISPLAY_TAG_T.WEB);
            //System.out.println(url);
            response.sendRedirect(url);
        }
        else {
            User user = new User(access_token);
            JSONObject jsonObject = JSONObject.fromObject(user.me());
            //System.out.println(jsonObject);
            //System.out.println(user_id+access_token);
            JSONObject infoJSON=jsonObject.getJSONObject("info");
            httpSession.setAttribute("yb_userhead",infoJSON.get("yb_userhead").toString());
            httpSession.setAttribute("yb_usernick",infoJSON.get("yb_usernick").toString());
            //httpSession.setAttribute("yb_usernick","aaaa");

            JSONObject friendJSON= JSONObject.fromObject(DemoApi.recommend(access_token,3));
            JSONObject newsJSON = JSONObject.fromObject(DemoApi.ybPush(access_token,2,3));
            //System.out.println(friendJSON);
            //System.out.println(newsJSON);

            if(friendJSON.get("status").toString().equals("success"))
            {
                JSONObject info_friend=friendJSON.getJSONObject("info");
                JSONArray friendList = info_friend.getJSONArray("list");
                httpSession.setAttribute("friendList",friendList);
            }

            if(newsJSON.get("status").toString().equals("success"))
            {
                JSONObject info_news=newsJSON.getJSONObject("info");
                JSONArray newsList = info_news.getJSONArray("list");
                /*for(int i = 0;i < newsList.size();i++)
                {
                    System.out.println(newsList.getJSONObject(i));
                }*/
                httpSession.setAttribute("newsList",newsList);
                //System.out.println(httpSession.getAttribute("newsList"));
            }

            request.getRequestDispatcher("toEnroll.jsp").forward(request,response);
            //response.sendRedirect("toEnroll.jsp");
        }
    }
}
