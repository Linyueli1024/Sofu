<template>
  <view class="quesInfo">
    <view class="titleDetail">{{ DetailTitle }}</view>
    <view class="interAndCtrl">
      <view class="interDetail">
        <label class="ansDataDetail">{{ansNum}}个回答</label>
        <label class="askDataDetail">{{concernNum}}个同问</label>
      </view>
      <view class="btnDetail">
        <button class="askBtn" v-if="isConcern">已同问</button>
        <button class="askBtn" @click="addConcern" v-else >+同问</button>
        <button class="ansBtn" @click="writeAns">写回答</button>
      </view>
    </view>
  </view>
  <view class="ansList">
    <view class="ansCard">
      <view class="bottomAnsList" v-for="item in DetailAnsList" :key="item.ans_id">
        <view v-if="item.img_link != null" style="display: flex;">
          <view class="leftAnsList">
            <view class="infoAnsList">
              <view class="avatarAnsList">
                <image :src="item.avatar" mode="scaleToFill" />
              </view>
              <view class="nicknameAnsList">{{ item.nickname }}</view>
            </view>
            <view class="detailAnsList">
              {{ item.ans_info }}
            </view>
            <view class="interAnsList">
              <label class="sameQesAnsList">发布于</label>
              <label class="ansAnsList">{{ item.post_time }}</label>
            </view>
          </view>
          <view class="rightAnsList">
            <image :src="item.img_link" mode="aspectFill" />
            <view class="ansTagAnsList">
              <!-- <label class="comAnsDetail">
              <image
              src="../../static/images/icon/comment_detail.png"
              mode="scaleToFill"
            /></label> -->
              <label class="likeAnsDetail">
                <image src="../../static/images/icon/like_detail_selected.png" mode="scaleToFill" v-if="isLike"/>
                  <image src="../../static/images/icon/like_detail.png" mode="scaleToFill" @click="addLike(item.ans_id)" v-else/>
              </label>
            </view>
          </view>
        </view>
        <view v-else style="
                      display: flex;
                  ">
          <view class="leftAnsList">
            <view class="infoAnsList">
              <view class="avatarAnsList">
                <image src="../../static/images/avatar.png" mode="scaleToFill" />
              </view>
              <view class="nicknameAnsList">{{ item.nickname }}</view>
            </view>
            <view class="detailAnsList2">
              {{ item.ans_info }}
            </view>
            <view class="state2Info">
              <view class="interAnsList2">
                <label class="sameQesAnsList">发布于</label>
                <label class="ansAnsList">{{ item.post_time }}</label>
              </view>
              <view class="ansTagAnsList2">
                <!-- <label class="comAnsDetail">
              <image
              src="../../static/images/icon/comment_detail.png"
              mode="scaleToFill"
            /></label> -->
                <label class="likeAnsDetail">
                  <image src="../../static/images/icon/like_detail_selected.png" mode="scaleToFill" v-if="isLike"/>
                  <image src="../../static/images/icon/like_detail.png" mode="scaleToFill" @click="addLike(item.ans_id)" v-else/>
                </label>
              </view>
            </view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { onLoad, onPullDownRefresh } from "@dcloudio/uni-app";
import { postDetailApi } from "@/services/detail"
import type { DetailAnsItem, DetailItem } from "@/types/detail";
import { ref } from "vue";
import type { RecommendItem } from "@/types/recommend";
import { postRecommendApi } from "@/services/recommend";
import {postAddConcernApi} from "@/services/addConcern"
import {postAddLikeApi} from "@/services/addLike"

// 接收推荐列表页面参数
const query = defineProps<{
  id: string,
}>()


//获取详情页面
const DetailList = ref<DetailItem>()
//获取详情页面回答列表
const DetailAnsList = ref<DetailAnsItem[]>([])

const DetailTitle = ref('')

//获取同问数
const concernNum = ref()

//获取回答数
const ansNum = ref()

const postDetailApiMethod = async () => {
  const res = await postDetailApi({ q_id: Number(query.id) })
  console.log(res);
  DetailList.value = res.data
  DetailAnsList.value = res.data.ans
  DetailTitle.value = res.data.q_title
  concernNum.value = res.data.concern_num
  ansNum.value = res.data.answer_num
  console.log(res.data);
}


// console.log(typeof(Number(query.id)));
onLoad(() => {
  postDetailApiMethod()
})

//下拉刷新
onPullDownRefresh(() => {
  setTimeout(() => {
    //结束下拉刷新
    uni.stopPullDownRefresh();
  }, 1000);
  postDetailApiMethod()
})

//写回答
const writeAns = () => {
  // console.log(111);
  uni.navigateTo({ url: `../../pages/addAns/addans?id=${query.id}&title=${DetailTitle.value}` })


}

//是否同问
const isConcern = ref(false)

//同问
const addConcern = async ()=>{
  const resaddConcern = await postAddConcernApi ({
    user_id:uni.getStorageSync('user_id'),
    q_id:Number(query.id)
  })
  console.log(resaddConcern);
  isConcern.value = !isConcern.value
}

//点赞
//记录是否点赞
const isLike = ref(false)
// const addLike = async (id:number)=>{
//   // console.log(id);
  
//   const resaddLike = await postAddLikeApi ({
//     ans_id:id
//   })
//   // console.log(resaddLike);
//   isLike.value = !isLike.value
// } 
</script>

<style lang="scss">
page {
  background-color: #f6f6f6 !important;
}

.quesInfo {
  padding: 25rpx;
  border-bottom: 0.3rpx solid rgba(204, 204, 204, 0.4);
  background-color: #fff;

  .titleDetail {
    font-size: 35rpx;
    font-weight: 800;
  }

  .interAndCtrl {
    display: flex;
    justify-content: space-between;

    .interDetail {
      display: inline-block;
      margin-top: 20rpx;
      font-size: 26rpx;
      color: #777;

      .ansDataDetail {
        margin-right: 10rpx;
      }

      .askDataDetail {}
    }

    .btnDetail {
      display: inline-block;
      margin-top: 20rpx;

      .askBtn {
        display: inline-block;
        width: 100rpx;
        height: 40rpx;
        font-size: 20rpx;
        line-height: 40rpx;
        padding: 1rpx;
        background-color: rgba(254, 234, 225, 0.8);
        color: #f66d27;
      }

      .ansBtn {
        display: inline-block;
        width: 100rpx;
        height: 40rpx;
        font-size: 20rpx;
        line-height: 40rpx;
        padding: 1rpx;
        margin-left: 10rpx;
        background-color: rgba(254, 234, 225, 0.8);
        color: #f66d27;
      }
    }
  }
}

.ansList {
  .ansCard {
    .bottomAnsList {
      margin-top: 30rpx;
      display: flex;
      align-items: center;
      padding: 20rpx 30rpx;
      justify-content: space-between;
      background-color: #fff;
      position: relative;
      padding-bottom: 34rpx;
      padding-left: 40rpx;

      .leftAnsList {
        display: inline-block;

        .infoAnsList {
          display: flex;
          align-items: center;

          .avatarAnsList {
            display: flex;
            align-items: center;

            image {
              display: inline-block;
              width: 50rpx;
              height: 50rpx;
              border-radius: 40rpx;
            }
          }

          .nicknameAnsList {
            display: inline-block;
            font-size: 26rpx;
            color: rgb(102, 102, 102);
            margin-left: 10rpx;
          }
        }

        .detailAnsList {
          margin-top: 10rpx;
          width: 450rpx;
          font-size: 27rpx;
          font-weight: 500;
          margin-right: 50rpx;
        }

        .detailAnsList2 {
          margin-top: 10rpx;
          font-size: 27rpx;
          font-weight: 500;
        }

        .state2Info {
          display: flex;
          justify-content: space-between;
          margin-top: 20rpx;
        }

        .interAnsList {
          font-size: 27rpx;
          color: #777;
          position: absolute;
          top: 298rpx;
          left: 40rpx;

          .sameQesAnsList {
            margin-right: 10rpx;
          }
        }

        .interAnsList2 {
          font-size: 27rpx;
          color: #777;
          display: inline-block;

          .sameQesAnsList {
            margin-right: 10rpx;
          }
        }

        .ansTagAnsList2 {
          position: relative;
          display: inline-block;
          position: absolute;
          bottom: 25rpx;
          right: 49rpx;

          .comAnsDetail {
            margin-right: 10rpx;

            image {
              width: 30rpx;
              height: 30rpx;
            }
          }

          .likeAnsDetail {
            image {
              width: 40rpx;
              height: 40rpx;
            }
          }
        }
      }

      .rightAnsList {
        // display: inline-block;
        margin-top: 66rpx;

        image {
          width: 170rpx;
          height: 170rpx;
        }

        .ansTagAnsList {
          position: relative;
          margin-left: 100rpx;
          top: 32rpx;
          left: 29rpx;

          .comAnsDetail {
            margin-right: 10rpx;

            image {
              width: 30rpx;
              height: 30rpx;
            }
          }

          .likeAnsDetail {
            image {
              width: 40rpx;
              height: 40rpx;
            }
          }
        }
      }
    }

    .bottomAnsList:first-child {
      margin-top: 0rpx;
    }
  }
}
</style>
