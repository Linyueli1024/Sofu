export type RecommendItem = {
    //第一条回答对象
    ans: RecommendAnsItem
    //回答数
    ansnum: number
    //问题标题
    title: string
    // 问题id
    q_id: number
    // 同问数
    concern: number
}

export type RecommendAnsItem = {
    img_link: string
    nickname: string
    avatar: string
    info: string
}


