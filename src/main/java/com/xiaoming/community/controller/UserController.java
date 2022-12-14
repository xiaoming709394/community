package com.xiaoming.community.controller;

import com.xiaoming.community.annotation.LoginRequired;
import com.xiaoming.community.entity.User;
import com.xiaoming.community.service.UserService;
import com.xiaoming.community.util.CommunityUtil;
import com.xiaoming.community.util.CookieUtil;
import com.xiaoming.community.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * 用户信息controller
 *
 * @author 赵明城
 * @date 2022/8/29
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 头像保存路径
     */
    @Value("${community.path.upload}")
    private String uploadPath;

    /**
     * 域名
     */
    @Value("${community.path.domain}")
    private String domain;

    /**
     * 根目录
     */
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    /**
     * 账号设置页面
     *
     * @return
     */
    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "site/setting";
    }

    /**
     * 更改用户头像
     *
     * @param headerImage
     * @param model
     * @return
     */
    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        //未获取到头像信息
        if (headerImage == null) {
            model.addAttribute("error", "您还没选择图片");
        }

        //原图片名称
        String fileName = headerImage.getOriginalFilename();
        //图片格式
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件的格式不正确");
            return "/site/setting";
        }

        //生成随机文件名
        fileName = CommunityUtil.generateUUID() + suffix;
        //确定存放路径
        File dest = new File(uploadPath + "/" + fileName);
        try {
            headerImage.transferTo(dest);
        } catch (IOException e) {
            log.error("上传文件失败：" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器异常！" + e);
        }

        //更新当前用户的头像的路径（web访问路径）
        //http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(), headerUrl);

        return "redirect:/index";
    }

    /**
     * http://localhost:8080/community/user/header/xxx.png
     * 读取用户头像信息
     *
     * @param fileName
     * @param response
     */
    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        //服务器存放路径
        fileName = uploadPath + "/" + fileName;
        //文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //响应图片
        response.setContentType("image/" + suffix);
        try (
                FileInputStream fis = new FileInputStream(fileName);
                OutputStream os = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }

        } catch (IOException e) {
            log.error("读取头像失败：" + e.getMessage());
        }
    }

    /**
     * 更改用户密码
     *
     * @param model
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @LoginRequired
    @RequestMapping(path = "/uploadPassword", method = RequestMethod.POST)
    public String uploadPassword(Model model, String oldPassword, String newPassword) {
        //获取用户登录信息
        User user = hostHolder.getUser();
        //更新密码
        Map<String, Object> map = userService.updatePassword(user.getId(), oldPassword, newPassword);
        //判断更改密码是否成功
        if (map == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("oldPasswordMsg", map.get("oldPasswordMsg"));
            model.addAttribute("newPasswordMsg", map.get("newPasswordMsg"));
            return "/site/setting";
        }
    }

}
