import { useEffect, useState } from "react";
import type { Note } from "../../types/others";
import { getUserNotes } from "../../api/api.notes";
import { useNavigate } from "react-router-dom";
import { getDateFormatter } from "../../utils/formatters";

/**
 * NotesList component displays a list of notes for the user.
 */
function NotesList() {
  const [isLoading, setIsLoading] = useState(true);
  const [notes, setNotes] = useState<Note[]>([]);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchNotes = async () => {
      try {
        const response = await getUserNotes();
        setNotes(response.notes);
      } catch {
        setNotes([]);
      } finally {
        setIsLoading(false);
      }
    };

    fetchNotes();
  }, []);

  return (
    <div className="flex flex-col w-full px-2">
      <div className="flex justify-start mb-6">
        <button
          className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-5 py-2 rounded-lg shadow transition"
          onClick={() => navigate(`/notes/create`)}
        >
          <i className="fas fa-plus"></i>
          Nowa Notatka
        </button>
      </div>

      {isLoading ? (
        <div className="flex flex-col items-center justify-center flex-grow mt-20 h-[50vh] text-center">
          <p className="text-gray-500 text-4xl font-bold uppercase">
            Ładowanie notatek...
          </p>
        </div>
      ) : notes.length === 0 ? (
        <div className="flex flex-col items-center justify-center flex-grow mt-20 h-[50vh] text-center">
          <p className="text-gray-500 text-4xl font-bold uppercase">
            Brak notatek
          </p>
        </div>
      ) : (
        <div className="flex justify-center">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 w-full max-w-6xl">
            {notes.map((note) => (
              <div
                key={note.noteId}
                className="bg-white border border-gray-200 rounded-xl p-5 shadow-md hover:shadow-lg transition cursor-pointer"
                onClick={() => navigate(`/notes/${note.noteId}`)}
              >
                <h3 className="text-xl font-semibold text-gray-800 mb-3">
                  {note.title.length > 25
                    ? `${note.title.substring(0, 25)}...`
                    : note.title}
                </h3>
                <p className="text-gray-600 text-sm leading-relaxed">
                  {note.content.length > 25
                    ? `${note.content.substring(0, 25)}...`
                    : note.content}
                </p>
                <div className="text-xs text-gray-400 mt-3 space-y-1">
                  <div className="flex items-center gap-1">
                    <i className="fas fa-clock mr-1"></i>
                    <span>
                      {getDateFormatter(note.createdAt)!.getDMYWithTime() ??
                        "Nieprawidłowa Data"}
                    </span>
                  </div>
                  <div className="flex items-center gap-1">
                    <i className="fas fa-pen mr-1"></i>
                    <span>
                      {getDateFormatter(note.updatedAt)!.getDMYWithTime() ??
                        "Nieprawidłowa Data"}
                    </span>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

export default NotesList;
