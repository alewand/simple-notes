import type { ReactNode } from "react";
import bgImage from "../../assets/bg.png";

interface BackgroundProps {
  children?: ReactNode;
}

/**
 * Background component that displays a full-screen background.
 */
function Background({ children }: BackgroundProps) {
  return (
    <div
      className="w-full min-h-screen bg-fixed bg-cover bg-center relative"
      style={{ backgroundImage: `url(${bgImage})` }}
    >
      <div className={`backdrop-blur-sm bg-white/65 w-full min-h-screen`}>
        {children}
      </div>
    </div>
  );
}

export default Background;
