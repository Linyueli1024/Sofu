export type PersonResult = {
    code: number
    msg: string
    data: PersonData
  }
 
export type PersonData = {
    concernNum:number
    nickname:string
    answerNum:number
    questionNum:number
    avatar:string
}
  