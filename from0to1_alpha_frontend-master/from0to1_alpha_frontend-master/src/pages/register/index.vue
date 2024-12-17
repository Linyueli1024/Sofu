<template>
<view class="container">
  <view>
    <view class="title"><image src="../../static/images/register_title.png" mode="widthFix"/></view>
  </view>
  <view class="edit-avatar">
    <button class="avatar-wrapper" open-type="chooseAvatar" @chooseavatar="onChooseAvatar">
   <image :src="avatarUrl" mode="widthFix" id="pic" class="avatar"/>
  </button> 
  <image src="../../static/images/icon/register_edit.png" mode="widthFix" id="edit"/>
  </view>
  <view class="info">
    <form @submit="formSubmit">
      <view class="nickname">
      <input type="nickname" class="weui-input" placeholder="请输入昵称"  id="nickname" name="nickname" :value="nickName" @blur="bindblur_nickname" @input="bindinput"/>
      <!-- <image src="../../static/images/icon/register_user.png" mode="widthFix"/> -->
    </view>
    <view class="invite">
      <input type="text" id="invite" placeholder="请输入邀请码" name="invite" @blur="bindblur_invite"/>
      <!-- <image src="../../static/images/icon/register_invite.png" mode="widthFix"/> -->
    </view>
    <button form-type="submit" @click="goToSelect" class="next" >Next</button>
  </form>
    </view>
      <uni-popup ref="message" @change="change">
      <view class="popup-content" :class="{ 'popup-height': type === 'left' || type === 'right' }"><text class="text">{{
        messageText }}</text></view>
    </uni-popup>
</view>
</template>

<script setup lang="ts">
import { onLoad } from '@dcloudio/uni-app'
import { ref } from 'vue'
import type { RegisterResult } from '@/types/member'
import { useMemberStore } from '@/stores'
import { postRegisterApi } from '@/services/register'

// #ifdef MP-WEIXIN
// 获取 code 登录凭证
let code = ''
onLoad(async () => {
  const res = await wx.login()
  // console.log(res);
  code = res.code
  wx.hideHomeButton({
    complete:()=>{
      console.log(9999);
      
    }
  })

  console.log(uni.getStorageSync('user_id'));
  
  if(uni.getStorageSync('user_id') != ""){
    uni.reLaunch({ url: '/pages/index/index' })
  }
})

//响应式用户头像
const avatarUrl = ref('')
avatarUrl.value = '../../static/images/avatar.png'
const onChooseAvatar = (e:any) => {
  avatarUrl.value = e.detail.avatarUrl
}


// //用户昵称
// const nickName = ref('')
// const bindblur = (e:any) => {
//   nickName.value = e.target.value
//   console.log(nickName.value);
// }
let nickName = ''
const bindblur_nickname = (e:any) => {
  nickName = e.target.value
}

let inviteCode = ''
const bindblur_invite = (e:any) => {
  inviteCode = e.target.value
}
let openId = ''

//提示框类型
const msgType = ref('')
//提示消息内容
const messageText = ref('')
//消息弹出框
const message = ref(null)
//点击next按钮提交注册信息
const formSubmit = async (profile: RegisterResult) => {
              // var that:any = this
  const memberStore = useMemberStore()
  // let openId = ''
  wx.getUserInfo({
    //成功后会返回
    success:(res)=>{
      // console.log(res);
      // 把你的用户信息存到一个变量中方便下面使用
      let userInfo= res.userInfo
      //获取openId（需要code来换取）这是用户的唯一标识符
      // 获取code值
      wx.login({
        //成功放回
        success:(res)=>{
          // console.log(res);
          let code=res.code
          // 通过code换取openId
          wx.request({
            url: `https://api.weixin.qq.com/sns/jscode2session?appid=wx95232b48f9fc16e6&secret=378d846539db881eaf704485f6ad37c0&js_code=${code}&grant_type=authorization_code`,
            success:async (res)=>{
              console.log(res.data.openid);
              openId = res.data.openid;
              // that.setData({openId:res.data.openid})
              const resRegister = await postRegisterApi({
                nickname: nickName,
                openid: openId,
                avatar: avatarUrl.value,
                invitecode: inviteCode
              })
              console.log(resRegister);
              
              msgType.value = 'bottom'
              messageText.value = resRegister.msg
              message.value.open()
              uni.setStorageSync('user_id', resRegister.data.user_id)
              wx.setStorageSync('state', '1')        
              uni.reLaunch({ url: '/pages/index/index' })
            }
          })
          // console.log(that.data.openId);
          
        }
      })
    }
  })

}



// const registerSuccess = (profile: RegisterResult) => {
//   //保存用户信息
//   const memberStore = useMemberStore()
//   memberStore.setProfile(profile)
//   profile.nickname = nickName
//   console.log(profile.nickname);
  
//   setTimeout(() => {
//     //页面跳转
//   }, 500);
// }

</script>

<style lang="scss">
  .container{
  padding-top: 50rpx;
}
.title{
  display: flex;
  justify-content: center;
}
.title image{
  width: 447rpx;
}
#nickname,#invite{
  width: 490rpx;
  height: 80rpx;
  background-color: #f6f6f6;
  border-radius: 30rpx;
}
.info{
  margin-top: 133rpx;
}
.nickname,.invite{
  position: relative;
  display: flex;
  justify-content: center;
}
.nickname image{
  width: 40rpx;
  position: absolute;
  z-index: 999;
  top: 26%;
  left: 150rpx;
}
.invite{
  margin-top: 66rpx;
  position: relative;
}
.invite image{
  width: 40rpx;
  position: absolute;
  z-index: 999;
  top: 26%;
  left: 150rpx;
}
input{
  padding-left: 70rpx;
}
.next{
  margin-top: 300rpx;
  width: 570rpx !important;
  height: 80rpx !important;
  border-radius: 40rpx !important;
  background-color: #f66d27;
  color: #fff;
}
.avatar-wrapper {
  padding: 0;
  width: 200rpx !important;
  border-radius: 100rpx;
  margin-top: 60rpx;
  margin-bottom: 50rpx;
  position: relative;
}

.avatar {
  display: block;
  width: 200rpx;
  height: 94rpx;
}
.edit-avatar{
  position: relative;
}
#edit{
 position: absolute;
 z-index: 999;
 width: 52rpx !important;
 top: 70%;
 left: 60%;
}
.popup-warn {
  color: #fff;
  background-color: #faecd8;
}

@mixin flex {
  display: flex;
  flex-direction: row;
}

@mixin height {
  height: 100%;
  flex: 1;
}

.box {
  @include flex;
}

.button {
  @include flex;
  align-items: center;
  justify-content: center;
  flex: 1;
  height: 35px;
  margin: 0 5px;
  border-radius: 5px;
}

.example-body {
  background-color: #fff;
  padding: 10px 0;
}

.button-text {
  color: #fff;
  font-size: 12px;
}

.popup-content {
  @include flex;
  align-items: center;
  justify-content: center;
  padding: 30rpx;
  height: 50rpx;
  background-color: #e2e2e2 !important;
  border-radius: 15rpx;
}

.popup-height {
  @include height;
  width: 200px;
}

.text {
  font-size: 12px;
  color: #333;
}

.popup-success {
  color: #fff;
  background-color: #e1f3d8;
}

.popup-warn {
  color: #fff;
  background-color: #faecd8;
}

.popup-error {
  color: #fff;
  background-color: #fde2e2;
}

.popup-info {
  color: #fff;
  background-color: #f2f6fc;
}

.success-text {
  color: #09bb07;
}

.warn-text {
  color: #e6a23c;
}

.error-text {
  color: #f56c6c;
}

.info-text {
  color: #909399;
}

.dialog,
.share {
  display: flex;
  flex-direction: column;
}

.dialog-box {
  padding: 10px;
}

.dialog .button,
.share .button {
  width: 100%;
  margin: 0;
  margin-top: 10px;
  padding: 3px 0;
  flex: 1;
}

.dialog-text {
  font-size: 14px;
  color: #333;
}
</style>