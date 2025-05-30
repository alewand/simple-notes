import { useParams } from "react-router-dom";
import Background from "../../components/common/Background";
import SideBar from "../../components/common/SideBar";
import NoteExplorer from "../../components/unique/NoteExplorer";
import { getSideBarOptions } from "../../constants/sideBarOptions";

interface NotePageProps {
  addMode?: boolean;
}

function NotePage({ addMode = false }: NotePageProps) {
  const { noteId } = useParams();

  return (
    <div className="flex flex-col items-center justify-center">
      <Background>
        <SideBar options={getSideBarOptions("Notatki")} />
        <div className="ml-12 sm:ml-16 py-8 px-4">
          <h2 className="flex flex-row items-center justify-start gap-4 text-2xl font-bold mb-6 text-gray-800">
            <i
              className={addMode ? "fas fa-plus-circle" : "fas fa-sticky-note"}
            ></i>
            {addMode ? "Nowa Notatka" : "Notatka"}
          </h2>
          <hr className="my-6" />
          <div className="flex justify-center">
            <NoteExplorer
              noteId={noteId ? noteId : undefined}
              isInAddMode={addMode}
            />
          </div>
        </div>
      </Background>
    </div>
  );
}

export default NotePage;
