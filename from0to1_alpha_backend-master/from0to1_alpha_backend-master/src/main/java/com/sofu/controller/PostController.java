package com.sofu.controller;

import com.sofu.dao.QuestionDao;
import com.sofu.pojo.Idea;
import com.sofu.pojo.Question;
import com.sofu.service.ClassifyService;
import com.sofu.service.IdeaService;
import com.sofu.service.QuestionService;
import com.sofu.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Id;
import java.util.*;

/**
 * ClassName: PostController
 * Author:Bellion
 * Description：<发布模块控制器>
 */
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
     IdeaService ideaService;

    @Autowired
    QuestionService questionService;

    @Autowired
    ClassifyService classifyService;

    /**
     * @Description: 发布想说
     */
    @PostMapping("/idea")
    public JSONResult addIdea(@RequestBody Map<String, Object> idea){
        Idea ideaNew = new Idea();
        ideaNew.setIdeaTitle(idea.get("ideatitle").toString());
        //可能上传多个图片，图片链接为数组
        ideaNew.setImgLink(idea.get("imglink").toString());
        //set classifyId,将[1,3,5]处理成[1][3][5]方便查询
        List<Integer> tags = (List<Integer>) idea.get("classifyid");
        String classify = processTagStr(tags);
        ideaNew.setClassifyId(classify);
        ideaNew.setIdeaInfo(idea.get("ideainfo").toString());
        ideaNew.setUserId((Integer) idea.get("userid"));
        ideaNew.setPostTime(new Date());
        ideaService.addIdea(ideaNew);
        return JSONResult.successMsg("发布成功");
    }

    /**
     * @Description: 发布问题
     */
    @PostMapping("/question")
    public JSONResult addQuestion(@RequestBody Map<String, Object> question){
        Question question1 = new Question();
        question1.setQTitle(question.get("qtitle").toString());
        if(question.get("imglink")!=null){
            question1.setImgLink(question.get("imglink").toString());
        }
        question1.setClassifyId(processTagStr((List<Integer>) question.get("classifyid")));

        //connectNum++
        List<Integer> classifys =(List<Integer>) question.get("classifyid");
        for(int i = 0; i<classifys.size(); i++){
            classifyService.addConnectNum(classifys.get(i));
        }
        question1.setQInfo(question.get("questioninfo").toString());
        question1.setUserId((Integer)question.get("userid"));
        question1.setPostTime(new Date());
        //初始热度值为0
        question1.setHotDegree(0);
        questionService.insertQuestion(question1);
        List<String> data = new ArrayList<>();
        return JSONResult.successMsg("发布成功",data);
    }

    /**
     * @Description: 分类标签字符处理：将[1,3,5]处理成[1][3][5]
     */
    public String processTagStr(List<Integer> tags){
        String classify = "";
        for(int i = 0; i<tags.size(); i++){
            int value = tags.get(i).intValue();
            String temp = "[" +value+"]";
            classify+=temp;
        }
        return classify;
    }

}
