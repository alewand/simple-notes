interface NoteButtonProps {
  name: string;
  icon: string;
  color: "blue" | "yellow" | "gray" | "red";
  onClick: () => void;
}

const colorMap = {
  blue: "bg-blue-600 hover:bg-blue-700",
  yellow: "bg-yellow-200 hover:bg-yellow-300",
  gray: "bg-gray-300 hover:bg-gray-400",
  red: "bg-red-600 hover:bg-red-700",
};

function NoteButton({ name, icon, color, onClick }: NoteButtonProps) {
  return (
    <button
      type="button"
      onClick={onClick}
      className={`flex items-center gap-2 ${colorMap[color]} text-white px-5 py-2 rounded-lg shadow transition`}
    >
      <i className={icon}></i>
      {name}
    </button>
  );
}

export default NoteButton;
