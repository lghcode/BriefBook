package com.lghcode.briefbook.controller;

import cn.hutool.core.util.ObjectUtil;
import com.lghcode.briefbook.model.DemoUser;
import com.lghcode.briefbook.service.DemoUserService;
import com.lghcode.briefbook.util.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lgh
 * @Date 2020/8/9 16:57
 */
@RestController
@RequestMapping("/demoUser")
public class DemoUserController {

    @Autowired
    private DemoUserService demoUserService;

    /**
     * 新增demo用户
     *
     * @Author lghcode
     * @param demoUser 用户属性
     * @return ResultJson
     * @Date 2020/8/9 18:02
     */
    @PostMapping("/save")
    public ResultJson save(DemoUser demoUser){
        if (ObjectUtil.isEmpty(demoUser)) {
            return ResultJson.error("参数为空");
        }
        demoUserService.insert(demoUser);
        return ResultJson.success("新增成功");
    }


}
