<template>
    <!-- <view class="navbar">
  <view class="navbar-fixed">
    <view :style="{ height: statusBarHeight + 'px' }"></view>
    <view class="navbar-content" :style="{ height: navBarHeight + 'px', width: windowWidth-40 + 'px' }">
      <view class="navbar-search">
        <view class="navbar-search_icon">
          <uni-icons type="search" size="16" color="#999"></uni-icons>
        </view>
        <view class="navbar-serach">
          <input class="navbar-search_text" type="text" v-model="val" placeholder="请输入您要搜索的内容" />
        </view>
      </view>
    </view>
  </view>
  <view :style="{ height: statusBarHeight + navBarHeight + 'px' }"></view>
</view> -->
  <view class="navbar">
    <view class="navbar-fixed">
      <!-- 状态栏小程序撑起高度 -->
      <view :style="{ height: statusBarHeight + 'px' }"></view>
      <view class="navbar-content" :style="{ height: navBarHeight + 'px', width: windowWidth + 'px' }">
        <view class="navbar-myanswerback_icon">
          <uni-icons type="back" size="22" color="#999" @click="gotoindex"></uni-icons>
        </view>
        <view class="myanswerTitle">搜索</view>
      </view>
    </view>
    <!-- 需要添加占位符高度  状态栏高度+导航栏高度（否则下面tab会塌陷）-->
    <view :style="{ height: statusBarHeight + navBarHeight + 'px' }"></view>
  </view>
      <!-- 推荐列表 -->
    <view class="RecommendList">
      <view class="cardRecommendList" v-for="item in searchResult" :key="item.q_id" :id="item.q_id">
        <navigator v-if="item.img_link != null" :url="`/pages/ques_detail/quesDetail?id=${item.q_id}`"
          open-type="navigate" hover-class="navigator-hover" class="RecommendCard">
          <view class="titleRecommendList">
            {{ item.q_title }}
          </view>
          <view class="bottomRecommendList">
            <view class="leftRecommendList">
              <view class="infoRecommendList">
                <view class="avatarRecommendList">
                  <image :src="item.ans.avatar" mode="scaleToFill" />
                </view>
                <view class="nicknameRecommendList">{{ item.ans.nickname }}</view>
              </view>
              <view class="schemaRecommendList">
                {{ item.ans.info }}
              </view>
            </view>
            <view class="rightRecommendList">
              <image :src="item.ans.img_link" mode="aspectFill" />
            </view>
          </view>
          <view class="interRecommendList">
            <label class="sameQesRecommendList">{{ item.concern }}<label>同问</label></label>
            <label class="ansRecommendList">{{ item.ansnum }}<label>回答</label></label>
          </view>
        </navigator>
        <navigator v-else :url="`/pages/ques_detail/quesDetail?id=${item.q_id}`" open-type="navigate"
          hover-class="navigator-hover" class="RecommendCard">
          <view class="titleRecommendList">
            {{ item.title }}
          </view>
          <view class="bottomRecommendList">
            <view class="leftRecommendList">
              <view class="infoRecommendList">
                <view class="avatarRecommendList">
                  <image :src="item.ans.avatar" mode="scaleToFill" />
                </view>
                <view class="nicknameRecommendList">{{ item.ans.nickname }}</view>
              </view>
              <view class="schemaRecommendList2">
                {{ item.ans.info }}
              </view>
              <view class="interRecommendList2">
                <label class="sameQesRecommendList2">{{ item.concern }}<label>同问</label></label>
                <label class="ansRecommendList2">{{ item.ansnum }}<label>回答</label></label>
              </view>
            </view>
          </view>
        </navigator>
      </view>
    </view>
</template>

<script setup lang="ts">
import { onLoad } from "@dcloudio/uni-app"
import { ref } from "vue"
import {postSearchApi} from '@/services/search'
let statusBarHeight = ref(20)
let navBarHeight = ref(45)
const windowWidth = ref(375)
// 接收搜索参数
const query = defineProps<{
  val: string,
}>()

//存储搜索内容
const searchContent = ref('')
searchContent.value = query.val

//存储搜索结果
const searchResult = ref()
onLoad(async () => {
  const res = await wx.login()
  // console.log(res);
  wx.hideHomeButton({
    complete: () => {
      // console.log(9999);

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

  startSearch()
})

const gotoindex = ()=>{
  uni.switchTab({url:'../../pages/index/index'})
}

const startSearch = async ()=>{
  const resStartSearch = await postSearchApi({
    keyword:searchContent.value
  })
  console.log(resStartSearch);
  searchResult.value = resStartSearch.data
}
</script>

<style scoped lang="scss">
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
.RecommendList {
  width: 750rpx;

  // margin-top: 10rpx;
  .cardRecommendList {
    padding: 26rpx;
    padding-left: 29rpx;
    margin-top: 30rpx;
    background-color: #fff;
    position: relative;

    .titleRecommendList {
      font-weight: 800;
      font-size: 30rpx;
    }

    .bottomRecommendList {
      display: flex;
      // align-items: center;
      margin-top: 20rpx;

      .leftRecommendList {
        display: inline-block;

        .infoRecommendList {
          display: flex;
          align-items: center;
          margin-top: 6rpx;

          .avatarRecommendList {
            display: flex;
            align-items: center;

            image {
              display: inline-block;
              width: 50rpx;
              height: 50rpx;
              border-radius: 40rpx;
            }
          }

          .nicknameRecommendList {
            display: inline-block;
            font-size: 26rpx;
            color: #777;
            margin-left: 10rpx;
          }
        }

        .schemaRecommendList {
          margin-top: 6rpx;
          width: 450rpx;
          font-size: 27rpx;
          //两行
          text-overflow: -o-ellipsis-lastline;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          line-clamp: 2;
          -webkit-box-orient: vertical;
        }

        .schemaRecommendList2 {
          margin-top: 6rpx;
          width: 700rpx;
          font-size: 27rpx;
          //两行
          text-overflow: -o-ellipsis-lastline;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          line-clamp: 2;
          -webkit-box-orient: vertical;
        }
      }

      .rightRecommendList {
        display: inline-block;
        margin-left: 45rpx;

        image {
          width: 170rpx;
          height: 170rpx;
        }

        .ansTagRecommendList {
          position: relative;
          margin-left: 79rpx;

          .btnAnsTagRecommendList {
            border-radius: 34rpx !important;
            width: 100rpx !important;
            height: 40rpx !important;
            font-size: 26rpx;
            padding: 1rpx;
            line-height: 40rpx;
            color: #fff;
            background-color: #f66d27;
            margin-top: 15rpx;
            position: absolute;
            left: 0rpx;
            bottom: 2rpx;
          }
        }
      }
    }

    .interRecommendList {
      margin-top: -42rpx;
      font-size: 27rpx;
      color: #777;

      .sameQesRecommendList {
        margin-right: 24rpx;
      }

      .sameQesRecommendList2 {
        margin-right: 24rpx;
        display: inline-block;
      }

      .ansRecommendList2 {
        display: inline-block;
        margin-right: 365rpx;
      }

      .btnAnsTagRecommendList2 {
        display: inline-block;
        border-radius: 34rpx !important;
        width: 100rpx !important;
        height: 40rpx !important;
        font-size: 26rpx;
        padding: 1rpx;
        line-height: 40rpx;
        color: #fff;
        background-color: #f66d27;
        margin-top: 25rpx;
        position: absolute;
        right: 45rpx;
        bottom: 20rpx;
      }
    }

    .interRecommendList2 {
      margin-top: 55rpx;
      font-size: 27rpx;
      color: #777;

      .sameQesRecommendList {
        margin-right: 24rpx;
      }

      .sameQesRecommendList2 {
        margin-right: 24rpx;
        display: inline-block;
      }

      .ansRecommendList2 {
        display: inline-block;
        margin-right: 365rpx;
      }

      .btnAnsTagRecommendList2 {
        display: inline-block;
        border-radius: 34rpx !important;
        width: 100rpx !important;
        height: 40rpx !important;
        font-size: 26rpx;
        padding: 1rpx;
        line-height: 40rpx;
        color: #fff;
        background-color: #f66d27;
        margin-top: 25rpx;
        position: absolute;
        right: 45rpx;
        bottom: 20rpx;
      }
    }
  }

  .cardRecommendList:first-child {
    margin-top: 0rpx;
  }
}
</style>