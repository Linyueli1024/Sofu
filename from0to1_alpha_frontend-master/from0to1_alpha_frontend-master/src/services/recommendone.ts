import type { RecommendOneItem } from '@/types/recommendone'
import { http } from '@/utils/http'

/**
 * 分类列表-小程序
 */
export const postRecommendOneApi = (data: { tag: number }) => {
  return http<RecommendOneItem[]>({
    method: 'POST',
    url: '/recommend/requestOne',
    data
  })
}
