import { getTwoWayMap } from "../utils/formatters";

export const roleMap = new Map<string, string>([
  ["USER", "Użytkownik"],
  ["ADMIN", "Administrator"],
]);

export const twoWayRoleMap = getTwoWayMap<string, string>(roleMap);
