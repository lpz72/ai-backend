package org.lpz.ai.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lpz.ai.common.BaseResponse;
import org.lpz.ai.common.DeleteRequest;
import org.lpz.ai.common.ErrorCode;
import org.lpz.ai.common.ResultUtils;
import org.lpz.ai.exception.BusinessException;
import org.lpz.ai.mapper.ContentMapper;
import org.lpz.ai.model.domain.Content;
import org.lpz.ai.model.domain.User;
import org.lpz.ai.service.ContentService;
import org.lpz.ai.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static net.sf.jsqlparser.parser.feature.Feature.delete;

@RestController
public class ContentController {

    @Resource
    private ContentMapper contentMapper;
    @Resource
    private ContentService contentService;

    @Resource
    private UserService userService;

//    @GetMapping("/list")
//    public BaseResponse<Page<Content>> allContent(long pageSize, long pageNum, HttpServletRequest request){
//        QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
//        User loginUser = userService.getLoginUser(request);
//        queryWrapper.eq("userId",loginUser.getId());
//        Page<Content> page = contentService.page(new Page<>(pageNum, pageSize), queryWrapper);
//        return ResultUtils.success(page);
//    }
    @GetMapping("/list")
    public BaseResponse<List<Content>> allContent(HttpServletRequest request){
        QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
        User loginUser = userService.getLoginUser(request);
        queryWrapper.eq("userId",loginUser.getId());
        List<Content> contentList = contentMapper.selectList(queryWrapper);
        return ResultUtils.success(contentList);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteContent(@RequestBody DeleteRequest deleteRequest) {
        Long id = deleteRequest.getId();

        if (id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        boolean b = contentService.removeById(id);

        return ResultUtils.success(b);
    }
}
