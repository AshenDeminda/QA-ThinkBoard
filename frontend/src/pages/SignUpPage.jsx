import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../lib/axios";
import toast from "react-hot-toast";

const SignUpPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const res = await api.post("/auth/signup", { email, password });
      localStorage.setItem("token", res.data.token);
      navigate("/");
    } catch (e) {
      toast.error("Sign up failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center">
      <div className="card w-full max-w-md bg-base-100">
        <div className="card-body">
          <h2 className="card-title">Sign Up</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-control">
              <label className="label"><span className="label-text">Email</span></label>
              <input className="input input-bordered" type="email" value={email} onChange={(e)=>setEmail(e.target.value)} required />
            </div>
            <div className="form-control mt-3">
              <label className="label"><span className="label-text">Password</span></label>
              <input className="input input-bordered" type="password" value={password} onChange={(e)=>setPassword(e.target.value)} required />
            </div>
            <div className="card-actions mt-6">
              <button className="btn btn-primary w-full" disabled={loading}>{loading?"Signing up...":"Sign Up"}</button>
            </div>
          </form>
          <div className="mt-4 text-sm">Have an account? <Link to="/signin" className="link">Sign in</Link></div>
        </div>
      </div>
    </div>
  );
};

export default SignUpPage;


