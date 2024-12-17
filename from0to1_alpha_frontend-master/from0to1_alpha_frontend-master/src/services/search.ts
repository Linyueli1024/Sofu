import type { SearchItem } from '@/types/search'
import { http } from '@/utils/http'

/**
 * 分类列表-小程序
 */
export const postSearchApi = (data: { keyword: string }) => {
  return http<SearchItem>({
    method: 'POST',
    url: '/search',
    data
  })
}
