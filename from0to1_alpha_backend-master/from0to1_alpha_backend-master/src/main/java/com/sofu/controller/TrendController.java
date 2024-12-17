package com.sofu.controller;

import com.sofu.pojo.Question;
import com.sofu.service.QuestionService;
import com.sofu.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * ClassName: TrendController
 * Author：Dee
 * Description：<热榜模块>
 */
@RestController
public class TrendController {

    @Autowired
    private QuestionService questionService;

    /**
     * @Description: 获取热榜
     */
    @GetMapping("/trendtopic")
    public JSONResult trendtopic() throws Exception {
        List<HashMap<String, Object>> data = new ArrayList<>();
        List<Question> questions = new ArrayList<Question>();
        questions = questionService.selectAllQuestion();
        for (int i = 0; i < 50; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("q_id", questions.get(i).getQId());
            map.put("q_title", questions.get(i).getQTitle());
            map.put("hotdegree", questions.get(i).getHotDegree());
            data.add(map);
        }
        return JSONResult.successMsg("获取热榜成功",data);
    }

}
