<script setup lang="ts">
import { onLoad, onShow } from '@dcloudio/uni-app'
import { ref } from 'vue';
import { postAnsApi } from '@/services/addans'
let statusBarHeight = ref(20)
let navBarHeight = ref(45)
const windowWidth = ref(375)
const val = ref('')
//消息弹出框
const message = ref(null)

// 接收问题详情页面参数
const query = defineProps<{
  id: string,
  title: string
}>()
// console.log(query.title);

//存储问题的标题
const qusTitle = ref('')
//存储问题id
const qusId = ref('')
//存储用户回答
const ansInfo = ref('')
qusTitle.value = query.title
qusId.value = query.id
onLoad(async () => {
  const res = await wx.login()
  // console.log(res);
  wx.hideHomeButton({
    complete: () => {
      // console.log(9999);

    }
  })

})


const isActive = ref(0)

//checked函数
const checked = (index: number) => {
  isActive.value = index
}


//保存上传图片地址
const imgsrc = ref('')
//上传图片
const chooseImg = () => {
  uni.chooseMedia({
    count: 9,
    mediaType: ['image', 'video'],
    sourceType: ['album', 'camera'],
    maxDuration: 30,
    camera: 'back',
    success(res) {
      console.log(res.tempFiles)
      console.log(res.tempFiles[0].tempFilePath);

      imgsrc.value = res.tempFiles[0].tempFilePath
    }
  })
}

//获取回答内容
const postInfoInput = (e: any) => {
  ansInfo.value = e.target.value
  // console.log(e.target.value);
}
//提示框类型
const msgType = ref('')
//提示消息内容
const messageText = ref('')
//写回答
const postAns = async (type: string) => {
  // console.log(111);
  console.log(ansInfo.value);
  
  if (ansInfo.value === "") {
    msgType.value = type
    messageText.value = "回答内容不可为空"
    message.value.open()
  } else {
    const respostAns = await postAnsApi({
      q_id: Number(qusId.value),
      img_link: imgsrc.value,
      ansInfo: ansInfo.value,
      user_id: uni.getStorageSync('user_id')
    })
    // console.log(respostAns);
    
    // setTimeout(() => {
    //   msgType.value = type
    // messageText.value = "发布成功"
    // message.value.open()
    // }, 2000);
    let pages = getCurrentPages(); //当前页
    let beforPage = pages[pages.length-2] //上一页
    uni.navigateBack({
	    success: function(){
        beforPage.onLoad()
      }
    })
  }

}

onShow(async () => {
  if (message.value) {
    message.value.close()
  }
})
</script>

<template>
  <view class="nav_item" v-if="isActive == 0" style="position:relative;">
    <view class="postQusTitle">
      <view style="font-weight: 800;">{{ qusTitle }}</view>
    </view>
    <view class="postQusContent">
      <textarea name="" id="" cols="70" rows="10" placeholder="请输入图文回答内容（150字以内）" maxlength="150" :value="postInfo"
        @input="postInfoInput" style="height: 350rpx;"></textarea>
    </view>
    <view style="display: flex;justify-content: center;">
      <image :src="imgsrc" :mode="heightFix"></image>
    </view>
    <view class="btnPost" style="float:right;position:absolute;bottom:-210rpx;right:0rpx;">
      <view class="chooseClassify" @click="chooseImg" style="display:inline-block;"><button>上传图片</button></view>
      <!-- <view class="chooseImg" @click="chooseImg" style="display:inline-block;"><button>请选择图片</button></view> -->
      <view class="postQusBtn" style="display:inline-block;" @click="postAns('bottom')"><button>发布</button></view>
    </view>
    <uni-popup ref="message">
      <view class="popup-content" :class="{ 'popup-height': type === 'left' || type === 'right' }"><text class="text">{{
        messageText }}</text></view>
    </uni-popup>
  </view>
</template>

<style lang="scss">
.tab_nav {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 70rpx;
  line-height: 70px;
}

.tab_nav .navTitle {
  height: 60rpx;
  line-height: 80rpx;
  width: 100%;
  text-align: center;
  font-size: 32rpx;
  font-family: Alibaba PuHuiTi;
  color: #333;
}

.active {
  // position: relative;
  color: #000 !important;
  font-weight: 700;
}

.active::after {
  content: "";
  position: absolute;
  width: 80rpx;
  height: 0rpx;
  background-color: #f66d27;
  left: 0px;
  right: 0px;
  bottom: 0px;
  margin: auto;
}

.nav_item {
  .postQusTitle {
    padding: 35rpx 25rpx;
    border-bottom: 0.3rpx solid rgba(204, 204, 204, 0.4);
  }

  .postQusContent {
    padding: 25rpx 25rpx;

    textarea {
      width: 670rpx;
    }
  }

  .postQusSelect {
    display: flex;
    justify-content: flex-end;
    margin-right: 20rpx;

    .qusLink .uni-icons {
      color: #aaa !important;
    }

    .qusPic .uni-icons {
      color: #aaa !important;
    }
  }

  .postQusBtn {
    margin-top: 20rpx;
    display: flex;
    justify-content: flex-end;
    margin-right: 20rpx;

    button {
      width: 120rpx;
      height: 64rpx;
      font-size: 24rpx;
      margin: 5rpx 27rpx;
      margin-right: 0rpx;
      display: inline-block;
      background-color: #f66d27;
      color: #fff;
      line-height: 64rpx;
      border-radius: 64rpx;
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
    background-color: #f6f6f6;

    .navbar-content {
      display: flex;
      align-items: center;
      padding: 0 15px;
      height: 45px;
      box-sizing: border-box;

      .navbar-myanswerback {
        display: flex;
        align-items: center;
        padding: 0 10px;
        width: 100%;
        height: 30px;
        border-radius: 30px;
        background-color: #fff;
        box-shadow: 6rpx 6rpx 6rpx rgba(0, 0, 0, 0.05), 0 0 6rpx rgba(0, 0, 0, 0.05), 0 0 6rpx rgba(0, 0, 0, 0.05);

        .navbar-myanswerback_icon {
          // width: 10px;
          // height: 10px;
          margin-right: 10px;
        }
      }

      .myanswerTitle {
        color: #f66d27;
      }
    }

  }

}

.chooseImg {
  button {
    width: 180rpx;
    height: 64rpx;
    font-size: 24rpx;
    margin: 5rpx 27rpx;
    margin-right: 0rpx;
    display: inline-block;
    background-color: #f66d27;
    color: #fff;
    line-height: 64rpx;
    border-radius: 64rpx;
  }
}

.chooseClassify {
  button {
    width: 180rpx;
    height: 64rpx;
    font-size: 24rpx;
    margin: 5rpx 27rpx;
    margin-right: 0rpx;
    display: inline-block;
    background-color: #f66d27;
    color: #fff;
    line-height: 64rpx;
    border-radius: 64rpx;
  }
}

.chooseClassifyCard {
  position: absolute;
  top: 200rpx;
  right: 200rpx;
  width: 500rpx;
  height: 500rpx;
  background-color: pink;
}

.uni-list-cell-pd {
  display: inline-block;
}

.classifyBlock {
  padding: 20rpx;
  background-color: rgba(246, 109, 39, 0.2);
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

.popup-warn {
  color: #fff;
  background-color: #faecd8;
}

//uniapp直接复制，像设置popup
@mixin flex {
  /* #ifndef APP-NVUE */
  display: flex;
  /* #endif */
  flex-direction: row;
}

@mixin height {
  /* #ifndef APP-NVUE */
  height: 100%;
  /* #endif */
  /* #ifdef APP-NVUE */
  flex: 1;
  /* #endif */
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
  /* #ifndef APP-NVUE */
  display: flex;
  /* #endif */
  flex-direction: column;
}

.dialog-box {
  padding: 10px;
}

.dialog .button,
.share .button {
  /* #ifndef APP-NVUE */
  width: 100%;
  /* #endif */
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
