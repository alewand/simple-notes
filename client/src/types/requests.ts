export interface LoginFormData {
  nicknameOrEmail: string;
  password: string;
}

export interface ConfirmFormData {
  nickname: string;
  password: string;
}

export interface ResetPasswordFormData {
  oldPassword: string;
  newPassword: string;
}

export interface RegisterFormData {
  nickname: string;
  email: string;
  password: string;
}
