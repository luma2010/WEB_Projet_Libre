import { useContext, useState } from "react";
import AuthContext from "../AuthContext.js";
import { useNavigate } from "react-router-dom";

function LoginForm() {
  const [pseudo, setPseudo] = useState("");
  const [password, setPassword] = useState("");
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await fetch("http://localhost:7070/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ pseudo, password }),
    });
    if (res.ok) {
      const data = await res.json();
      login(data); 
      navigate(`/joueurs/${data.id}`); 
    } else {
      alert("Erreur de connexion");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input placeholder="Pseudo" value={pseudo} onChange={e => setPseudo(e.target.value)} />
      <input type="password" placeholder="Mot de passe" value={password} onChange={e => setPassword(e.target.value)} />
      <button type="submit">Se connecter</button>
    </form>
  );
}

export default LoginForm;
