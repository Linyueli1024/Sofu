import type { DetailItem } from '@/types/detail'
import { http } from '@/utils/http'

/**
 * 分类列表-小程序
 */
export const postDetailApi = (data: { q_id: number }) => {
  return http<DetailItem>({
    method: 'POST',
    url: '/detail',
    data
  })
}
