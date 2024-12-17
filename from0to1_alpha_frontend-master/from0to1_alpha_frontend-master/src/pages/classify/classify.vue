<script setup lang="ts">
import { onLoad } from "@dcloudio/uni-app";
import {getClassifyApi} from "@/services/classify"
import { ref } from "vue";

const classifyBars = ref()
const classifyIndex = ref(0)
onLoad(async () => {
  const res = await getClassifyApi()
  console.log(res);
  console.log(res.data);
  classifyBars.value = res.data
})

//存放分类
const classifyBox =ref([
  {index:0, title:'选课'},
  {index:1, title:'转专业'},

])

// //点击选择分类
// const selectClassify = (index:any)=>{
//   arrSelect.value[0] = {value:32}
// }

// //提交用户选择的分类
// const formSubmit = async (profile: RegisterResult) => {
//               // var that:any = this
//   const memberStore = useMemberStore()
//   // let openId = ''
//   wx.getUserInfo({
//     //成功后会返回
//     success:(res)=>{
//       // console.log(res);
//       // 把你的用户信息存到一个变量中方便下面使用
//       let userInfo= res.userInfo
//       //获取openId（需要code来换取）这是用户的唯一标识符
//       // 获取code值
//       wx.login({
//         //成功放回
//         success:(res)=>{
//           // console.log(res);
//           let code=res.code
//           // 通过code换取openId
//           wx.request({
//             url: `https://api.weixin.qq.com/sns/jscode2session?appid=wx95232b48f9fc16e6&secret=378d846539db881eaf704485f6ad37c0&js_code=${code}&grant_type=authorization_code`,
//             success:async (res)=>{
//               console.log(res.data.openid);
//               openId = res.data.openid;
//               // that.setData({openId:res.data.openid})
//               const resRegister = await postRegisterApi({
//                 nickname: nickName,
//                 openid: openId,
//                 avatar: avatarUrl.value,
//                 invitecode: inviteCode
//               })
//               console.log(resRegister.data.user_id);
//               uni.setStorageSync('user_id', resRegister.data.user_id)
//               wx.setStorageSync('state', '1')        
//               uni.reLaunch({ url: '/pages/classify/classify' })
//             }
//           })
//           // console.log(that.data.openId);
          
//         }
//       })
//     }
//   })

// }
//选择分类后触发
const xuanke = ref(1)
const zhuanzhaunye = ref()
const tabClassify = ref()
// function selectClassify(index, e) {
//   console.log(e);
// }

//发布选择分类
const items = ref([{
  value: 1,
  name: '选课'
},
{
  value: 2,
  name: '转专业'
},
{
  value: 3,
  name: '美食'
},
{
  value: 4,
  name: '就业实习'
},
{
  value: 5,
  name: '升学'
},
{
  value: 6,
  name: '出国'
},
{
  value: 7,
  name: '考试'
},
{
  value: 8,
  name: '情感'
},
{
  value: 9,
  name: '社团'
},
{
  value: 10,
  name: '铜盘校区'
},
{
  value: 11,
  name: '兼职'
},
{
  value: 12,
  name: '猫狗'
},
{
  value: 13,
  name: '校园活动'
},
{
  value: 14,
  name: '交友'
},
{
  value: 15,
  name: '游戏'
},
{
  value: 16,
  name: '兴趣'
},
{
  value: 17,
  name: '运动'
},
{
  value: 18,
  name: '竞赛'
},
{
  value: 19,
  name: '科研'
}
])

const values = ref()
const checkboxChange = (e: any) => {
  // var items = this.items,
  values.value = e.detail.value;
  // for (var i = 0, lenI = 6; i < lenI; ++i) {
  // 	const item = items[i].value
  // 	if(values.includes(item.value)){
  // 		this.$set(item,'checked',true)
  // 	}else{
  // 		this.$set(item,'checked',false)
  // 	}
  // }
  console.log(values.value);
}
const finalClassify = ref()
const goToHome = ()=>{
  const resultArray = new Array()
  let Data = new Array
  if (values.value) {
    Data = JSON.parse(JSON.stringify(values.value));
  }
  for (let i = 0; i < Data.length; i++) {
    resultArray[i] = Number(Data[i])
  }
  finalClassify.value = resultArray
  console.log(Data);
}
</script>

<template>
  <!-- <view class="classifyTitle">
    选择你希望了解的领域
  </view>
  <view class="classifySpan">
    获取个性化定制
  </view>
  <view class="classifyBox">
    <view v-for="(item,index) in classifyBars"
    :key="item.index"
    class="box"
    >
      <view class="boxImg"><image
        :src="item.img_link"
        mode="aspectFill"
      /></view>
      <view class="boxTitle"><label>{{item.name}}</label></view>
      <view class="modeClassify" @tap="selectClassify(index, $event)" :class="{'active':xuanke == 0}"></view>
    </view>
  </view> -->
      <view class="classifyTitle" style="margin-top:35rpx;">
    选择你希望了解的领域
  </view>
   <view style="display:flex;justify-content:center;margin-top:60rpx;">
     <view class="classifyBlock" >
    <view class="uni-list">
      <checkbox-group @change="checkboxChange">
        <label class="uni-list-cell uni-list-cell-pd" v-for="item in items" :key="item.value"
          style="margin-right:43rpx;">
          <view style="display:inline-block;">
            <checkbox :value="item.value" :checked="item.checked" />
          </view>
          <view style="display:inline-block;">{{ item.name }}</view>
        </label>
      </checkbox-group>
    </view>
  </view>
   </view>
  <button form-type="submit" @click="goToHome" class="next" >进入社区</button>
</template>


<style scoped lang="scss">
  .classifyTitle{
    font-size: 48rpx;
    font-family: "PingFang SC", BlinkMacSystemFont, -apple-system;
    text-align: center;
    color: rgb(51, 51, 51);
    font-weight: bold;
    white-space: nowrap;
    letter-spacing: 2rpx;
    word-break: normal;
    margin-top: 20rpx;
  }
  .classifySpan{
    font-size: 34rpx;
    font-family: "PingFang SC", BlinkMacSystemFont, -apple-system;
    text-align: center;
    color: rgb(127, 127, 127);
    white-space: nowrap;
    letter-spacing: 2rpx;
    word-break: normal;
    margin-top: 10rpx;
  }
  .classifyBox{
    padding: 20rpx 50rpx;
    .box{
      display: inline-block;
      width: 189rpx;
      height: 222rpx;
      // background-color: pink;
      margin: 13rpx;
      border-radius: 20rpx;
      padding: 20rpx 0;
      border: 1rpx solid rgb(215, 215, 215);
      position: relative;
      .boxImg{
        display: flex;
        justify-content: center;
        image{
          width: 165rpx;
        height: 150rpx;
        border-radius: 20rpx;
        }
        
      }
      .boxTitle{
        display: flex;
        justify-content: center;
        margin-top: 20rpx;
        label{
          font-size: 32rpx;
    font-family: "PingFang SC", BlinkMacSystemFont, -apple-system;
    text-align: center;
    color: rgb(51, 51, 51);
    white-space: pre-wrap;
    letter-spacing: 0px;
    word-break: break-word;
    line-height: 44rpx;
        }
      }
    }

  }
  .next{
  margin-top: 40rpx;
  width: 570rpx !important;
  height: 80rpx !important;
  border-radius: 40rpx !important;
  background-color: #f66d27;
  color: #fff;
}
.modeClassify{
  width: 189rpx;
  height:265rpx;
  background-color: rgba(0,0,0,0);
  position: absolute;
  top: 0rpx;
  border-radius: 20rpx;
}
.active{
  width: 189rpx;
  height:265rpx;
  background-color: rgba(0,0,0,0.1);
  position: absolute;
  top: 0rpx;
  border-radius: 20rpx;
}

.classifyBlock {
  padding: 20rpx;
  border-radius: 20rpx;
}

.confirmClassify {
  display: flex;
  justify-content: flex-end;

  button {
    width: 110rpx;
    height: 64rpx;
    font-size: 24rpx;
    margin: 5rpx 27rpx;
    margin-right: 0rpx;
    background-color: #f66d27;
    color: #fff;
    line-height: 64rpx;
    border-radius: 64rpx;
  }
}
</style>