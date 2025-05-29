import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getUser } from "../../utils/userAuthentication";
import Logo from "./Logo";
import type { SideBarOption } from "../../constants/sideBarOptions";

export interface SideBarOptions {
  options: SideBarOption[];
}

/**
 * SideBar component for navigation in private pages.
 */
function SideBar({ options }: SideBarOptions) {
  const [isLoading, setIsLoading] = useState(true);
  const [isOpen, setIsOpen] = useState(false);
  const [userNickname, setUserNickname] = useState<string>("");

  // Load user nickname to the sidbebar top.
  useEffect(() => {
    const fetchUser = async () => {
      const currentUser = await getUser();
      const { nickname } = currentUser;
      setUserNickname(nickname);
      setIsLoading(false);
    };
    fetchUser();
  }, []);

  return (
    <>
      {/* Overlay */}
      {isOpen && (
        <div
          className="fixed inset-0 bg-black bg-opacity-30 z-20"
          onClick={() => setIsOpen(false)}
        />
      )}

      {/* Sidebar */}
      <div
        className={`fixed top-0 left-0 h-full bg-white shadow-lg z-30
        ${isOpen ? "w-56 sm:w-64" : "w-12 sm:w-16"}`}
      >
        {/* Header */}
        <div
          className={`flex items-center p-4 border-b 
          ${isOpen ? "justify-between" : "justify-center"}`}
        >
          {/* Logo and Toggle Button */}
          {isOpen ? (
            <>
              <button
                title="Zwiń Menu"
                onClick={() => setIsOpen(false)}
                className="ml-2 text-xl"
              >
                ✕
              </button>
              <Logo size="sm" />
            </>
          ) : (
            <button
              title="Rozwiń Menu"
              onClick={() => setIsOpen(true)}
              className="text-xl"
            >
              ☰
            </button>
          )}
        </div>

        {/* User Nickname */}
        {isOpen && (
          <div className="flex flex-col items-center py-4 border-b">
            <span className="text-base sm:text-lg text-gray-600 font-semibold">
              {isLoading ? "Ładowanie..." : `${userNickname}`}
            </span>
          </div>
        )}

        {/* Options */}
        <nav className={`flex flex-col justify-center mt-4 px-2`}>
          {options.map((option) =>
            option.link ? (
              <Link
                key={option.link}
                to={option.link}
                title={option.name}
                className={`flex items-center ${isOpen ? "gap-3 px-4" : "justify-center px-2"} py-2 rounded-md ${
                  option.active
                    ? "bg-blue-100 text-blue-600 font-semibold"
                    : "text-gray-700 hover:bg-gray-100"
                }`}
              >
                <i
                  className={`${option.icon} ${
                    isOpen ? "text-base sm:text-lg" : "text-sm sm:text-base"
                  }`}
                ></i>
                {isOpen && <span>{option.name}</span>}
              </Link>
            ) : (
              <button
                key={option.name}
                onClick={option.onClick}
                title={option.name}
                className={`flex items-center ${isOpen ? "gap-3 px-4" : "justify-center px-2"} py-2 rounded-md text-left text-gray-700 hover:bg-gray-100`}
              >
                <i
                  className={`${option.icon} ${
                    isOpen ? "text-base sm:text-lg" : "text-sm sm:text-base"
                  }`}
                ></i>
                {isOpen && <span>{option.name}</span>}
              </button>
            ),
          )}
        </nav>
      </div>
    </>
  );
}

export default SideBar;
