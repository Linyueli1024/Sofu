import type { PersonResult } from '@/types/person'
import { http } from '@/utils/http'

export const postPersonApi = (data: {user_id: number}) => {
    return http<PersonResult>({
      method: 'POST',
      url: '/user/person',
      data,
    })
  }