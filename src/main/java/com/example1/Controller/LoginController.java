package com.example1.Controller;

import com.example1.Dao.UserDao;
import com.example1.Domain.User;
import com.example1.util.GetSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Administrator on 2017/5/9.
 */
@Controller
public class LoginController {
    @Autowired
    private UserDao userDao;

    //登录页面访问
    @RequestMapping("/index")
    @ResponseBody
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        try {
            //返回视图之前,先去Cookie中查询是否有值，有则是用户之前点击过记住我
            HttpServletRequest request = GetSession.getRequest();
            Cookie[] cookies =request.getCookies();
            if(cookies!=null) {
                User user = new User();
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("userName")) {
                        String userName = cookie.getValue();
                        user.setUserName(userName);
                    }
                    if (cookie.getName().equals("passWord")) {
                        String passWord = cookie.getValue();
                        user.setPassWord(passWord);
                    }
                }
                //这个判断可以优化一下，不影响主逻辑 主要是判断在Cookie中是够找到了用户信息
                if(user.getPassWord()!=null){
                    mv.addObject("user",user);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //如果Cookie中没有值，直接返回页面
        mv.setViewName("Login");
        return mv;
    }


    //登录成功跳转页面（服务内部调用）
    @RequestMapping("/index1")
    public ModelAndView index1() {
         User user = (User) GetSession.getSession().getAttribute("user");
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user );
        mv.setViewName("Index");
        return mv;
    }

    //根据用户ID查询
    @RequestMapping("/findUserById")
    @ResponseBody
    public User findUserById(@RequestParam(value="id") Integer id) {
        User user = null;
        try {
            user = userDao.findUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    //查询所有用户
    @RequestMapping("/queryAll")
    @ResponseBody
    public List<User> queryAll() {
        try {
            List<User> list = userDao.queryAll();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*测试登录d1*/
    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(HttpServletResponse response, @RequestParam("userName")String userName, @RequestParam("passWord") String passWord,
                                    @RequestParam("userId")Integer id, @RequestParam("remember")String remember) {
        try {
           //用户登录，不管是否点击记住我，都要存到session中去
            Map<String,Object> resultMap = new HashMap<>();
            User user  = userDao.findUserByUser(userName,passWord);
            GetSession.getSession().setAttribute("user",user);

            //用户点击了记住我，将值存在Cookie中返回
            if(remember.equals("记住我")){
               Cookie cookie = new Cookie("userName",user.getUserName());
                Cookie cookie1 = new Cookie("passWord",user.getPassWord());
                response.addCookie(cookie);
                response.addCookie(cookie1);
            }else {//用户没有点击记住我，将Cookie中的值清空返回
                Cookie cookie = new Cookie("userName",null);
                Cookie cookie1 = new Cookie("passWord",null);
                response.addCookie(cookie);
                response.addCookie(cookie1);
            }
            //判断用户是否存在，如果不存在就返回未找到该用户
            if(user!=null){
                resultMap.put("status","ok");
                resultMap.put("user",user);
                return resultMap;
            }else {
                resultMap.put("status","error");
                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
}

    @RequestMapping("/toRegister")
    public ModelAndView toRegister() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("toRegister");
        return mv;
    }

    //下载导入CVS文件接口
    @RequestMapping("/download")
    public void dowonloadCSVCrate(HttpServletResponse response){
        CVSCreate(response);


    }

    public void CVSCreate(HttpServletResponse response) {
        // 表格头
        Object[] head = { "商品编码" };
        List<Object> headList = Arrays.asList(head);

        //数据
        String fileName = "testCSV.csv";//文件名称
        String filePath = "D:/test/"; //文件路径

        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(filePath + fileName);
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
          /*  csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "utf8"), 1024);*/

            //文件下载，使用如下代码
         response.setContentType("application/csv;charset=utf8");
         response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            ServletOutputStream out = response.getOutputStream();
           csvWtriter = new BufferedWriter(new OutputStreamWriter(out, "utf8"), 1024);

            int num = headList.size() / 2;
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < num; i++) {
                buffer.append(" ,");
            }
           // csvWtriter.write(buffer.toString() + fileName + buffer.toString());
           /* csvWtriter.newLine();*/

            // 写入文件头部
            writeRow(headList, csvWtriter);

            // 写入文件内容
           /* for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }*/
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写一行数据
     * @param row 数据列表
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }

    //跳转页面
    @RequestMapping("/jump")
    public ModelAndView jump(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("export");
        return mv;
    }

}
