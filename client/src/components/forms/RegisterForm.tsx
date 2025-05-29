import React, { useState } from "react";
import { Link } from "react-router-dom";
import { register } from "../../api/api.auth";
import FormField from "../inputs/FormField";
import type { RegisterFormData } from "../../types/requests";
import { useAlert } from "../../contexts/AlertContext";

/**
 * RegisterForm component that handles user registration.
 */
function RegisterForm() {
  const [isLoading, setIsLoading] = useState(false);
  const [registerFormData, setRegisterFormData] = useState<RegisterFormData>({
    nickname: "",
    email: "",
    password: "",
  });

  const [confirmPassword, setConfirmPassword] = useState<string>("");

  const { showAlert } = useAlert();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setRegisterFormData(
      (prev) =>
        ({
          ...prev,
          [name]: value,
        }) as RegisterFormData,
    );
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    if (registerFormData.password !== confirmPassword) {
      showAlert("Hasła nie są takie same!", "error");
      setIsLoading(false);
      return;
    }

    try {
      await register(registerFormData);
    } catch (error: any) {
      showAlert(error.message, "error");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="w-full px-3 mt-20 mb-5 sm:mt-0 sm:px-0 py-3">
      <form
        onSubmit={handleSubmit}
        className="flex flex-col justify-center items-center bg-white w-full max-w-md mx-auto p-3 sm:p-5 rounded-lg shadow-md gap-2"
      >
        <h1 className="text-lg sm:text-xl font-bold text-center text-gray-800 mb-2 w-full">
          Rejestracja
        </h1>

        <div className="flex flex-col gap-4 w-full">
          <FormField
            label="Nick"
            name="nickname"
            value={registerFormData.nickname}
            onChange={handleChange}
            required
            disabled={isLoading}
            placeholder="janek123"
          />

          <FormField
            label="Email"
            type="email"
            name="email"
            value={registerFormData.email}
            onChange={handleChange}
            required
            disabled={isLoading}
            placeholder="jan@clingclang.com"
          />

          <FormField
            label="Hasło"
            type="password"
            name="password"
            value={registerFormData.password}
            onChange={handleChange}
            required
            disabled={isLoading}
            placeholder="••••••••••••••••"
          />

          <FormField
            label="Powtóz Hasło"
            type="password"
            name="confirmPassword"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
            disabled={isLoading}
            placeholder="••••••••••••••••"
          />
        </div>

        <button
          type="submit"
          className="w-full mt-2 bg-blue-600 text-white py-2 px-3 text-sm rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-colors duration-200 font-medium"
          disabled={isLoading}
        >
          {isLoading ? "Rejestrowanie..." : "Zarejestruj się"}
        </button>

        <p className="text-center text-xs sm:text-sm text-gray-600 pt-1 mt-1">
          Masz już konto?{" "}
          <Link
            to="/login"
            className="ml-2 font-medium text-blue-600 hover:text-blue-500"
          >
            Zaloguj się!
          </Link>
        </p>
      </form>
    </div>
  );
}

export default RegisterForm;
