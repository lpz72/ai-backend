package org.lpz.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lpz.ai.mapper.ContentMapper;
import org.lpz.ai.model.domain.Content;
import org.lpz.ai.service.ContentService;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【content(识别记录表)】的数据库操作Service实现
* @createDate 2025-02-19 18:43:09
*/
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content>
    implements ContentService {

}




