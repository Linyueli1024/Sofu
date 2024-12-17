import type { PostItem } from '@/types/post'
import { http } from '@/utils/http'

/**
 * 分类列表-小程序
 */
export const postChooseClassifyApi = (data: { qtitle:string, imglink:string, classifyid: Array<number>, questioninfo:string, userid:number}) => {
  return http<PostItem>({
    method: 'POST',
    url: '/post/question',
    data
  })
}
