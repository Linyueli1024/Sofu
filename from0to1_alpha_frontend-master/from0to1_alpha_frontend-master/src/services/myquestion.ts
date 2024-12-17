import type { MyQuestionResult } from '@/types/myquestion'
import { http } from '@/utils/http'

export const postMyQuestionApi = (data: {user_id: number}) => {
    return http<MyQuestionResult>({
      method: 'POST',
      url: '/user/question',
      data,
    })
  }