import "./App.css";
import { TodoList } from "./TodoList";
import { QueryClient, QueryClientProvider, useQuery } from "react-query";
import * as D from "io-ts/Decoder";
import { TodoDecoder } from "./types";

const queryClient = new QueryClient();

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <MainPage />
    </QueryClientProvider>
  );
}

function MainPage(): JSX.Element {
  const { isLoading, error, data } = useQuery("repoTodos", () =>
    fetch("http://localhost:8081/todos").then(async (res) => {
      const json = await res.json();
      const arr = await D.array(TodoDecoder).decode(json);
      return arr._tag === "Left" ? [] : arr.right;
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
