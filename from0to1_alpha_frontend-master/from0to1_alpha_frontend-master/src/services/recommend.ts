import type { RecommendItem } from '@/types/recommend'
import { http } from '@/utils/http'

/**
 * 分类列表-小程序
 */
export const postRecommendApi = (data: { userid: number }) => {
  return http<RecommendItem[]>({
    method: 'POST',
    url: '/recommend/requestAll',
    data
  })
}
