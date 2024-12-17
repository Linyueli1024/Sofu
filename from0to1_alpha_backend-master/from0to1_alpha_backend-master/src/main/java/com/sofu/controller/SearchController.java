package com.sofu.controller;

import com.sofu.pojo.Answer;
import com.sofu.pojo.Question;
import com.sofu.pojo.User;
import com.sofu.service.AnswerService;
import com.sofu.service.QuestionService;
import com.sofu.service.UserService;
import com.sofu.utils.JSONResult;
import com.sofu.utils.SearchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: SearchController
 * Author：Dee
 * Description：<搜索模块>
 */
@RestController
public class SearchController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    AnswerService answerService;

    @Autowired
    UserService userService;

    /**
     * @Description: 用户搜索
     */
    @PostMapping("/search")
    public JSONResult search(@RequestBody Map<String, String> keyword) throws Exception {
        String key = keyword.get("keyword");
        List<Question> questions = new ArrayList<>();
        List<HashMap<String, Object>> data = new ArrayList<>();
        //中间没有空格
        if(!key.contains(" ")){
            //解决通配符问题
            key = SearchUtils.specialStr(key);
            //支持不连续关键字搜索
            key = SearchUtils.specialStrKeyword(key);
            questions = questionService.queryQuestion(key);
        }
        //中间有空格
        else{
            key = SearchUtils.conactString(key);
            questions = questionService.queryByMultiKeywords(key);
        }
        if (questions.size() == 0) {
            return JSONResult.successMsg("暂无结果");
        }
        for(Question question: questions){
            Integer qId = question.getQId();
            List<Answer> answers = answerService.queryByqId(qId);
            System.out.println(answers);
            System.out.println("---");
            //3.对每条问题查询喜欢量最高的回答
            if(answers.size()!=0){
                Answer bestAnswer = getBestAnswer(answers);
                System.out.println(bestAnswer);
                System.out.println("---");
                //对于单个问题，封装问题和对应的一个最好回答
                HashMap<String, Object> simpleElement = new HashMap<String, Object>();
                simpleElement.put("title", question.getQTitle());
                simpleElement.put("q_id", question.getQId());
                HashMap<String, String> ansMap = new HashMap<>();
                //根据回答对象的userId查询回答用户的头像和昵称
                User user = userService.selectUserById(bestAnswer.getUserId());
                ansMap.put("avatar", user.getAvatar());
                ansMap.put("nickname", user.getNickname());
                ansMap.put("info", bestAnswer.getAnsInfo());
                ansMap.put("img_link", bestAnswer.getImgLink());
                simpleElement.put("ans", ansMap);
                simpleElement.put("concern", question.getConcernNum());
                simpleElement.put("ansnum", question.getAnswerNum());
                //4.封装单条数据
                data.add(simpleElement);
            }

        }
        return JSONResult.successMsg("成功获取搜索结果", data);
    }
    /**
     * @Description: 获取Answer列表中喜欢数最高的回答--bestAnswer
     */
    public Answer getBestAnswer(List<Answer> answers){
        //该问题没有回答
        int maxLike = 0;
        int index = 0;//bestAnswer的下标
        for(int i = 0; i<answers.size(); i++){
            if(answers.get(i).getLikeNum()>maxLike){
                maxLike = answers.get(i).getLikeNum();
                index = i;
            }
        }
        return answers.get(index);
    }
}
