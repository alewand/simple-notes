import { Navigate, Route, Routes } from "react-router-dom";
import DashboardPage from "../pages/private/DashBoardPage";
import NotFoundPage from "../pages/NotFoundPage";
import AccountSettings from "../pages/private/AccountSettings";
import NotesListPage from "../pages/private/NotesListPage";
import AddNotePage from "../pages/private/NotePage";

function PrivateRouter() {
  return (
    <Routes>
      <Route path="/" element={<DashboardPage />} />
      <Route path="/dashboard" element={<Navigate to="/" replace />} />
      <Route path="/account" element={<AccountSettings />} />
      <Route path="/notes" element={<NotesListPage />} />
      <Route path="/notes/create" element={<AddNotePage addMode />} />
      <Route path="/notes/:noteId" element={<AddNotePage />} />
      <Route path="/login" element={<Navigate to="/" replace />} />
      <Route path="/register" element={<Navigate to="/" replace />} />

      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
}

export default PrivateRouter;
