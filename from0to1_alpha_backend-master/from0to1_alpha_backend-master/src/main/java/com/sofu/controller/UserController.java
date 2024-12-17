package com.sofu.controller;

import com.sofu.pojo.*;
import com.sofu.pojo.vo.AnswerVo;
import com.sofu.service.AnswerService;
import com.sofu.service.MessageService;
import com.sofu.service.QuestionService;
import com.sofu.service.UserService;
import com.sofu.utils.JSONResult;
import com.sofu.utils.RC4InvitationCode;
import org.jnosql.artemis.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ClassName: UserController
 * Author：Dee
 * Description：<用户模块>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AnswerService answerService;
    @Autowired
    private QuestionService questionService;
    /**
     * @Description: 用户注册
     */
    @PostMapping("/register")
    public JSONResult register(@RequestBody Map<String, String> user) throws Exception {
        HashMap<String, Integer> data = new HashMap<String, Integer>();
        // 1. 判断昵称必须不为空
        if (StringUtils.isBlank(user.get("nickname"))) {
            return JSONResult.errorMsg("昵称不能为空");
        }
        // 2.判断邀请码必须不为空
        if (StringUtils.isBlank(user.get("invitecode"))) {
            return JSONResult.errorMsg("邀请码不能为空");
        }
        // 3. 判断昵称是否存在
        boolean nicknameIsExist = userService.queryNicknameIsExist(user.get("nickname"));
        // 4.判断wx_openid是否存在
        boolean openidIsExist = userService.queryOpenIdIsExist(user.get("openid"));
        // 5. 保存用户注册信息
        if(nicknameIsExist){
            return JSONResult.errorMsg("昵称已存在");
        }
        if(openidIsExist){
            return JSONResult.errorMsg("请勿重复注册！");
        }
        User user1 = new User();
        user1.setNickname(user.get("nickname"));
        user1.setAvatar(user.get("avatar"));
        user1.setWxOpenId(user.get("openid"));
        user1.setState(1);
        String inviteCode = RC4InvitationCode.m4Rc4Encrypt(); //随机生成邀请码
        boolean inviteCodeIsExist = userService.queryInviteCodeIsExist(inviteCode);
        while (inviteCodeIsExist) {
            inviteCode = RC4InvitationCode.m4Rc4Encrypt();
            inviteCodeIsExist = userService.queryInviteCodeIsExist(inviteCode);
        }
        user1.setInviteCode(inviteCode);
        User result = userService.selectUserByInviteCode(user.get("invitecode"));
        if (result == null) {
            return JSONResult.errorMsg("邀请码无效");
        }
        user1.setInvitedBy(result.getUserId());
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        date = formatter.parse(formatter.format(date));
        user1.setRegTime(date);
        user1.setLoginTime(date);
        userService.saveUser(user1);
        User self = userService.selectUserByInviteCode(inviteCode);
        data.put("user_id",self.getUserId());
        return JSONResult.successMsg("注册成功", data);
    }

    /**
     * @Description: 用户选择分类
     */
    @PostMapping("/choose")
    public JSONResult choose(@RequestBody Map<String, Object> userTaste) throws Exception {
        //判断用户至少选择了一个分类
        List<Integer> classify = (List<Integer>) userTaste.get("classify_id");
        if (classify.size() < 3) {
            return JSONResult.errorMsg("请至少选择三个分类");
        }
        int userId = (int) userTaste.get("user_id");
        UserTaste userTaste1 = new UserTaste();
        for (int i = 0; i < classify.size(); i++) {
            userTaste1.setUserId(userId);
            userTaste1.setClassifyId(classify.get(i));
            userTaste1.setEngagement(0);
            userService.saveUserTaste(userTaste1);
        }
        return JSONResult.successMsg("用户选择分类成功");
    }

    /**
     * @Description: 获取用户信息
     */
    @PostMapping("/show")
    public JSONResult show(@RequestBody Map<String, Integer> user) throws Exception {
        int userId = user.get("user_id");
        User user1 = userService.selectUserById(userId);
        Map <String, String> data = new HashMap<>();
        data.put("avatar", user1.getAvatar());
        data.put("nickname", user1.getNickname());
        return JSONResult.successMsg("成功", data);
    }

    /**
     * @Description: 编辑用户信息
     */
    @PutMapping("/edit")
    public JSONResult edit(@RequestBody Map<String, Object> user) throws Exception {
        int userId = (int) user.get("user_id");
        String avatar = (String) user.get("avatar");
        String nickname = (String) user.get("nickname");
        User result = userService.selectUserById(userId);
        if (nickname.equals(result.getNickname())) {
            return JSONResult.errorMsg("未进行修改");
        }
        if (StringUtils.isBlank(nickname)) {
            return JSONResult.errorMsg("昵称不能为空");
        }
        //过滤掉空格
        nickname = nickname.replaceAll(" +","");
        userService.updateUser(userId, avatar, nickname);
        return JSONResult.successMsg("编辑成功");
    }

    /**
     * @Description: 获取用户回答
     */
    @PostMapping("/answer")
    public JSONResult answer(@RequestBody Map<String, Integer> user) throws Exception {
        int userId = (int) user.get("user_id");
        List<HashMap<String, Object>> data = new ArrayList<>();
        List<AnswerVo> answerVos = new ArrayList<AnswerVo>();
        answerVos = userService.selectAnswerVoList(userId);
        if (answerVos.size() == 0) {
            return JSONResult.successMsg("获取成功");
        }
        for (int i = 0; i < answerVos.size(); i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("q_title", answerVos.get(i).getQuestion().getQTitle());
            map.put("q_id", answerVos.get(i).getQId());
            data.add(map);
        }
        return JSONResult.successMsg("获取成功",data);
    }

    /**
     * @Description: 获取用户消息
     */
    @PostMapping("/message")
    public JSONResult Message(@RequestBody Map<String,Integer> user){
        //1.获取user_id
        Integer user_id = user.get("user_id");
        List<HashMap<String, Object>> data = new ArrayList<>();
        //2. 查询对应的消息
        List<Message> messages = messageService.queryById(user_id);
        for (Message message : messages) {
            HashMap <String,Object> mesEle = new HashMap<>();
            Integer id = message.getInfo();
            //查询回答表，获取消息内容
            Answer answer = answerService.queryByAnsId(id);
            String info = answer.getAnsInfo();
            Integer qId = answer.getQId();
            Integer read = message.getReadstate();
            String readState = "";
            if(read == 0) {
                readState = "unread";
            }else{
                readState = "readed";
            }
            Date date = answer.getPostTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String answerTime = sdf.format(date);
            Integer ansId = answer.getAnsId();
            Integer userId = answer.getUserId();
            User user1 = userService.selectUserById(userId);
            String nickname = user1.getNickname();
            String avatar = user1.getAvatar();
            mesEle.put("nickname",nickname);
            mesEle.put("avatar",avatar);
            mesEle.put("info",info);
            mesEle.put("qId",qId);
            mesEle.put("ansId",ansId);
            mesEle.put("readstate",readState);
            mesEle.put("answerTime",answerTime);
            data.add(mesEle);
        }
        return new JSONResult(200,"成功获取用户信息", data);
    }

    /**
     * @Description: 获取用户提问
     */
    @PostMapping("/question")
    public JSONResult question(@RequestBody Map<String, Integer> user) throws Exception {
        int userId = (int) user.get("user_id");
        List<Question> questions = userService.selectQuestion(userId);
        List<HashMap<String, Object>> data = new ArrayList<>();
        if (questions.size() == 0) {
            return JSONResult.successMsg("获取成功");
        }
        for (int i = 0; i < questions.size(); i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("q_title", questions.get(i).getQTitle());
            map.put("q_id", questions.get(i).getQId());
            data.add(map);
        }
        return JSONResult.successMsg("获取成功", data);
    }

    /**
     * @Description: 问题重新编辑
     */
    @PutMapping("/question/edit")
    public JSONResult questionEdit(@RequestBody Map<String, Object> question) throws Exception {
        int qId = (int) question.get("q_id");
        String qTitle = (String) question.get("q_title");
        String qInfo = (String) question.get("q_info");
        List<String> img = (List<String>) question.get("img_link");
        String imgLink = "";
        if (img.size() != 0) {
            for (int i = 0; i < img.size() - 1; i++) {
                imgLink += img.get(i);
                imgLink += ",";
            }
            imgLink += img.get(img.size() - 1);
        }
        userService.updateQuestion(qId, qTitle, qInfo, imgLink);
        return JSONResult.successMsg("问题编辑成功");
    }
    /**
     * @Description: 我的页面接口，获取所有页面信息
     */
    @PostMapping("/person")
    public JSONResult getPerson(@RequestBody Map<String, String> info){
        Map <String, Object> data = new HashMap<>();
        String userId = info.get("user_id");
        List<Question> questions = userService.selectQuestion(Integer.parseInt(userId));
        int questionNum = questions.size();
        List<Answer> answers = answerService.queryByUserId(Integer.parseInt(userId));
        System.out.println("回答列表："+answers);
        int ansNum = answers.size();
        List<Concern> concerns = userService.selectByUserId(Integer.parseInt(userId));
        int concernNum = concerns.size();
        User user1 = userService.selectUserById(Integer.parseInt(userId));
        data.put("avatar", user1.getAvatar());
        data.put("nickname", user1.getNickname());
        data.put("answerNum",ansNum);
        data.put("concernNum", concernNum);
        data.put("questionNum",questionNum);
        return new JSONResult(200, "成功获取我的页面信息", data);

    }

    /**
     * @Description: 我的同问接口，获取我的同问问题列表
     */
    @PostMapping("/concern")
    public JSONResult getConcern(@RequestBody Map<String,String> info){
        Integer userId = Integer.parseInt(info.get("user_id"));
        //1.根据userId查询同问关系表
        List<Concern> concerns = userService.selectByUserId(userId);
        List<HashMap<String, Object>> data = new ArrayList<>();
        System.out.println(concerns);
        if (concerns.size() != 0) {
            for (Concern concern : concerns) {
                HashMap<String, Object> concernEle = new HashMap<>();
                //1)根据concern表中的q_id查询q_title
                Integer qId = concern.getQId();
                System.out.println(qId);
                Question question = questionService.selectQuestionByQid(qId);
                System.out.println(question);
                concernEle.put("q_title",question.getQTitle());
                concernEle.put("q_id",question.getQId());
                //2)根据concern表中的userId查询头像和昵称
                Integer userId1 = concern.getUserId();
                User user = userService.selectUserById(userId1);
                concernEle.put("avatar",user.getAvatar());
                concernEle.put("nickname",user.getNickname());
                data.add(concernEle);
            }
        }
        return new JSONResult(200,"成功获取我的同问信息",data);
    }
    /**
     * @Description: 问题同问
     */
    @PostMapping("/addConcern")
    public JSONResult addConcern(@RequestBody Map<String,Integer> info){
        Integer userId =info.get("user_id");
        Integer qId = info.get("q_id");
        //1.concern表插入数据
        Concern concern = new Concern();
        concern.setUserId(userId);
        concern.setQId(qId);
        Date date = new Date();
        concern.setAddTime(date);
        userService.addConcern(concern);
        //2.该问题（qId）的concernNum++
        questionService.addConcernNum(qId);
        return new JSONResult(200,"同问成功",null);

    }



}
