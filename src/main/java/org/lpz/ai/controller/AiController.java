package org.lpz.ai.controller;
import java.util.Date;

import org.lpz.ai.common.BaseResponse;
import org.lpz.ai.common.ErrorCode;
import org.lpz.ai.common.ResultUtils;
import org.lpz.ai.exception.BusinessException;
import org.lpz.ai.mapper.ContentMapper;
import org.lpz.ai.model.domain.Content;
import org.lpz.ai.model.domain.User;
import org.lpz.ai.service.UserService;
import org.lpz.ai.utils.AiUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class AiController {

    @Resource
    private ContentMapper contentMapper;

    @Resource
    private UserService userService;

    @PostMapping("/word")
    public BaseResponse<String> word(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        User loginUser = userService.getLoginUser(request);

        String res = AiUtils.word(file);
        Content content = new Content();
        content.setContent(res);
        content.setType("文字识别");
        content.setCreateTime(new Date());
        content.setModifyTime(new Date());
        content.setUserId(loginUser.getId());

        contentMapper.insert(content);

        return ResultUtils.success(res);

    }

    @PostMapping("/img")
    public BaseResponse<String> img(@RequestParam MultipartFile file,HttpServletRequest request) throws IOException {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);

        String res = AiUtils.img(file);
        Content content = new Content();
        content.setContent(res);
        content.setType("图像识别");
        content.setCreateTime(new Date());
        content.setModifyTime(new Date());
        content.setUserId(loginUser.getId());
        contentMapper.insert(content);

        return ResultUtils.success(res);
    }
    @GetMapping("/nlp")
    public BaseResponse<String> nlp(@RequestParam(required = false) String text,HttpServletRequest request){
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);

        String res = AiUtils.nlp(text);
        Content content = new Content();
        content.setContent(res);
        content.setType("文本纠错");
        content.setCreateTime(new Date());
        content.setModifyTime(new Date());
        content.setUserId(loginUser.getId());

        contentMapper.insert(content);

        return ResultUtils.success(res);
    }




}
