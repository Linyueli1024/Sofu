import type { HotResult } from '@/types/hot'
import { http } from '@/utils/http'

export const getHotApi = () => {
    return http<HotResult>({
      method: 'GET',
      url: '/trendtopic'
    })
  }