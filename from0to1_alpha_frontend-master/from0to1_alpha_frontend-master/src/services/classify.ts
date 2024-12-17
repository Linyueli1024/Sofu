import { http } from '@/utils/http'
import type { ClassifyResult } from '@/types/classify'

export const getClassifyApi = () => {
    return http<ClassifyResult>({
      method: 'GET',
      url: '/classify'
    })
  }