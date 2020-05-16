package com.pgl.xiao.controller;

import com.pgl.xiao.domain.Image;
import com.pgl.xiao.service.ImageService;
import com.pgl.xiao.core.FastDFSClient;
import com.pgl.xiao.utils.ImageFileSettings;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.pgl.xiao.core.Constants.IMAGE_URL;

@CrossOrigin
@RestController
@RequestMapping(value = IMAGE_URL)
public class ImageController implements BasicController<Image> {

    @Resource
    private ImageFileSettings settings;

    @Resource
    private ImageService service;

    @Value("${upload.url}")
    private String uploadURL;

    @Value("${upload.location}")
    private String uploadPath;

    @Resource
    private FastDFSClient fastDFSClient;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object findByPage(Map<String, String> params) {
        return null;
    }

    @Override
    public Object update(Image image) {
        return null;
    }

    @Override
    public Object find(Integer id) {
        return null;
    }

    @Override
    public Object delete(Integer id) {
        return null;
    }

    @Override
    public Object save(Map<String, String> params) {
        return null;
    }

    /**
     * 图片上传的处理
     * @param req
     * @return
     */
    @PostMapping(value = "save", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object add(HttpServletRequest req)  {
        logger.info("上传文件");
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置编码
        upload.setHeaderEncoding("UTF-8");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        // 得到上传的所有文件的文件名
        Iterator itr = multipartRequest.getFileNames();
        // 遍历上传的文件
        Map<String, Object> map = new HashMap<>();
        try {
            while (itr.hasNext()) {
                // 得到上传文件的文件名
                String fileName = (String) itr.next();
                // 得到上传文件
                MultipartFile item = multipartRequest.getFile(fileName);
                // 得到上传文件的原文件文件名
                String originalName = item.getOriginalFilename();
                logger.info("上传文件：" + originalName);
                // 得到扩展名
                String extName = originalName.substring(originalName.lastIndexOf(".") + 1);
                byte[] bytes = item.getBytes();
                // 得到图片的Group位置信息
                String token = fastDFSClient.uploadFile(bytes, extName);
                String fileInfo = token.replaceAll("group1/M00/", "data/");
                String path = token.replace("group1/M00", uploadPath);
                Image image = new Image();
                image.setName(originalName);
                image.setCreatedate(LocalDateTime.now());
                image.setPath(path);
                String url = uploadURL + "/" + fileInfo;
                image.setUrl(url);
                service.save(image);
                // 4.成功时，设置map
                map.put("error", 0);
                map.put("message", "上传成功");
                map.put("url", url);
            }
        } catch(Exception e) {
            logger.info(e.getMessage());
            map = new HashMap<>();
            map.put("error", 1);
            map.put("message", "上传失败");
        }
        return map;
    }
}
