export type MyAnswerResult = {
    code: number
    msg: string
    data: MyAnswerItem[]
  }
 
export type MyAnswerItem = {
    q_title:string
    q_id:number
}
  