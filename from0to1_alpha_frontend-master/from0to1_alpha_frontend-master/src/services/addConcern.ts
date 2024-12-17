import type { AddConcernItem } from '@/types/addConcern'
import { http } from '@/utils/http'

/**
 * 分类列表-小程序
 */
export const postAddConcernApi = (data: { user_id: number , q_id: number }) => {
  return http<AddConcernItem>({
    method: 'POST',
    url: '/user/addConcern',
    data
  })
}
