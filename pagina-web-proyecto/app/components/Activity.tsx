// components/Card.tsx
import React, { useEffect, useState } from "react";
import Cookies from "js-cookie";

interface ActivityProps {
  id: string;
  image: string; // URL protegida
  title: string;
  date: string;
  onClick: () => void;
}

const Activity = ({ image, title, date, onClick }: ActivityProps) => {
  const [previewImage, setPreviewImage] = useState<string>("");

  useEffect(() => {
    const loadProtectedImage = async () => {
      if (!image) return;
      
      const token = Cookies.get("token");

      try {
        const response = await fetch(image, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!response.ok) {
          console.error("Error cargando imagen protegida");
          return;
        }

        const blob = await response.blob();
        const objectURL = URL.createObjectURL(blob);
        setPreviewImage(objectURL);
      } catch (err) {
        console.error("Error al cargar la imagen:", err);
      }
    };

    loadProtectedImage();
  }, [image]);

  return (
    <div
      className="bg-white shadow-md rounded-lg overflow-hidden w-full max-w-sm cursor-pointer hover:shadow-lg transition-shadow"
      onClick={onClick}
    >
      <div
        className="h-48 bg-cover bg-center"
        style={{
          backgroundImage: previewImage ? `url(${previewImage})` : "none",
          backgroundColor: previewImage ? "transparent" : "#6b7280",
        }}
      ></div>

      <div className="p-4">
        <h3 className="text-xl font-semibold text-gray-800 truncate">{title}</h3>
        <p className="text-sm text-gray-500 mt-2">{date}</p>
      </div>
    </div>
  );
};

export default Activity;
