package cn.leadeon.sprjsp.controller;

import cn.leadeon.sprjsp.comm.ExportUtil;
import cn.leadeon.sprjsp.controller.service.TraficluckService;
import cn.leadeon.sprjsp.entity.TraficLuck;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: he.l
 * @create: 2019-07-04 17:21
 **/
@Controller
public class SystemController {
    private static final Logger logger = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private TraficluckService traficluckService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/find")
    @ResponseBody
    public List<TraficLuck> find(){
        Map<String,Object> params = new HashMap<String,Object>();
        return traficluckService.find(params);
    }

    /**
     * 多线程 导出
     * @return
     */
    @RequestMapping("/export")
    @ResponseBody
    public String export(){
       Map<String,Object> params = new HashMap<String,Object>();
       int total = traficluckService.total(params);
       traficluckService.export(total,params);
       return "";
    }

    /**
     * 下载组装好的文件
     *
     * @param request request
     * @param response response
     * @param fileName 文件名称
     */
    @RequestMapping("/downloadExportFile")
    public void downloadExportFile(HttpServletRequest request, HttpServletResponse response, String fileName) {
        InputStream in = null;
        OutputStream out = null;
        try {
            String path = ResourceUtils.getURL("classpath:static").getPath();
            String realPath = path+"/tempdir/"+fileName;
            if (!new File(realPath).exists()) {
                logger.error("需要下载的文件不存在！");
                return;
            }
            in = new FileInputStream(new File(realPath));
            // 这个就就是弹出下载对话框的关键代码
            response.setContentType("application/x-msdownload;charset=GBK");
            response.setHeader("Content-disposition", "attachment;filename=\"" + URLEncoder.encode(fileName + ".txt", "utf-8") + "\"");

            out = response.getOutputStream();

            IOUtils.copy(in, out);

            out.flush();

            in.close();

            out.close();
        } catch (Exception e) {
            logger.error("导出" + fileName + "失败！" + e);
        } finally {
            try {
                in.close();

                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
