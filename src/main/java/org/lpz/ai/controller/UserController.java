package org.lpz.ai.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lpz.ai.common.BaseResponse;
import org.lpz.ai.common.ErrorCode;
import org.lpz.ai.common.ResultUtils;
import org.lpz.ai.exception.BusinessException;
import org.lpz.ai.model.domain.User;
import org.lpz.ai.model.request.UserLoginRequest;
import org.lpz.ai.model.request.UserRegisterRequest;
import org.lpz.ai.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.lpz.ai.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = {"http://localhost:3000"},allowCredentials = "true")
//@CrossOrigin(origins = {"http://39.107.143.21:80"},allowCredentials = "true")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){

        if (userRegisterRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userRegisterRequest.getUsername();
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();


        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        long id = userService.userRegister(userAccount,userPassword,checkPassword,username);
        return ResultUtils.success(id);
    }

    /**
     * 用户登录
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){

        if (userLoginRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){

        if (request == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int i = userService.userLogout(request);
        return ResultUtils.success(i);
    }

    /**
     * 获取当前用户信息
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object object = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) object;
        if(user == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        // todo 校验用户是否合法
        long id = user.getId();
        User user1 = userService.getById(id);
        User savetyUser = userService.getSavetyUser(user1);
        return ResultUtils.success(savetyUser);
    }









}
