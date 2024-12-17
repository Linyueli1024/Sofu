<template>
    <view class="navbar">
        <view class="navbar-fixed">
            <!-- 状态栏小程序撑起高度 -->
            <view :style="{height:statusBarHeight+'px'}"></view>
            <view class="navbar-content" :style="{height:navBarHeight+'px',width:windowWidth+'px'}">
                <view class="navbar-myquestionback_icon">
                    <uni-icons type="back" size="22" color="#999" @click="gotohome"></uni-icons>
                </view>
                <view class="myquestionTitle">我的提问</view>
            </view>
        </view>
        <!-- 需要添加占位符高度  状态栏高度+导航栏高度（否则下面tab会塌陷）-->
        <view :style="{height: statusBarHeight+navBarHeight+'px'}"></view>
    </view>
  <view class="myquestion">
    <view class="tabMyquestion">
      共有{{myQuestionLength}}个提问
    </view>
    <view class="myQuestionList">
      <view class="oneList" v-for="(item,index) in myQuestionList" :key="index"  @tap="goToDetail(item.q_id)">
        <view class="myQuestionListTitle">
          {{item.q_title}}
        </view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { onLoad } from "@dcloudio/uni-app";
import {postMyQuestionApi} from "@/services/myquestion"
import { ref } from "vue";

let statusBarHeight = ref(20)
let navBarHeight = ref(45)
const windowWidth = ref(375)
const val = ref('')

//保存我的问题列表
const myQuestionList = ref()

//保存问题数
const myQuestionLength = ref()

onLoad(async ()=>{
  //隐藏返回主页
  wx.hideHomeButton({
    complete:()=>{
      console.log(9999);
      
    }
  })

  // 在 created 生命周期钩子中执行一些操作
    //  获取手机系统信息
    const info = uni.getSystemInfoSync()
    // 设置状态栏高度（H5顶部无状态栏小程序有状态栏需要撑起高度）
    statusBarHeight.value = info.statusBarHeight
    windowWidth.value = info.windowWidth
    // 除了h5 app mp-alipay的情况下执行
    // #ifndef H5 || APP-PLUS || MP-ALIPAY
    // 获取胶囊的位置
    const menuButtonInfo = uni.getMenuButtonBoundingClientRect()
    console.log(menuButtonInfo);
    // (胶囊底部高度 - 状态栏的高度) + (胶囊顶部高度 - 状态栏内的高度) = 导航栏的高度
    // navBarHeight.value = (menuButtonInfo.bottom - info.statusBarHeight) + (menuButtonInfo.top - info.statusBarHeight)
    windowWidth.value = menuButtonInfo.left
    // #endif

  const res = await postMyQuestionApi({user_id:uni.getStorageSync('user_id')})
  console.log(res);
  myQuestionList.value = res.data
  myQuestionLength.value = res.data.length
  
})

//点击返回去我的主页
const gotohome = ()=>{
  uni.switchTab({url: 'my'})
}

//跳转详情
const goToDetail = (id:number)=>{
  uni.navigateTo({url:`../../pages/ques_detail/quesDetail?id=${id}`})
}
</script>

<style scoped lang="scss">
  .myquestion{
    width: 720rpx;
    height: 45rpx;
    background-color: rgb(242, 237, 237);
    margin-top: 20rpx;
    margin-left: 15rpx;
    .tabMyquestion{
      font-size: 23rpx;
      color: #777;
      padding: 6rpx;
    }
    .oneList{
      width: 700rpx;
      height: 90rpx;
      display: flex;
      align-items: center;
      padding: 5rpx;
      padding-left: 20rpx;
      .myQuestionListTitle{
        font-size: 32rpx;
        font-weight: 500;
      }
    }
  }
      .navbar {
        .navbar-fixed {
            position: fixed;
            top: 0;
            left: 0;
            z-index: 99;
            width: 100%;
            background-color:#f6f6f6;
            .navbar-content {
                display: flex;
                align-items: center;
                padding: 0 15px;
                height: 45px;
                box-sizing: border-box;

                .navbar-myquestionback {
                    display: flex;
                    align-items: center;
                    padding: 0 10px;
                    width: 100%;
                    height: 30px;
                    border-radius: 30px;
                    background-color: #fff;
                    box-shadow: 6rpx 6rpx 6rpx rgba(0, 0, 0, 0.05),0 0 6rpx rgba(0,0,0,0.05), 0 0 6rpx rgba(0,0,0,0.05);

                    .navbar-myquestionback_icon {
                        // width: 10px;
                        // height: 10px;
                        margin-right: 10px;
                    }
                }
                .myquestionTitle{
                  color: #f66d27;
                }
            }

        }

    }
</style>