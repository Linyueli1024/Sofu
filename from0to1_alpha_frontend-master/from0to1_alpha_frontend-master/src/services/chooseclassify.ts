import type { ChooseClassifyResult } from '@/types/chooseclassify'
import { http } from '@/utils/http'

type ClassifyParams = {
  user_id:string
  classify_id:Array<number>
}
export const postClassifyApi = (data: ClassifyParams) => {
    return http<ChooseClassifyResult>({
      method: 'POST',
      url: '/user/register',
      data,
    })
  }