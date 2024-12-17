import type { RegisterResult } from '@/types/member'
import { http } from '@/utils/http'

type RegisterParams = {
  openid: string
  avatar: string
  nickname: string
  invitecode: string
}
export const postRegisterApi = (data: RegisterParams) => {
    return http<RegisterResult>({
      method: 'POST',
      url: '/user/register',
      data,
    })
  }