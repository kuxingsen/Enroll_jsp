package cn.yiban.Service;

import cn.yiban.Bean.Student;
import cn.yiban.Dao.Jdbc;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by 傻逼 on 2018/1/25.
 */
@WebServlet(name = "AddServlet", urlPatterns = {"/Add"})
public class AddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession httpSession = request.getSession();
        String yb_userid= (String) httpSession.getAttribute("yb_userid");
        String yb_usernick= (String) httpSession.getAttribute("yb_usernick");
        Student student = new Student(yb_userid,yb_usernick);
        String fileLocal = fileUpload(request,response,yb_usernick,student);//上传文件
        System.out.println(fileLocal);
        Jdbc jdbc = new Jdbc();
        String sql = "insert into EnrollList (yb_userid,yb_usernick,phonenumber,first,second,instest,reason,fileLocal) values"
                +"('"+yb_userid+"','"+yb_usernick+"','"+student.getPhonenumber()+"','"+student.getFirst()+"','"+student.getSecond()+
                "','"+student.getInstest()+"','"+student.getReason()+"','"+fileLocal+"')";
        System.out.println(sql);
        jdbc.executeInsert(sql);
        request.getRequestDispatcher("/toModify.jsp").forward(request,response);
    }

    private String fileUpload(HttpServletRequest request, HttpServletResponse response, String name,Student student) throws ServletException, IOException {
        //request.setCharacterEncoding("UTF-8");
        System.out.println("fileUpload.....");
        Boolean hasFile = false;
        String fileLocal = "D:\\Enroll\\"+name;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("utf-8");
        try {
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item:list)
            {
                if(item.isFormField())
                {
                    String inputName = item.getFieldName();
                    String inputValue = item.getString("utf-8");
                    System.out.println(inputName+"::"+inputValue);
                    switch ((inputName)){
                        case "phonenumber":
                            student.setPhonenumber(inputValue);break;
                        case "first":
                            student.setFirst(inputValue);break;
                        case "second":
                            student.setSecond(inputValue);break;
                        case "reason":
                            student.setReason(inputValue);break;
                        case "instest":
                            student.setInstest(inputValue);break;
                        default:
                            System.out.println("wrong...........");
                    }
                }
                else
                {
                    String fileName= item.getName();
                    fileName = fileName.substring(fileName.lastIndexOf("//")+1);
                    InputStream is = item.getInputStream();
                    File file = new File(fileLocal);
                    file.mkdirs();
                    FileOutputStream fos = new FileOutputStream(fileLocal+"\\"+fileName);

                    byte[] buf = new byte[1024];
                    int len = 0;
                    while( (len=is.read(buf))>0 ){
                        fos.write(buf,0,len);
                    }
                    is.close();
                    fos.close();
                    hasFile = true;
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return hasFile?fileLocal:"null";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
