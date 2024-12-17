package com.sofu.controller;

import com.sofu.pojo.Answer;
import com.sofu.service.AnswerService;
import com.sofu.service.QuestionService;
import com.sofu.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * ClassName: AnswerController
 * Author:Bellion
 * Description：<answer类控制器>
 */

@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @Autowired
    QuestionService questionService;

    /**
     * @Description: 写回答
     */
    @PostMapping("/add")
    public JSONResult addAns(@RequestBody Map<String, String> answer){
        //1.封装answer对象
        Answer answer1 = new Answer();
        answer1.setQId(Integer.parseInt(answer.get("q_id")));
        answer1.setAnsInfo(answer.get("ansInfo"));
        answer1.setImgLink(answer.get("img_link"));
        answer1.setUserId(Integer.parseInt(answer.get("user_id")));
        //1.1获取当前时间
        Date date = new Date();
        answer1.setPostTime(date);
        //2.插入answer表
        answerService.addAns(answer1);
        //3.TODO 该回答对于的问题对象 ，其属性ansnum++
        questionService.andAnswerNum(Integer.parseInt(answer.get("q_id")));
        return  JSONResult.successMsg("写回答操作成功");
    }

    /**
     * @Description: 回答点赞
     */
    @PostMapping("/like")
    public JSONResult like(@RequestBody Map<String, Integer> answer){
        int ansId = answer.get("ans_id");
        answerService.addLikeNum(ansId);
        return JSONResult.successMsg("点赞成功");
    }

}
