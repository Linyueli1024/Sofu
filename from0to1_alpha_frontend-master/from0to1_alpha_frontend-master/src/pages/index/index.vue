<script setup lang="ts">
import { onLoad, onPullDownRefresh, onShow } from '@dcloudio/uni-app'
import { ref, reactive, getCurrentInstance, toRaw } from 'vue';
import { postRecommendApi } from '@/services/recommend'
import { postRecommendOneApi } from '@/services/recommendone'
let statusBarHeight = ref(20)
let navBarHeight = ref(45)
const windowWidth = ref(375)
const val = ref('')
const extraIcon = ref({
  color: 'red',
  size: '18',
  type: 'fire'
})
const extraIcon2 = ref({
  color: 'orange',
  size: '18',
  type: 'fire'
})
const extraIcon3 = ref({
  color: '#e6af6f',
  size: '18',
  type: 'fire'
})


onLoad(async () => {
  const res = await wx.login()
  // console.log(res);
  wx.hideHomeButton({
    complete: () => {
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

  getClassify()
  getRecommendList()
  getHotList()
})

const navList = ref([
  // {index:0, title:'想法'},
  { index: 0, title: '推荐' },
  { index: 1, title: '热榜' }
])

const isActive = ref(0)

//checked函数
const checked = (index: number) => {
  isActive.value = index
}
//定义弹窗类型
let type = {
  value: 'center'
}
const change = (e: any) => {
  console.log('当前模式：' + e.type + ',状态：' + e.show);
}
// const popup = ref<any>(null);
// const toggle = (type:any)=> {
//   type.value = '5'
//   // open 方法传入参数 等同在 uni-popup 组件上绑定 type属性
//   console.log(popup.value);

//   popup.open(type)
// }

// 以下是推荐部分
const proxy = getCurrentInstance()?.proxy
import { itemArr } from './index'
import type { RecommendItem } from '@/types/recommend';
import { getClassifyApi } from '@/services/classify';
import { getHotApi } from '@/services/hot';
const itemList = reactive(itemArr)
const xiala_state = ref(false)

//记录是否选择了分类
const isSelect = ref(0)
const recommendList = ref<RecommendItem[]>([])

const getRecommendList = async () => {
  const res = await postRecommendApi({ userid: uni.getStorageSync('user_id') })
  recommendList.value = res.data
  console.log(recommendList.value);
  tabIndex.value = 0;
  isSelect.value = 1
}

//选取分类标签重新渲染推荐列表
//选择分类，事件委托
const selectClassify = async (index: any) => {
  if (index === 0) {
    uni.startPullDownRefresh()
    // setTimeout(() => {
    //     //结束下拉刷新
    //   uni.stopPullDownRefresh ();
    // }, 1000);
    const res = await postRecommendApi({ userid: uni.getStorageSync('user_id') })
    recommendList.value = res.data
    console.log(recommendList.value);
    tabIndex.value = 0;
  } else {
    tabIndex.value = index;
    console.log(tabIndex.value);
    uni.startPullDownRefresh()
    // setTimeout(() => {
    //     //结束下拉刷新
    //   uni.stopPullDownRefresh ();
    // }, 1000);
    const res = await postRecommendOneApi({ tag: tabIndex.value })
    recommendList.value = res.data
    console.log(recommendList.value);

  }
  isSelect.value = 1
}

// //点击进入详情页
// const ToDetail = (e:any)=>{
//   console.log(e.target);
// }

//点击下拉菜单选择分类
const xiala = () => {
  xiala_state.value = !xiala_state.value

}

//tabbar
const tabBars: any = ref()
const tabIndex = ref(0)
//渲染分类菜单
const getClassify = async () => {
  const res = await getClassifyApi()
  console.log(res);
  const newData = res.data.unshift({ img_link: null, name: "全部" })
  console.log(res.data);
  tabBars.value = res.data
}

//切换选项卡
const toggleTab = (index: any) => {
  tabIndex.value = index;
  console.log(tabIndex.value);

}
//滑动切换swiper
const tabChange = (e: any) => {
  console.log(e);
  tabIndex.value = e.detail.current;
}

//还未实现重新渲染

//点击上拉菜单
const shangla = () => {
  xiala_state.value = !xiala_state.value
}



//下拉刷新
onPullDownRefresh(() => {
  setTimeout(() => {
    //结束下拉刷新
    uni.stopPullDownRefresh();
  }, 1000);
  if (isSelect.value === 0) {
    console.log(11);

    getRecommendList()
  }
  else {
    console.log(tabIndex.value);

    // selectClassify(tabIndex.value)
  }
})

//写回答
const goToWrite = () => {
  console.log(111);
  uni.navigateTo({
    url: '../../pages/answer/answer'
  })
}

//热搜
//获取内容
const hotcontent = ref()
const getHotList = async () => {
  const res = await getHotApi()
  console.log(res);
  hotcontent.value = res.data
}

//获取搜索框输入的内容
const searchContent = ref('')
const getContent = ()=>{
  console.log(searchContent.value);
  
}
//搜索功能
const doSearch = ()=>{
  console.log(111);
  uni.navigateTo({url:`../../pages/searchResult/searchresult?val=${val.value}`})
  console.log(val.value);
}

//热榜跳转到详情页
const hotToDetail = (id:number)=>{
  uni.navigateTo({url:`/pages/ques_detail/quesDetail?id=${id}`})
  console.log(111);
  
}

onShow(()=>{
  if(val.value){
    val.value = ""
  }
})
</script>


<template>
  <view class="navbar">
  <view class="navbar-fixed">
    <!-- 状态栏小程序撑起高度 -->
    <view :style="{ height: statusBarHeight + 'px' }"></view>
    <view class="navbar-content" :style="{ height: navBarHeight + 'px', width: windowWidth + 'px' }">
      <view class="navbar-search">
        <view class="navbar-search_icon">
          <uni-icons type="search" size="16" color="#999"></uni-icons>
        </view>
        <view class="navbar-serach">
          <input class="navbar-search_text"  type="text" v-model="val" placeholder="请输入您要搜索的内容" @confirm="doSearch"/>
        </view>
      </view>
    </view>
  </view>
  <!-- 需要添加占位符高度  状态栏高度+导航栏高度（否则下面tab会塌陷）-->
  <view :style="{ height: statusBarHeight + navBarHeight + 'px' }"></view>
</view>
<!-- tab栏切换 -->
<view style="position:fixed;z-index:999;width:750rpx;background-color: #fff;">
  <view class="tab_nav">
    <view class="navTitle" v-for="(item, index) in navList" :key="index">
      <view :class="{ 'activeTab': isActive === index }" @click="checked(index)">
        {{ item.title }}
      </view>
    </view>
  </view>
</view>
<!-- 推荐 -->
<view class="nav_item" v-if="isActive == 0">
  <view class="listClassify">
    <!-- <label class="inner">全部</label>
      <label class="inner">生活</label>
      <label class="inner">学习</label>
      <label class="inner">娱乐</label>
      <label class="inner">选课</label>
      <label class="inner">美食</label>
      <label class="inner">情感</label>
      <label class="inner">美食</label>
      <label class="inner">情感</label>
      <label class="imageClassify"><image
        src="../../static/images/icon/xiala.png"
        mode="scaleToFill"
        @click="xiala"
      /></label> -->
    <view>
      <!-- 顶部导航栏 -->
        <view class="horizonal-tab">
        <scroll-view scroll-x="true" scroll-with-animation class="scroll-tab">
          <!-- <view class="scroll-tab-item" :class="{'active': tabIndex==0}" 
					@tap="getRecommendList">全部</view> -->
          <block v-for="(item, index) in tabBars" :key="index">
            <view class="scroll-tab-item" :class="{ 'active': tabIndex == index }" @tap="selectClassify(index)">
              {{ item.name }}
              <view class="scroll-tab-line"></view>
            </view>

          </block>
        </scroll-view>
      </view>



    </view>
  </view>
  <!-- 分类 -->
  <view class="cardClassify" v-if="xiala_state">
    <view class="topClassify">
        <label class="titleClassify">分类</label>
        <label class="shanglaClassify">
          <image src="../../static/images/icon/shangla.png" mode="aspectFill" @click="shangla" />
        </label>
      </view>
      <view class="btnClassify" @click="selectClassify">
        <button id="1">选课</button>
        <button id="2">转专业</button>
        <button id="3">美食</button>
        <button id="4">就业实习</button>
        <button id="5">升学</button>
        <button id="6">出国</button>
        <button id="7">考试</button>
        <button id="8">情感</button>
        <button id="9">社团</button>
        <button id="10">铜盘校区</button>
        <button id="11">兼职</button>
        <button id="12">猫狗</button>
        <button id="13">校园活动</button>
        <button id="14">交友</button>
        <button id="15">游戏</button>
        <button id="16">兴趣</button>
        <button id="17">运动</button>
        <button id="18">竞猜</button>
        <button id="19">科研</button>
      </view>
    </view>
    <!-- 推荐列表 -->
    <view class="RecommendList">
      <view class="cardRecommendList" v-for="item in recommendList" :key="item.q_id" :id="item.q_id">
        <!-- v-if="item.img_link != null" class="RecommendCard" -->
        <navigator v-if="item.ans.img_link != null" :url="`/pages/ques_detail/quesDetail?id=${item.q_id}`"
          open-type="navigate" hover-class="navigator-hover" class="RecommendCard">
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
              <view class="schemaRecommendList">
                {{ item.ans.info }}
              </view>
            </view>
            <view class="rightRecommendList">
              <image :src="item.ans.img_link" mode="aspectFill" />
              <!-- <label class="ansTagRecommendList">
                <button class="btnAnsTagRecommendList" @click="goToWrite" style="z-index:999;">
                  写回答
                </button>
              </label> -->
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
                <!-- <button class="btnAnsTagRecommendList2" @click="goToWrite" style="z-index:999;">
                  写回答
                </button> -->
              </view>
            </view>
          </view>
        </navigator>
      </view>
      <!-- <view class="cardRecommendList">
            <navigator
              url="/pages/ques_detail/quesDetail"
              open-type="navigate"
            >
            <view class="titleRecommendList">
              这是个短问题
            </view>
            <view class="bottomRecommendList">
              <view class="leftRecommendList">
              <view class="infoRecommendList">
                <view class="avatarRecommendList"><image
                  src="../../static/images/avatar.png"
                  mode="scaleToFill"
                /></view>
                <view class="nicknameRecommendList">AAAAA</view>
              </view>
              <view class="schemaRecommendList2">
                这是个短问题这是个短问题这是个短问题这是个短问题这是个短问题这是个短问题这是个短问题这是个短问题这是个短问题这是个短问题这是个短问题
              </view>
              <view class="interRecommendList2">
                <label class="sameQesRecommendList2">200<label>同问</label></label>
                <label class="ansRecommendList2">300<label>回答</label></label>
                <button class="btnAnsTagRecommendList2">
                  写回答
                </button>
              </view>
            </view>
            </view>     
            </navigator>
          </view>
          <view class="cardRecommendList">
            <navigator
              url="/pages/ques_detail/quesDetail"
              open-type="navigate"
            >
            <view class="titleRecommendList">
              这是个短问题
            </view>
            <view class="bottomRecommendList">
              <view class="leftRecommendList">
              <view class="infoRecommendList">
                <view class="avatarRecommendList"><image
                  src="../../static/images/avatar.png"
                  mode="scaleToFill"
                /></view>
                <view class="nicknameRecommendList">AAAA</view>
              </view>
              <view class="schemaRecommendList">
                 这是个长问题这是个长问题这是个长问题这是个长问题这是个长问题这是个长问题这是个长问题这是个长问题这是个长问题这是个长问题这是个长问题这是个长问题这是个长问题这是个长问题这是个长问题这是个长问题
              </view>
            </view>
            <view class="rightRecommendList">
              <image
                src="../../static/images/recommend3.png"
                mode="aspectFill"
              />
              <label class="ansTagRecommendList">
                <button class="btnAnsTagRecommendList">
                  写回答
                </button>
              </label>
            </view>
            </view>
            <view class="interRecommendList">
                <label class="sameQesRecommendList">200<label>同问</label></label>
                <label class="ansRecommendList">300<label>回答</label></label>
              </view>     
            </navigator>
          </view>
          <view class="cardRecommendList">
            <navigator
              url="/pages/ques_detail/quesDetail"
              open-type="navigate"
            >
            <view class="titleRecommendList">
              这是个短问题
            </view>
            <view class="bottomRecommendList">
              <view class="leftRecommendList">
              <view class="infoRecommendList">
                <view class="avatarRecommendList"><image
                  src="../../static/images/avatar.png"
                  mode="scaleToFill"
                /></view>
                <view class="nicknameRecommendList">AAAA</view>
              </view>
              <view class="schemaRecommendList">
                 这是个短问题
              </view>
            </view>
            <view class="rightRecommendList">
              <image
                src="../../static/images/recommend3.png"
                mode="aspectFill"
              />
              <label class="ansTagRecommendList">
                <button class="btnAnsTagRecommendList">
                  写回答
                </button>
              </label>
            </view>
            </view>
            <view class="interRecommendList">
                <label class="sameQesRecommendList">200<label>同问</label></label>
                <label class="ansRecommendList">300<label>回答</label></label>
              </view>     
            </navigator>
          </view> -->
      <!-- <view class="cardRecommendList">
            <navigator
              url="/pages/ques_detail/quesDetail"
              open-type="navigate"
            >
            <view class="titleRecommendList">
              求问现在坐小白要用哪个小程序？
            </view>
            <view class="bottomRecommendList">
              <view class="leftRecommendList">
              <view class="interRecommendList">
                <label class="sameQesRecommendList2">0<label>同问</label></label>
                <label class="ansRecommendList2" style="margin-right:406rpx;">0<label>回答</label></label>
                <button class="btnAnsTagRecommendList2">
                  写回答
                </button>
              </view>
            </view>
            </view>     
            </navigator>
          </view> -->
    </view>
  </view>
  <!-- 热搜 -->
  <view class="nav_item" v-if="isActive == 1">
    <view class="hot">
      <image src="../../static/images/hot.png" mode="scaleToFill" />
      <view class="hot_list">
        <uni-list>
            <uni-list-item v-for="(item, index) in hotcontent" :key="index" :show-extra-icon="true" :extra-icon="extraIcon"
            :title="item.q_title" :note="`${item.hotdegree}℃热度`" @tap="hotToDetail(item.q_id)"/>
        </uni-list>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
page {
  background-color: #f6f6f6;
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
      justify-content: center;
      align-items: center;
      padding: 0 15px;
      height: 45px;
      box-sizing: border-box;

      .navbar-search {
        display: flex;
        align-items: center;
        padding: 0 10px;
        width: 100%;
        height: 30px;
        border-radius: 30px;
        background-color: #fff;
        box-shadow: 6rpx 6rpx 6rpx rgba(0, 0, 0, 0.05), 0 0 6rpx rgba(0, 0, 0, 0.05), 0 0 6rpx rgba(0, 0, 0, 0.05);

        .navbar-search_icon {
          // width: 10px;
          // height: 10px;
          margin-right: 10px;
        }

        .navbar-search_text {
          width: 100%;
          font-size: 14px;
          color: #999;
        }
      }

      &.search {
        padding-left: 0;

        .navbar-content__search-icons {
          margin-left: 10px;
          margin-right: 10px;
        }

        .navbar-search {
          border-radius: 5px;
        }
      }
    }

  }

}

.tab_nav {
  display: flex;
  justify-content: center;
  align-items: center;
}

.tab_nav .navTitle {
  height: 60rpx;
  line-height: 60rpx;
  width: 100%;
  text-align: center;
  font-size: 32rpx;
  font-family: Alibaba PuHuiTi;
  color: #333;
}

.activeTab {
  position: relative;
  color: #f66d27;
}

.activeTab::after {
  content: "";
  position: absolute;
  width: 80rpx;
  height: 7rpx;
  background-color: #f66d27;
  left: 0px;
  right: 0px;
  bottom: 0px;
  margin: auto;
}

// 以下是热搜模块
.hot image {
  width: 750rpx;
  height: 296rpx;
  margin-top: 20rpx;
}

// 以下是推荐模块
// .uni-popup.top, .uni-popup.left, .uni-popup.right{
//   top:105px !important;
// }
.listClassify {
  margin: 30px 5rpx 0 5rpx;
  padding: 8rpx;
  background-color: #fff;
  border-top: 0.3rpx solid rgba(204, 204, 204, 0.4);

  // position: fixed;
  // top: 133rpx;
  //     z-index: 999;
  .inner {
    margin: 20rpx;
    font-size: 29rpx;
  }

  .imageClassify {
    margin-top: 5rpx;

    image {
      width: 40rpx;
      height: 40rpx;
    }
  }
}

.nav_item {
  position: relative;

  .cardClassify {
    position: absolute;
    top: 0rpx;
    background-color: #fff;
    width: 690rpx;
    padding: 10rpx 30rpx;
    z-index: 999;

    .topClassify {
      display: flex;
      justify-content: space-between;

      .titleClassify {
        font-weight: 800;
      }

      .shanglaClassify {
        image {
          width: 40rpx;
          height: 40rpx;
        }
      }
    }

    .btnClassify {
      margin-top: 20rpx;

      button {
        width: 120rpx;
        height: 64rpx;
        font-size: 24rpx;
        margin: 5rpx 27rpx;
        display: inline-block;
        background-color: #fff;
        color: #777;
        line-height: 64rpx;
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

.modesipper {
  width: 100%;
  height: 100%;
  background-color: pink;
  position: absolute;
  top: 0rpx;
  left: 0rpx;
}

.horizonal-tab {}

.horizonal-tab .active {
  color: #f66d27;
}

.scroll-tab {
  white-space: nowrap;
  /* 必要，导航栏才能横向*/
  border-bottom: 1rpx solid #eee;
  text-align: center;
}

.scroll-tab-item {
  display: inline-block;
  /* 必要，导航栏才能横向*/
  margin: 20rpx 30rpx 0 30rpx;
}

.active .scroll-tab-line {
  border-bottom: 5rpx solid #f66d27;
  border-top: 5rpx solid #f66d27;
  border-radius: 20rpx;

}
</style>