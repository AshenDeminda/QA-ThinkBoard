import { Navigate } from "react-router-dom";
import { isAuthenticated } from "../lib/utils";

const ProtectedRoute = ({ children }) => {
  return isAuthenticated() ? children : <Navigate to="/signin" replace />;
};

export default ProtectedRoute;