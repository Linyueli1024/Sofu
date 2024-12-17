export type HotResult = {
  code: number
  msg: string
  data: HotItem[]
}

export type HotItem = {
  q_title:string
  hotdegree:number
  q_id:number
}
