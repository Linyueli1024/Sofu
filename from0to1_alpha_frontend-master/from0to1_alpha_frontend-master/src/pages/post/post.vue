<script setup lang="ts">
import { onLoad, onShow } from '@dcloudio/uni-app'
import { ref } from 'vue';
import { postChooseClassifyApi } from '@/services/post'
let statusBarHeight = ref(20)
let navBarHeight = ref(45)
const windowWidth = ref(375)
const val = ref('')
//消息弹出框
const message = ref(null)

onLoad(async () => {
  const res = await wx.login()
  // console.log(res);
  wx.hideHomeButton({
    complete: () => {
      console.log(9999);

    }
  })

  // uni.hideTabBar()
  // 在 created 生命周期钩子中执行一些操作
  //  获取手机系统信息
  const info = uni.getSystemInfoSync()
  // 设置状态栏高度（H5顶部无状态栏小程序有状态栏需要撑起高度）
  // statusBarHeight.value = info.statusBarHeight
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

})



const navList = ref([
  { index: 0, title: '想问' },
  { index: 1, title: '想说' }
])

const back = () => {
  console.log(111);

  uni.switchTab({
    url: '../index/index'
  });

}

const isActive = ref(0)

//checked函数
const checked = (index: number) => {
  isActive.value = index
}
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

const gotohome = () => {
  uni.switchTab({ url: '../../pages/index/index' })
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

//判断是否弹出选择分类
const isConfirm = ref(false)
//选择分类
const chooseClassify = () => {
  isConfirm.value = !isConfirm.value
}
const checkbox3 = ref([0])
const hobby = ref([{
  text: '足球',
  value: 0
}, {
  text: '篮球',
  value: 1
}, {
  text: '游泳',
  value: 2
}])

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
//点击确认按钮，确认提交分类
const confirmClassify = () => {
  isConfirm.value = !isConfirm.value
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

//获取发布问题的标题
const postTitle = ref('')
const postTitleInput = (e: any) => {
  postTitle.value = e.target.value
  console.log(e.target.value);
}

//获取发布问题的补充说明
const postInfo = ref('')
const postInfoInput = (e: any) => {
  postInfo.value = e.target.value
  console.log(e.target.value);
}

//提示框类型
const msgType = ref('')
//提示消息内容
const messageText = ref('')
//发布问题
const postQus = async (type: string) => {
  console.log(postTitle.value);
  console.log(finalClassify.value);

  if (postTitle.value == "") {
    msgType.value = type
    messageText.value = "请输入你的问题"
    message.value.open()
  } else if (finalClassify.value == null) {
    msgType.value = type
    messageText.value = "请选择分类"
    message.value.open()
  } else if (JSON.parse(JSON.stringify(finalClassify.value)).length === 0) {
    msgType.value = type
    messageText.value = "请选择分类"
    message.value.open()
  }
  else {
    const respost = await postChooseClassifyApi({
      qtitle: postTitle.value,
      imglink: "",
      classifyid: JSON.parse(JSON.stringify(finalClassify.value)),
      questioninfo: postInfo.value,
      userid: uni.getStorageSync('user_id')
    })
    console.log(respost);
    msgType.value = type
    messageText.value = "发布成功"
    message.value.open()
    
    // setTimeout(() => {

    // }, 2000);
    uni.switchTab({ url: '../../pages/index/index' })
  }
}

onShow(async () => {
  if (message.value) {
    message.value.close()
  }
  postTitle.value = ""
  postInfo.value = ""
})
</script>

<template>
  <!-- <view class="back">
      <uni-icons custom-prefix="custom-icon" type="back" size="30" @click="back"></uni-icons>
    </view> -->
  <!-- tab栏切换 -->
  <!-- <view class="tab_nav">
    	<view class="navTitle" v-for="(item,index) in navList" :key="index" >
    		<view :class="{'active':isActive === index}" @click="checked(index)" style="color:rgb(102, 102, 102);">
    			{{item.title}}
    		</view>
    	</view>
    </view> -->
  <view class="navbar">
    <view class="navbar-fixed">
      <!-- 状态栏小程序撑起高度 -->
      <view :style="{ height: statusBarHeight + 'px' }"></view>
      <view class="navbar-content" :style="{ height: navBarHeight + 'px', width: windowWidth + 'px' }">
        <view class="navbar-myanswerback_icon">
          <uni-icons type="back" size="22" color="#999" @click="gotohome"></uni-icons>
        </view>
        <view class="myanswerTitle">发布</view>
      </view>
    </view>
    <!-- 需要添加占位符高度  状态栏高度+导航栏高度（否则下面tab会塌陷）-->
    <view :style="{ height: statusBarHeight + navBarHeight + 'px' }"></view>
  </view>
  <view class="nav_item" v-if="isActive == 0" style="position:relative;">
    <view class="postQusTitle">
      <input type="text" placeholder="输入问题并以问号结尾（20字以内）" :value="postTitle" @input="postTitleInput">
    </view>
    <view class="postQusContent">
      <textarea name="" id="" cols="70" rows="10" placeholder="对问题补充说明，可以更快获得解答（100字以内选填）" maxlength="100"
        :value="postInfo" @input="postInfoInput"></textarea>
    </view>
    <image :src="imgsrc" :mode="heightFix"></image>
    <!-- <view class="chooseClassifyCard">
        <view class="uni-px-5 uni-pb-5">
    				<view class="text">多选选中：{{JSON.stringify(checkbox3)}}</view>
    				<uni-data-checkbox mode="tag" multiple v-model="checkbox3" :localdata="hobby"></uni-data-checkbox>
    		</view>
      </view> -->
    <view class="classifyBlock" style="position:absolute;width:500rpx;top: 292rpx;left: 105rpx;z-index:999;"
      v-show="isConfirm">
      <view class="uni-list">
        <checkbox-group @change="checkboxChange">
          <label class="uni-list-cell uni-list-cell-pd" v-for="item in items" :key="item.value"
            style="margin-right:41rpx;">
            <view style="display:inline-block;">
              <checkbox :value="item.value" :checked="item.checked" />
            </view>
            <view style="display:inline-block;">{{ item.name }}</view>
          </label>
        </checkbox-group>
      </view>
      <view class="confirmClassify" @click="confirmClassify"><button>确认</button></view>
    </view>
    <view class="btnPost" style="float:right;position:absolute;bottom:-159rpx;right:0rpx;">
      <view class="chooseClassify" @click="chooseClassify" style="display:inline-block;"><button>请选择分类</button></view>
      <!-- <view class="chooseImg" @click="chooseImg" style="display:inline-block;"><button>请选择图片</button></view> -->
      <view class="postQusBtn" style="display:inline-block;" @click="postQus('bottom')"><button>发布</button></view>
    </view>
    <uni-popup ref="message" @change="change">
      <view class="popup-content" :class="{ 'popup-height': type === 'left' || type === 'right' }"><text class="text">{{
        messageText }}</text></view>
    </uni-popup>
  </view>
  <view class="nav_item" v-if="isActive == 1">
    <view class="postQusTitle">
      <input type="text" placeholder="输入“想法”的标题">
    </view>
    <view class="postQusContent">
      <textarea name="" id="" cols="70" rows="10" placeholder="对内容补充说明，可以更快获得更高的曝光度"></textarea>
    </view>
    <view class="postQusBottom">
      <view class="postQusSelect">
        <uni-icons custom-prefix="custom-icon" type="paperclip" size="30" class="qusLink"></uni-icons>
        <uni-icons custom-prefix="custom-icon" type="images" size="30" class="qusPic"></uni-icons>
      </view>
      <view class="postQusBtn">
        <button>发布</button>
      </view>
    </view>
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
