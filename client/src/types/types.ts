import * as D from "io-ts/Decoder";

export interface Todo {
  id: number;
  description: string;
  done: boolean;
}
export const TodoDecoder = D.struct({
  id: D.number,
  description: D.string,
  done: D.boolean,
});
