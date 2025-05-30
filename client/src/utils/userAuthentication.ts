import { getUserFromAPI } from "../api/api.user";
import type { User } from "../types/others";

/**
 * Gets user from local storage.
 * @returns user object or null if not found or invalid
 */
export const getUserFromStorage = (): User | null => {
  const userString = localStorage.getItem("user");
  let user;
  try {
    user = userString ? JSON.parse(userString) : null;
  } catch {
    return null;
  }

  return user;
};

/**
 * Checks if user has all required properties.
 * @param user
 * @returns true if user is valid, false otherwise
 */
export const isUserValid = (user: User): boolean => {
  return !!(user && user.nickname && user.email && user.createdAt && user.role);
};

/**
 * Gets user from local storage or API if not found or corrupted.
 * @returns user object
 */
export const getUser = async (): Promise<User> => {
  let user = getUserFromStorage();
  if (!user || !isUserValid(user)) {
    user = await getUserFromAPI();
    localStorage.setItem("user", JSON.stringify(user));
  }

  return user;
};

/**
 * Checks if user is authenticated by checking local storage.
 * Only for initial check (first component render).
 * @returns true if user is authenticated, false otherwise
 */
export const isUserAuthenticatedByStorage = (): boolean => {
  const accessToken = localStorage.getItem("accessToken");
  const user = getUserFromStorage();
  return !!(accessToken && user);
};
