import { render, screen } from "@testing-library/react";
import App from "./App";

test("renders todo list", () => {
  render(<App />);
  const loading = screen.getByText("Loading...");
  expect(loading).toBeInTheDocument();
});
