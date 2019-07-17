package cn.leadeon.sprshiro.controller;

import cn.leadeon.sprshiro.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @description:
 * @author: he.l
 * @create: 2019-05-22 16:53
 **/
@Controller
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(TestController.class);
    //用户登录
//    @RequestMapping("/loginUser")
//    public String loginUser(@RequestParam("username") String username,
//                            @RequestParam("password") String password,
//                            HttpSession session) {
//        //把前端输入的username和password封装为token
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        Subject subject = SecurityUtils.getSubject();
//        try {
//            subject.login(token);
//            session.setAttribute("user", subject.getPrincipal());
//            return "index";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "login";
//        }
//    }
    @RequestMapping("/loginUser")
    public String login(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        String loginName = user.getUsername();
        logger.info("准备登陆用户 => {}", loginName);
        UsernamePasswordToken token = new UsernamePasswordToken(loginName,user.getPassword());
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + loginName + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + loginName + "]进行登录验证..验证通过");
        } catch (UnknownAccountException uae) {
            logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("message", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("message", "密码不正确");
        } catch (LockedAccountException lae) {
            logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,账户已锁定");
            redirectAttributes.addFlashAttribute("message", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,错误次数过多");
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            logger.info("用户[" + loginName + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            return "index";
        } else {
            token.clear();
            return "redirect:/login";
        }
    }
    //退出登录
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "login";
    }

    //访问login时跳到login.jsp
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    //退出登录
    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message", "您已安全退出");
        return "redirect:/login";
    }


    //admin角色才能访问
    @RequestMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin success";
    }

    //有delete权限才能访问
    @RequestMapping("/edit")
    @ResponseBody
    public String edit() {
        return "edit success";
    }

    @RequestMapping("/test")
    @ResponseBody
    @RequiresRoles("guest")
    public String test(){
        return "test success";
    }
}