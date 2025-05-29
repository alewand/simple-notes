import { useEffect, useState } from "react";
import type { User } from "../../types/others";
import { getUser, getUserFromStorage } from "../../utils/userAuthentication";
import { twoWayRoleMap } from "../../constants/maps";
import EditableField from "../inputs/EditableField";
import { getDateFormatter } from "../../utils/formatters";

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

  useEffect(() => {
    const fetchUser = async () => {
      setIsLoading(true);
      const user = await getUser();
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
      <div className="w-full max-w-4xl grid grid-cols-1 sm:grid-cols-2 gap-4 px-4 mt-1">
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
          label="Data utworzenia konta"
          name="createdAt"
          value={
            getDateFormatter(userInfo.createdAt)?.getDMY() ??
            "Nieprawidłowa Data"
          }
          onConfirm={onConfirm}
          isLoading={isLoading}
          type="text"
          editable={false}
        />
      </div>
    </div>
  );
}

export default AccountInfo;
