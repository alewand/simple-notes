import type { User } from "./others";

export interface CommonResponse {
  message: string;
}

export interface UserResponse {
  user: User;
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
