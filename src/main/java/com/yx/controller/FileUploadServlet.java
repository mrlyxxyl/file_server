package com.yx.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URLDecoder;

/**
 * User: LiWenC
 * Date: 16-9-18
 */
@WebServlet(urlPatterns = {"/servlet/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(403);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        try {

            String projectId = req.getParameter("projectId");//项目id
            String fileName = URLDecoder.decode(req.getParameter("fileName"), "UTF-8");//文件名称
            long fileLen = Long.parseLong(req.getParameter("fileLen"));//文件总长度
            long startPoint = Long.parseLong(req.getParameter("startPoint"));//保存起始点
            Part part = req.getPart("file");
            String uploadPath = "E:/" + projectId;
            String str;
            if (part != null) {
                File file = new File(uploadPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                InputStream is = part.getInputStream();

                String targetFile = uploadPath + "/" + fileName;
                RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "rw");
                if (!new File(targetFile).exists()) {
                    randomAccessFile.setLength(fileLen);
                }
                randomAccessFile.seek(startPoint);
                int len;
                byte[] bytes = new byte[1024];
                while ((len = is.read(bytes)) > 0) {
                    randomAccessFile.write(bytes, 0, len);
                }
                randomAccessFile.close();
                str = "true";
            } else {
                str = "false";
            }
            resp.getOutputStream().print(str);
        } catch (Exception e) {
            System.out.println("upload" + e.getMessage());
            resp.getOutputStream().print("false");
        }
    }
}
