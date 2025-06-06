export interface User {
  nickname: string;
  email: string;
  role: string;
  createdAt: string;
}

export interface Note {
  noteId: string;
  title: string;
  content: string;
  createdAt: string;
  updatedAt: string;
}
