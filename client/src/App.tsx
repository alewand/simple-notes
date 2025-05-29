import Alert from "./components/common/Alert";
import { BrowserRouter } from "react-router-dom";
import Redicretor from "./components/common/Redirector";
import { AlertProvider } from "./contexts/AlertContext";
import RouterController from "./routes/RouterController";

function App() {
  return (
    <AlertProvider>
      <Alert />
      <BrowserRouter>
        <Redicretor />
        <RouterController />
      </BrowserRouter>
    </AlertProvider>
  );
}

export default App;
