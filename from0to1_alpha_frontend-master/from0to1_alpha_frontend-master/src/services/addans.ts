import type { AddAnsItem } from '@/types/addans'
import { http } from '@/utils/http'

/**
 * 分类列表-小程序
 */
export const postAnsApi = (data: { q_id: number, img_link: string, ansInfo: string, user_id: number }) => {
    return http<AddAnsItem>({
        method: 'POST',
        url: '/answer/add',
        data
    })
}
