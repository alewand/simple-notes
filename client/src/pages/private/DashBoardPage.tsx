import { Link } from "react-router-dom";
import Background from "../../components/common/Background";
import SideBar from "../../components/common/SideBar";
import { dashBoardPageContent } from "../../constants/dashBoardPageOptions";
import { getSideBarOptions } from "../../constants/sideBarOptions";
import Logo from "../../components/common/Logo";

function DashboardPage() {
  return (
    <div className="flex flex-col text-center">
      <Background>
        <SideBar options={getSideBarOptions("Dashboard")} />
        <div className="ml-12 sm:ml-16">
          <div className="flex flex-col items-center justify-center h-screen px-6">
            <Logo size="lg" />
            <h1 className="text-xl sm:text-2xl md:text-3xl font-bold mt-6 text-white drop-shadow-xl">
              {dashBoardPageContent.title}
            </h1>
            <h2 className="text-lg sm:text-xl md:text-2xl font-semibold mt-4 text-white drop-shadow-lg">
              {dashBoardPageContent.description}
            </h2>
            <Link
              key="/notes"
              to="/notes"
              className="mt-6 px-6 py-3 bg-blue-600 text-white rounded-lg text-lg font-semibold hover:bg-blue-700 transition-all duration-300"
            >
              {dashBoardPageContent.buttonText}
            </Link>
          </div>
        </div>
      </Background>
    </div>
  );
}

export default DashboardPage;
