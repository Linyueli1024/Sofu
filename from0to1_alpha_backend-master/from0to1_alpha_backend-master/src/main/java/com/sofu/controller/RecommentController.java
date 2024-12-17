package com.sofu.controller;

import com.sofu.pojo.Answer;
import com.sofu.pojo.Question;
import com.sofu.pojo.User;
import com.sofu.pojo.UserTaste;
import com.sofu.service.AnswerService;
import com.sofu.service.QuestionService;
import com.sofu.service.UserService;
import com.sofu.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * ClassName: RecommentController
 * Author:Bellion
 * Description：<实现问题推荐页面>
 */

@RestController
@RequestMapping("/recommend")
public class RecommentController {

    @Autowired
    QuestionService questionService;
    @Autowired
    AnswerService answerService;
    @Autowired
    UserService userService;

    /**
     * @Description: （主函数）获取用户选择分类后的推荐内容列表
     */
    @PostMapping("/requestOne")
    public JSONResult getRecommendOne(@RequestBody Map<String,Integer> info){
        List<HashMap<String, Object>> data = new ArrayList<>();
        //1.用户新选分类只能单选
        Integer tag = info.get("tag");
        List<Question> questions = new ArrayList<>();
        String key = "["+tag+"]";
        int num = 10;
        List<Question> questionsRes = questionService.selectByTag(key, num);
        for(Question tempQuestion : questionsRes){
            questions.add(tempQuestion);
        }
        //对questions列表根据热度值进行排序
        Comparator<Question> hotdegreeComparator = Comparator.comparing(Question::getHotDegree).reversed();
        questions.sort(hotdegreeComparator);
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
        return JSONResult.successMsg("成功获取推荐列表", data);
    }

    /**
     * @Description: 获取用户根据自己喜好获得的推荐列表
     */
    @PostMapping("/requestAll")
    public JSONResult getRecommendAll(@RequestBody Map<String,Integer> info){
        List<HashMap<String, Object>> data = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        // 2.初始状态下拉去推荐列表？根据用户本身的兴趣分类
        String userId = info.get("userid").toString();
        System.out.println(userId);
        //查询该user的分类兴趣 sofu_usertaste表 Classify = 1,2..
        //1, 4, 7, 10...size = 3
        List<Integer> classify = userService.queryClassify(userId);
        if(classify.size()==0){
            classify.add(1);
            classify.add(5);
            classify.add(7);
            classify.add(3);
            classify.add(2);
        }
        int num = 10;
        for(Integer integer : classify){
            String tag = "["+integer+"]";
            System.out.println(tag);
            List<Question> questionsRes = questionService.selectByTag(tag, num);
            System.out.println(questionsRes);
            System.out.println("----");
            for(Question tempQuestion : questionsRes){
                questions.add(tempQuestion);
            }
        }
        //对questions列表根据热度值进行排序
        Comparator<Question> hotdegreeComparator = Comparator.comparing(Question::getHotDegree).reversed();
        questions.sort(hotdegreeComparator);
        System.out.println(questions);

     /*   int size = classify.length()/3;
        int index = 1;
        int num = 1;//查询条数
        for(int i = 0; i< size; i++){
            int key = Integer.parseInt(String.valueOf(classify.charAt(index)));
            List<Question> questionsRes = questionService.selectByTag(String.valueOf(key), num);
            //将单个分类的查询结构添加到question结果列表
            for(Question tempQuestion : questionsRes){
                questions.add(tempQuestion);
            }
            index+=3;
        }*/
        System.out.println(questions);
        for(Question question: questions) {
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
                simpleElement.put("hotdegree",question.getHotDegree());
                //4.封装单条数据
                data.add(simpleElement);
            }

        }
        return JSONResult.successMsg("成功获取根据用户兴趣分类获取的推荐列表",data);
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
