import "./App.css";
import { TodoList } from "./TodoList";
import { QueryClient, QueryClientProvider, useQuery } from "react-query";
import * as D from "io-ts/Decoder";

const queryClient = new QueryClient();

export interface Todo {
  id: number;
  description: string;
  done: boolean;
}
export const TodoD = D.struct({
  id: D.number,
  description: D.string,
  done: D.boolean,
});
export const TodoArray: D.Decoder<unknown, Array<Todo>> = D.array(TodoD);
export const Jo = D.struct({
  "::": TodoArray,
});
export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Example />
    </QueryClientProvider>
  );
}

function Example(): JSX.Element {
  const { isLoading, error, data } = useQuery("repoData", () =>
    fetch("http://localhost:8081/todos/user").then(async (res) => {
      const json = await res.json();
      console.log(json);
      const arr = await Jo.decode(json);
      console.log(arr);
      return arr._tag === "Left" ? [] : arr.right["::"];
    })
  );

  if (isLoading) return <div>Loading...</div>;

  if (error) return <div>An error has occurred: {error}</div>;

  return (
    <div>
      <div className="App">
        <TodoList todos={data ?? []} />
      </div>
    </div>
  );
}
