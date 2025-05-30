import type {
  CommonResponse,
  NoteResponse,
  NotesResponse,
} from "../types/responses";
import api, { handleApiError } from "./api";

export const getNoteById = async (noteId: string): Promise<NoteResponse> => {
  try {
    const response = await api.get<NoteResponse>(`/api/notes/get/${noteId}`);
    return response.data;
  } catch (error: unknown) {
    throw handleApiError(
      error,
      "Wystąpił błąd podczas pobierania notatki. Spróbuj ponownie.",
    );
  }
};

export const getUserNotes = async (): Promise<NotesResponse> => {
  const response = await api.get<NotesResponse>("/api/notes/get-all");
  return response.data;
};

export const createNote = async (
  title: string,
  content: string,
): Promise<CommonResponse> => {
  try {
    const reponse = await api.post<CommonResponse>("/api/notes/create", {
      title,
      content,
    });
    return reponse.data;
  } catch (error: unknown) {
    throw handleApiError(
      error,
      "Wystąpił błąd podczas tworzenia notatki. Spróbuj ponownie.",
    );
  }
};

export const updateNote = async (
  noteId: string,
  title: string,
  content: string,
): Promise<CommonResponse> => {
  try {
    const response = await api.post<CommonResponse>(
      `/api/notes/update/${noteId}`,
      {
        title,
        content,
      },
    );
    return response.data;
  } catch (error: unknown) {
    throw handleApiError(
      error,
      "Wystąpił błąd podczas aktualizacji notatki. Spróbuj ponownie.",
    );
  }
};

export const deleteNote = async (noteId: string): Promise<CommonResponse> => {
  try {
    const response = await api.delete<CommonResponse>(
      `/api/notes/delete/${noteId}`,
    );
    return response.data;
  } catch (error: unknown) {
    throw handleApiError(
      error,
      "Wystąpił błąd podczas usuwania notatki. Spróbuj ponownie.",
    );
  }
};
