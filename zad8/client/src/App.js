import React, { useEffect, useState } from "react";
import { getUser } from "./api";

function App() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get("token");
    if (token) {
      localStorage.setItem("token", token);
      window.history.replaceState(null, "", "/");
    }

    const savedToken = localStorage.getItem("token");
    if (savedToken) {
      getUser(savedToken)
          .then((res) => setUser(res))
          .catch(() => localStorage.removeItem("token"));
    }
  }, []);



  const handleLogin = () => {
    window.location.href = "http://localhost:5000/auth/google";
  };

  return (
      <div>
        <h1>auth-simple-app</h1>
        {user ? (
            <div>
              <p>Logged in with ID: {user.userId}</p>
            </div>
        ) : (
            <button onClick={handleLogin}>Log in via Google</button>
        )}
      </div>

  );
}

export default App;
