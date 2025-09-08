import { Link, useNavigate } from "react-router-dom";
import { PlusIcon } from "lucide-react";

const Navbar = () => {
  const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem('token');
    navigate('/signin');
  };

  return (
    <header className="bg-base-300 border-b border-base-content/10">
      <div className="mx-auto max-w-6xl p-4">
        <div className="flex items-center justify-between">
          <h1 className="text-3xl font-bold text-primary font-mono tracking-tight">ThinkBoard</h1>
          <div className="flex items-center gap-4">
            {token && (
              <Link to={"/create"} className="btn btn-primary">
                <PlusIcon className="size-5" />
                <span>New Note</span>
              </Link>
            )}
            {!token ? (
              <>
                <Link to="/signin" className="btn">Sign In</Link>
                <Link to="/signup" className="btn btn-outline">Sign Up</Link>
              </>
            ) : (
              <button className="btn btn-ghost" onClick={logout}>Logout</button>
            )}
          </div>
        </div>
      </div>
    </header>
  );
};
export default Navbar;