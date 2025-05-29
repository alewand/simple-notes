import Background from "../../components/common/Background";
import SideBar from "../../components/common/SideBar";
import { getSideBarOptions } from "../../constants/sideBarOptions";

function NotesListPage() {
  return (
    <div className="flex flex-col items-center justify-center">
      <Background>
        <SideBar options={getSideBarOptions("Notatki")} />
        <div className="ml-12 sm:ml-16"></div>
      </Background>
    </div>
  );
}

export default NotesListPage;
