package com.sofu.controller;

import com.sofu.pojo.Classify;
import com.sofu.service.ClassifyService;
import com.sofu.service.UserService;
import com.sofu.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ClassifyController
 * Author：Dee
 * Description：<分类模块>
 */
@RestController
public class ClassifyController {

    @Autowired
   ClassifyService classifyService;

    /**
     * @Description: 获取兴趣分类
     */
    @GetMapping("/classify")
    public JSONResult classify() throws Exception {
        List<Classify> c = classifyService.selectClassify();
        List<HashMap<String, String>> data = new ArrayList<>();
        for(int i = 0; i<c.size(); i++){
            HashMap<String, String> element = new HashMap<>();
            element.put("name",c.get(i).getName());
            element.put("img_link",c.get(i).getImgLink());
            data.add(element);
        }
        return JSONResult.successMsg("gain classify success",data);
    }

}
