import type { AddLikeItem } from '@/types/addLike'
import { http } from '@/utils/http'

/**
 * 分类列表-小程序
 */
export const postAddLikeApi = (data: { ans_id: number }) => {
  return http<AddLikeItem>({
    method: 'POST',
    url: '/answer/like',
    data
  })
}
