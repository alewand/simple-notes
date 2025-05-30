import type { Note, User } from "./others";

export interface CommonResponse {
  message: string;
}

export interface RefreshResponse {
  message: string;
  accessToken: string;
}

export interface AuthResponse {
  message: string;
  accessToken: string;
  user: User;
}

export interface NoteResponse {
  message: string;
  note: Note;
}

export interface NotesResponse {
  message: string;
  notes: Note[];
}
