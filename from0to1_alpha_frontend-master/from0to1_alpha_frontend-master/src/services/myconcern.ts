import type { MyConcernResult } from '@/types/myconcern'
import { http } from '@/utils/http'

export const postMyConcernApi = (data: {user_id: number}) => {
    return http<MyConcernResult>({
      method: 'POST',
      url: '/user/concern',
      data,
    })
  }