import { Navigate } from "react-router-dom";
import { isAuthenticated } from "../lib/utils";

const AuthRoute = ({ children }) => {
  return isAuthenticated() ? <Navigate to="/home" replace /> : children;
};

export default AuthRoute;