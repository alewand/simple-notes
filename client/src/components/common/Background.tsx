import type { ReactNode } from "react";

interface BackgroundProps {
  children?: ReactNode;
}

/**
 * Background component that displays a full-screen background.
 */
function Background({ children }: BackgroundProps) {
  return (
    <div className="w-full min-h-screen bg-fixed bg-cover bg-center relative bg-gray-100">
      {children}
    </div>
  );
}

export default Background;
