import { useEffect, useState } from "react";
import type { User } from "../../types/others";
import { getUserFromStorage } from "../../utils/userAuthentication";
import { twoWayRoleMap } from "../../constants/maps";
import EditableField from "../inputs/EditableField";
import { getDateFormatter } from "../../utils/formatters";
import { getUserFromAPI } from "../../api/api.user";
import { getUserNotes } from "../../api/api.notes";

/**
 * AccountInfo component displays user information and allows editing it (avatar, name, surname, nickname, email).
 */
function AccountInfo() {
  const [isLoading, setIsLoading] = useState(true);
  const [userInfo, setUserInfo] = useState<User>(
    getUserFromStorage() ?? {
      nickname: "",
      email: "",
      role: "",
      createdAt: "",
    },
  );

  const [notesAmount, setNotesAmount] = useState(0);

  useEffect(() => {
    const fetchUser = async () => {
      setIsLoading(true);
      const user = await getUserFromAPI();
      try {
        const notes = await getUserNotes();
        setNotesAmount(notes.notes.length);
      } catch {
        setNotesAmount(0);
      }
      const mappedRole = twoWayRoleMap.to(user.role);
      if (mappedRole) user.role = mappedRole;
      setUserInfo(user);
      setIsLoading(false);
    };
    fetchUser();
  }, []);

  const onConfirm = (fieldName: string, newValue: string) =>
    setUserInfo((prev) => ({
      ...prev,
      [fieldName]: newValue,
    }));

  return (
    <div className="flex flex-col items-center justify-center gap-4 mt-5">
      <div className="w-full max-w-4xl flex flex-col gap-4 px-4 mt-1 min-h-[500px]">
        <EditableField
          label="Nick"
          name="nickname"
          value={userInfo.nickname}
          onConfirm={onConfirm}
          isLoading={isLoading}
        />
        <EditableField
          label="Mail"
          name="email"
          value={userInfo.email}
          onConfirm={onConfirm}
          isLoading={isLoading}
        />
        <EditableField
          label="Typ Konta"
          name="role"
          value={userInfo.role}
          onConfirm={onConfirm}
          isLoading={isLoading}
          type="text"
          editable={false}
          inputMap={twoWayRoleMap}
        />
        <EditableField
          label="Utworzono Konto"
          name="createdAt"
          value={
            getDateFormatter(userInfo.createdAt)?.getDMYWithTime() ??
            "Nieprawidłowa Data"
          }
          onConfirm={onConfirm}
          isLoading={isLoading}
          type="text"
          editable={false}
        />
        <EditableField
          label="Liczba Notatek"
          name="notesCount"
          value={notesAmount.toString()}
          onConfirm={onConfirm}
          isLoading={isLoading}
          type="text"
          editable={false}
        ></EditableField>
      </div>
    </div>
  );
}

export default AccountInfo;
