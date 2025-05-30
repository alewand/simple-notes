import Background from "../../components/common/Background";
import SideBar from "../../components/common/SideBar";
import NotesList from "../../components/unique/NotesList";
import { getSideBarOptions } from "../../constants/sideBarOptions";

function NotesListPage() {
  return (
    <div className="flex flex-col items-center justify-center">
      <Background>
        <SideBar options={getSideBarOptions("Notatki")} />
        <div className="ml-12 sm:ml-16 py-8 px-4">
          <h2 className="flex flex-row items-center justify-start gap-4 text-2xl font-bold mb-6 text-gray-800">
            <i className="fas fa-sticky-note"></i>Twoje Notatki
          </h2>
          <hr className="my-6" />
          <NotesList />
        </div>
      </Background>
    </div>
  );
}

export default NotesListPage;
