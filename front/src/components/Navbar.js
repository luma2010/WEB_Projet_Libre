import { useContext } from "react";
import AuthContext from "../AuthContext.js";
import { Link } from "react-router-dom";

function Navbar() {
  const { user, logout } = useContext(AuthContext);

  return (
    <nav>
      <Link to="/joueurs">Liste des joueurs</Link>
      <Link to="/raids">Liste des raids</Link> {/* lien raids ajouté */}
      {user ? (
        <>
          <span>{user.pseudo}</span>
          <button onClick={logout}>Déconnexion</button>
          <Link to={`/joueurs/${user.id}`}>Mon profil</Link>
        </>
      ) : (
        <Link to="/login">Connexion</Link>
      )}
      <Link to="/creer-joueur">S'inscrire</Link>
      
    </nav>
  );
}

export default Navbar;
