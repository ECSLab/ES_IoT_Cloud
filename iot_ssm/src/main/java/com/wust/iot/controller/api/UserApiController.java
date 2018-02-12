package com.wust.iot.controller.api;

import com.wust.iot.dto.UserDto;
import com.wust.iot.dto.Result;
import com.wust.iot.enums.ResultEnums;
import com.wust.iot.exception.SimpleException;
import com.wust.iot.model.User;
import com.wust.iot.service.UserService;
import com.wust.iot.utils.HeadImgUtil;
import com.wust.iot.utils.JwtUtil;
import com.wust.iot.utils.Md5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping(value = "api/user")
@RestController
@EnableSwagger2
@Api(value = "user", description = "用户接口")
public class UserApiController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @ApiOperation(value = "登录", notes = "移动端注意获取其token\n" +
            "支持手机号登录，username可以填写手机号")
    @PostMapping(value = "/doLogin")
    public Result<User> doLogin(@RequestParam(value = "username") String username,
                                @RequestParam(value = "password") String password,
                                HttpServletRequest request) {
        logger.debug("进入api/doLogin");
        HttpSession session = request.getSession();
        if (username != null) {
            //判断是手机号还是用户名
            User user = null;
            if (isMobile(username)){
                user = userService.findUserByMobile(username);
            } else {
                user = userService.findUserByUsername(username);
            }


            if (user != null) {
                //进行加密
                String encryptPassword = Md5Util.getMD5ofStr(password);
                if (user.getPassword().equals(encryptPassword)) {

                    //手机端进行登录
                    if (request.getHeader("token") != null) {
                        try {
                            String token = JwtUtil.createJWT(user.getId());

                            //存入Redis
                            putTokenIntoRedis(user.getId(),token);
                            user.setToken(token);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new SimpleException(ResultEnums.TOKEN_GENERATE_ERROR);
                        }
                    } else {
                        //PC端
                        user.setToken("Web does not need");
                        user.setPassword(null);
                        session.setAttribute("user",user);
//                        session.setAttribute("SPRING_SECURITY_CONTEXT",  SecurityContextHolder.getContext());
                        //TODO 待添加二维码  自动登录功能
                    }
                    return new Result<User>(ResultEnums.SUCCESS, user);
                } else {
                    return new Result(ResultEnums.PASSWORD_NOT_RIGHT);
                }
            } else {
                //该用户不存在
                return new Result(ResultEnums.USER_NOT_EXIST);
            }
        } else {
            return new Result(ResultEnums.PARAMETER_INVALID);
        }
    }

    /**
     * 验证是否是手机号
     * @param str
     * @return
     */
    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    private void putTokenIntoRedis(Integer userId, String token) {
        ValueOperations<String,String> stringOperations = redisTemplate.opsForValue();
        //设置过期时间为24小时
        stringOperations.set(String.valueOf(userId),token,24, TimeUnit.HOURS);
        logger.debug("redis中中的数据:"+stringOperations.get("1"));
    }

    @ApiOperation(value = "注册")
    @PostMapping(value = "/doRegister")
    public Result<User> doRegister(@RequestParam(value = "username") @Valid String username,
                                   @RequestParam(value = "password") @Valid String password,
                                   @RequestParam(value = "bindPhone") @Valid String bindPhone) {

        if (userService.findUserByUsername(username) != null) {
            return new Result(ResultEnums.USERNAME_ALREADY_EXIST);
        }
        User user = new User();
        user.setUsername(username);
        String encryptPassword = Md5Util.getMD5ofStr(password);
        user.setPassword(encryptPassword);
        user.setBindPhone(bindPhone);
        if (userService.insertOneUser(user) == 1) {
            return new Result<User>(ResultEnums.SUCCESS, user);
        } else {
            return new Result(ResultEnums.SERVER_ERROR);
        }
    }

    @ApiOperation(value = "获取用户详细信息")
    @GetMapping(value = "/{id}/info")
    public Result<User> getUserInfo(@PathVariable("id") int id) {
        User user = userService.findOneUserById(id);
        if (user != null) {
            return new Result<User>(ResultEnums.SUCCESS, user);
        } else {
            return new Result<User>(ResultEnums.USER_NOT_EXIST);
        }
    }


    @ApiOperation(value = "查询用户名是否被占用", notes = "用户名不可重复")
    @GetMapping(value = "/{username}/check")
    public Result checkUsername(@PathVariable(value = "username") String username) {
        if (userService.findUserByUsername(username) != null) {
            return new Result(ResultEnums.USERNAME_ALREADY_EXIST);
        } else {
            return new Result(ResultEnums.USER_NOT_EXIST);
        }
    }

    @ApiOperation(value = "修改个人信息", notes = "用户名暂不可修改,上传用户头像时名称为headImage(只支持一张图片)")
    @PutMapping(value = "/modify")
    public Result modifyUserInfo(@RequestParam(value = "id", required = true) int id,
                                 @ModelAttribute(value = "user") UserDto user,
                                 HttpServletRequest request) {

        User oldUser = userService.findOneUserById(id);
        oldUser.setBindPhone(user.getBindPhone());
        oldUser.setBindEmail(user.getBindEmail());
        oldUser.setRealName(user.getRealName());
        oldUser.setBirthday(user.getBirthday());
        oldUser.setLocation(user.getLocation());
        oldUser.setWorkPlace(user.getWorkPlace());
        oldUser.setPersonalProfile(user.getPersonalProfile());
        oldUser.setPhone(user.getPhone());
        oldUser.setQq(user.getQq());

        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            //上传图片
            if (multipartResolver.isMultipart(request)) {
                //转化为多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Map<String, MultipartFile> fileMap = multiRequest.getFileMap();
                Iterator<String> fileNameIterator = fileMap.keySet().iterator();
                //上传路径
                File fileDir = new File(HeadImgUtil.HEAD_IMAGE_DIR);
                if (!fileDir.exists() && !fileDir.isDirectory()) {
                    fileDir.mkdirs();
                }

                //获取到图片且必须一张
                if (fileNameIterator.hasNext()) {
                    MultipartFile file = fileMap.get(fileNameIterator.next());
                    if (file.isEmpty() || file.getSize() == 0) {

                    }

                    String[] strings = file.getContentType().split("/");
                    String type = strings[1];
                    logger.info("图片类型:" + type);

                    //重命名上传后的文件名
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

                    String fileName = sdf.format(date) + "." + type;
                    logger.info("文件名称为:" + fileName);
                    //定义上传路径
                    File localFile = new File(HeadImgUtil.HEAD_IMAGE_DIR + fileName);
                    file.transferTo(localFile);
                    logger.info("上传路径:" + localFile.getPath());
                    System.out.println("上传路径为:" + localFile.getPath());

                    //设置图片路径
                    oldUser.setHeadImage(localFile.getPath());
                } else {
                    //没有上传图片的话，则抛出异常
                    throw new Exception("未上传图片");
                }
                //TODO 将信息存入数据库
                int count = userService.insertOneUser(oldUser);
                if (count != 1)
                    return new Result(ResultEnums.SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(ResultEnums.SERVER_ERROR);
        }
        return new Result<UserDto>(ResultEnums.SUCCESS, user);
    }
}
