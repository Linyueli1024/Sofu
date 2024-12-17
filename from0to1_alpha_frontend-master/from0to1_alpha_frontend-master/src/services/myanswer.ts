import type { MyAnswerResult } from '@/types/myanswer'
import { http } from '@/utils/http'

export const postMyAnswerApi = (data: {user_id: number}) => {
    return http<MyAnswerResult>({
      method: 'POST',
      url: '/user/answer',
      data,
    })
  }