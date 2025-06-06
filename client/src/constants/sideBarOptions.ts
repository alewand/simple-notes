import { logout } from "../api/api.auth";

export interface SideBarOption {
  name: string;
  icon: string;
  link?: string;
  onClick?: () => void;
  active?: boolean;
}

/**
 * Global options for the sidebar component.
 */
const options: SideBarOption[] = [
  { name: "Dashboard", icon: "fas fa-home", link: "/dashboard" },
  {
    name: "Konto",
    icon: "fas fa-user",
    link: "/account",
  },
  {
    name: "Notatki",
    icon: "fas fa-sticky-note",
    link: "/notes",
  },
  {
    name: "Wyloguj",
    icon: "fas fa-sign-out-alt",
    onClick: async () => await logout(),
  },
];

/**
 * Function to get the sidebar options.
 * @param activeOption - The name of the currently active option.
 * @returns The sidebar options with the active option marked.
 */
export const getSideBarOptions = (activeOption?: string) => {
  options.forEach((option) => {
    if (activeOption === option.name) option.active = true;
    else option.active = false;
  });

  return options;
};
