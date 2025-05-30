import type { User } from "../types/others";
import type { ResetPasswordFormData } from "../types/requests";
import type { CommonResponse } from "../types/responses";
import api, { handleApiError } from "./api";

export const getUserFromAPI = async (): Promise<User> => {
  const response = await api.get<User>("/api/user/get");
  const user = response.data;
  localStorage.setItem("user", JSON.stringify(user));
  return user;
};

export const changeUserInfoField = async (
  name: string,
  value: string,
): Promise<CommonResponse> => {
  try {
    const response = await api.post<CommonResponse>("/api/user/change-info", {
      name: name,
      value: value,
    });
    return response.data;
  } catch (error: unknown) {
    throw handleApiError(
      error,
      "Wystąpił błąd zmiany danych użytkownika. Spróbuj ponownie.",
    );
  }
};

export const changePassword = async (
  data: ResetPasswordFormData,
): Promise<CommonResponse> => {
  try {
    const response = await api.post<CommonResponse>(
      "/api/user/change-password",
      data,
    );
    return response.data;
  } catch (error: unknown) {
    throw handleApiError(
      error,
      "Wystąpił błąd zmiany hasła. Spróbuj ponownie.",
    );
  }
};
