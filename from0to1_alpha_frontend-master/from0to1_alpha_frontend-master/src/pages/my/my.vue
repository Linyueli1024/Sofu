<script setup lang="ts">
import { onLoad, onShow } from "@dcloudio/uni-app";
import {postPersonApi} from "@/services/person"
import { ref } from "vue";

//保存个人信息
const personInfo = ref()

//保存用户头像
const personavatar = ref()

//保存用户昵称
const personnickname = ref()

//保存同问数
const personconcern = ref()

//保存回答数
const personans = ref()

//保存提问数
const personques = ref()

onLoad(async ()=>{
  const res = await postPersonApi({user_id:uni.getStorageSync('user_id')})
  console.log(res);
  personInfo.value = res.data
  personavatar.value = res.data.avatar
  personnickname.value = res.data.nickname
  personconcern.value = res.data.concernNum
  personans.value = res.data.answerNum
  personques.value = res.data.questionNum
})

const gotoMyquestion = ()=>{
  uni.redirectTo({url:'myquestion'})
}
const gotoMyanswer = ()=>{
  uni.redirectTo({url:'myanswer'})
}
const gotoMyconcern = ()=>{
  uni.redirectTo({url:'myconcern'})
}

onShow(()=>{
  const page = getCurrentPages()
  const perpage = page[page.length-1]
  perpage.onLoad()
})

</script>

<template>
  <view class="myInfo">
    <view class="myUserInfo">
      <view class="myAvatar">
        <image
          :src="personavatar"
          mode="scaleToFill"
        />
      </view>
      <view class="myNickname"><label>{{personnickname}}</label></view>
    </view>
    <view class="myInterInfo">
      <view class="mySim" @click="gotoMyconcern">
        <view class="mySimNum">{{personconcern}}</view>
        <view class="mySimTitle">我的同问</view>
      </view>
      <view class="myAns" @click="gotoMyanswer">
        <view class="myAnsNum">{{personans}}</view>
        <view class="myAnsTitle">我的回答</view>
      </view>
      <view class="myQus" @click="gotoMyquestion">
        <view class="myQusNum">{{personques}}</view>
        <view class="myQusTitle">我的提问</view>
      </view>
    </view>
    <view class="myDetail">
      <view class="mess">
        <uni-icons type="chatboxes" size="30" class="icon1"></uni-icons>
        <view class="messTitle">消息</view>
        <uni-icons type="right" size="30" class="icon2"></uni-icons>
      </view>
      <view class="edit">
        <uni-icons type="compose" size="30" class="icon1"></uni-icons>
        <view class="editTitle">编辑资料</view>
        <uni-icons type="right" size="30" class="icon2"></uni-icons>
      </view>
      <view class="feedback">
        <uni-icons type="paperplane" size="30" class="icon1"></uni-icons>
        <view class="feedbackTitle">反馈</view>
        <uni-icons type="right" size="30" class="icon2"></uni-icons>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
//myInfo 用户基本信息 包括头像昵称 收藏同问回答提问
.myInfo{
  .myUserInfo{
    margin-top: 80rpx;
    .myAvatar{
      display: flex;
      justify-content: center;
      image{
        width: 100rpx;
        height: 100rpx;
        border-radius: 50rpx;
      }
    }
    .myNickname{
      display: flex;
      justify-content: center;
      margin-top: 30rpx;
      color: #777;
      font-weight: 800;
    }
  }
  .myInterInfo{
    display: flex;
    justify-content: space-around;
    padding: 0 30rpx;
    margin-top: 60rpx;
      padding-bottom: 50rpx;
    border-bottom: 0.3rpx solid rgba(204, 204, 204, 0.4);
    .myCollect{
      display: inline-block;
      .myCollectNum{
        display: flex;
        justify-content: center;
        font-size: 35rpx;
      }
      .myCollectTitle{
        font-size: 26rpx;
        margin-top: 2rpx;
        color: #696868;
      }

    }
    .mySim{
      display: inline-block;
      .mySimNum{
        display: flex;
        justify-content: center;
        font-size: 35rpx;
      }
      .mySimTitle{
        font-size: 26rpx;
        margin-top: 2rpx;
        color: #696868;
      }
    }
    .myAns{
      display: inline-block;
      .myAnsNum{
        display: flex;
        justify-content: center;
        font-size: 35rpx;
      }
      .myAnsTitle{
        font-size: 26rpx;
        margin-top: 2rpx;
        color: #696868;
      }
    }
    .myQus{
      display: inline-block;
      .myQusNum{
        display: flex;
        justify-content: center;
        font-size: 35rpx;
      }
      .myQusTitle{
        font-size: 26rpx;
        margin-top: 2rpx;
        color: #696868;
      }
    }
  }
  .myDetail{
    padding: 80rpx 80rpx;
    .mess{
      display: flex;
      align-items: center;
      .icon1 .uni-icons{
        font-size: 55rpx !important;
        display: inline-block;
        color: #f66d27 !important;
      }
      .icon2 .uni-icons{
        font-size:45rpx !important;
        display: inline-block;
        color: #aaa !important;
      }
      .messTitle{
        font-size: 30rpx;
        display: inline-block;
        margin-left: 30rpx;
        margin-right: 390rpx;
        // font-weight: 500;
      }
    }
    .edit{
      margin-top: 35rpx;
      display: flex;
      align-items: center;
      .icon1 .uni-icons{
        font-size: 55rpx !important;
        display: inline-block;
        color: #f66d27 !important;
      }
      .icon2 .uni-icons{
        font-size:45rpx !important;
        display: inline-block;
        color: #aaa !important;
      }
      .editTitle{
        font-size: 30rpx;
        display: inline-block;
        margin-left: 30rpx;
        margin-right: 330rpx;
        // font-weight: 500;
      }
    }
    .feedback{
      display: flex;
      align-items: center;
      margin-top: 35rpx;
      .icon1 .uni-icons{
        font-size: 55rpx !important;
        display: inline-block;
        color: #f66d27 !important;
      }
      .icon2 .uni-icons{
        font-size:45rpx !important;
        display: inline-block;
        color: #aaa !important;
      }
      .feedbackTitle{
        font-size: 30rpx;
        display: inline-block;
        margin-left: 30rpx;
        margin-right: 390rpx;
        // font-weight: 500;
      }
    }
  }
}
</style>
