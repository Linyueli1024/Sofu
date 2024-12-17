package com.sofu.controller;

import com.sofu.pojo.Answer;
import com.sofu.pojo.Question;
import com.sofu.pojo.User;
import com.sofu.pojo.vo.AnswerVo;
import com.sofu.pojo.vo.QuestionVo;
import com.sofu.service.AnswerService;
import com.sofu.service.QuestionService;
import com.sofu.service.UserService;
import com.sofu.utils.JSONResult;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ClassName: DetailController
 * Author：Dee
 * Description：<问题详情页模块>
 */
@RestController
public class DetailController {

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Autowired
    UserService userService;

    @PostMapping("/detail")
    public JSONResult detail(@RequestBody Map<String,Integer> info){
        HashMap<String,Object> data = new HashMap<>();
        Integer qId = info.get("q_id");
        Question question = questionService.selectQuestionByQid(qId);
        data.put("q_title",question.getQTitle());
        data.put("concern_num",question.getConcernNum());
        data.put("answer_num",question.getAnswerNum());
        List<HashMap<String, Object>> ansData = new ArrayList<>();
        List<Answer> answers = answerService.queryByqId(qId);
        if(answers.size()!=0){
            for(int i = 0; i<answers.size(); i++){
                HashMap<String,Object> ansEle = new HashMap<>();
                ansEle.put("img_link",answers.get(i).getImgLink());
                ansEle.put("ans_info",answers.get(i).getAnsInfo());
                Date postTime = answers.get(i).getPostTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = sdf.format(postTime);
                ansEle.put("post_time",format);
                //根据answer_id查询发布该回答的用户id
                Integer userId = answers.get(i).getUserId();
                User user = userService.selectUserById(userId);
                ansEle.put("nickname",user.getNickname());
                ansEle.put("avatar",user.getAvatar());
                ansEle.put("ans_id",answers.get(i).getAnsId());
                ansData.add(ansEle);
            }
            data.put("ans",ansData);
        }
        return new JSONResult(200,"成功获取详情信息",data);
    }

}
