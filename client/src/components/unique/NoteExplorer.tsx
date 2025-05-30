import { useEffect, useState } from "react";
import type { Note } from "../../types/others";
import {
  createNote,
  deleteNote,
  getNoteById,
  updateNote,
} from "../../api/api.notes";
import { useAlert } from "../../contexts/AlertContext";
import { useNavigate } from "react-router-dom";
import NoteButton from "../buttons/NoteButton";
import { getDateFormatter } from "../../utils/formatters";

interface NoteExplorerProps {
  noteId?: string;
  isInAddMode?: boolean;
}

/**
 * NoteExplorer component allows users to view, create, edit, and delete notes.
 * It fetches a note by its ID if provided, or initializes a new note in add mode.
 * Users can edit the note's title and content, save changes, or delete the note.
 */
function NoteExplorer({ noteId, isInAddMode = false }: NoteExplorerProps) {
  const { showAlert } = useAlert();
  const navigate = useNavigate();

  const [orginalNote, setOriginalNote] = useState<Note>({
    noteId: "",
    title: "",
    content: "",
    createdAt: "",
    updatedAt: "",
  });

  const [note, setNote] = useState<Note>({
    noteId: "",
    title: "",
    content: "",
    createdAt: "",
    updatedAt: "",
  });

  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [isInEditMode, setIsInEditMode] = useState<boolean>(false);

  useEffect(() => {
    const fetchNote = async () => {
      if (isInAddMode || !noteId) {
        setIsLoading(false);
        return;
      }

      try {
        const response = await getNoteById(noteId);
        setNote({ ...response.note });
        setOriginalNote({ ...response.note });
      } catch (error: any) {
        showAlert(error.message, "error");
        navigate("/notes");
      } finally {
        setIsLoading(false);
      }
    };

    fetchNote();
  }, [noteId, isInAddMode]);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => {
    const { name, value } = e.target;
    setNote((prevNote) => ({
      ...prevNote,
      [name]: value,
    }));
  };

  const handleSave = async () => {
    try {
      if (note.title === "") {
        showAlert("Tytuł notatki nie może być pusty.", "error");
        return;
      }

      const response = await createNote(note.title, note.content);
      showAlert(response.message, "success");
      navigate("/notes");
    } catch (error: any) {
      showAlert(error.message, "error");
    }
  };

  const handleUpdate = async () => {
    try {
      if (
        orginalNote.title === note.title &&
        orginalNote.content === note.content
      ) {
        showAlert("Nie dokonano żadnych zmian.", "info");
        setIsInEditMode(false);
        return;
      }
      const response = await updateNote(note.noteId, note.title, note.content);
      showAlert(response.message, "success");
      navigate("/notes");
    } catch (error: any) {
      showAlert(error.message, "error");
    }
  };

  const handleDelete = async () => {
    try {
      const response = await deleteNote(note.noteId);
      showAlert(response.message, "success");
      navigate("/notes");
    } catch (error: any) {
      showAlert(error.message, "error");
    }
  };

  return (
    <div className="flex flex-col gap-6 w-full overflow-hidden">
      <div>
        <label className="block text-gray-700 font-medium mb-2">Tytuł</label>
        <input
          name="title"
          type="text"
          className="w-full border border-gray-300 rounded px-4 py-2 focus:outline-none focus:ring focus:ring-blue-200"
          placeholder={isInAddMode ? "Wpisz tytuł notatki tutaj..." : ""}
          value={isLoading ? "Ładowanie..." : note.title}
          onChange={handleChange}
          disabled={!isInAddMode && !isInEditMode}
          required
        />
      </div>

      <div>
        <label className="block text-gray-700 font-medium mb-2">Treść</label>
        <textarea
          name="content"
          className="w-full border border-gray-300 rounded px-4 py-2 min-h-[400px] focus:outline-none focus:ring focus:ring-blue-200"
          placeholder={isInAddMode ? "Wpisz treść notatki tutaj..." : ""}
          value={isLoading ? "Ładowanie..." : note.content}
          onChange={handleChange}
          disabled={!isInAddMode && !isInEditMode}
        ></textarea>
      </div>
      {!isInAddMode && !isLoading && (
        <div className="text-xs text-gray-400 mt-2 self-end text-right">
          <div className="flex items-center gap-1 justify-end">
            <i className="fas fa-clock mr-1"></i>
            <span>
              {getDateFormatter(note.createdAt)!.getDMYWithTime() ??
                "Nieprawidłowa Data"}
            </span>
          </div>
          <div className="flex items-center gap-1 justify-end">
            <i className="fas fa-pen mr-1"></i>
            <span>
              {getDateFormatter(note.updatedAt)!.getDMYWithTime() ??
                "Nieprawidłowa Data"}
            </span>
          </div>
        </div>
      )}

      <div className="flex justify-center items-center gap-4">
        {isInAddMode || isInEditMode ? (
          <div className="flex flex-col sm:flex-row gap-4">
            <NoteButton
              name="Zapisz"
              icon="fas fa-save"
              color="blue"
              onClick={isInAddMode ? handleSave : handleUpdate}
            />

            <NoteButton
              name="Wyczyść"
              icon="fas fa-eraser"
              color="yellow"
              onClick={() => {
                if (note.title === "" && note.content === "") {
                  showAlert("Nie można wyczyścić pustej notatki.", "info");
                  return;
                }
                setNote((prevNote) => ({
                  ...prevNote,
                  title: "",
                  content: "",
                }));
                showAlert("Notatka została wyczyszczona.", "success");
              }}
            />

            <NoteButton
              name="Anuluj"
              icon="fas fa-times"
              color="gray"
              onClick={
                isInAddMode
                  ? () => navigate("/notes")
                  : () => {
                      setNote(orginalNote);
                      setIsInEditMode(false);
                    }
              }
            />
          </div>
        ) : (
          <div className="flex gap-4 flex-col sm:flex-row">
            <NoteButton
              name="Edytuj"
              icon="fas fa-edit"
              color="blue"
              onClick={() => setIsInEditMode(true)}
            />

            <NoteButton
              name="Usuń"
              icon="fas fa-trash"
              color="red"
              onClick={handleDelete}
            />

            <NoteButton
              name="Powrót"
              icon="fas fa-arrow-left"
              color="gray"
              onClick={() => navigate("/notes")}
            />
          </div>
        )}
      </div>
    </div>
  );
}

export default NoteExplorer;
