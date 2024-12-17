export type MyQuestionResult = {
    code: number
    msg: string
    data: MyQuestionItem[]
  }
 
export type MyQuestionItem = {
    q_title:string
    q_id:number
}
  