import { useEffect, useState, useContext } from "react";
import { Link } from "react-router-dom";
import AuthContext from "../AuthContext.js"; 

function RaidList() {
  const [raids, setRaids] = useState([]);
  const { user } = useContext(AuthContext);

  useEffect(() => {
    fetch("http://localhost:7070/raids")
      .then(res => res.json())
      .then(setRaids);
  }, []);

  return (
    <div>
      <h2>Liste des Raids</h2>
      <ul>
        {raids.map(r => (
          <li key={r.id}>
            <Link to={`/raids/${r.id}`}>{r.heure} - Raid #{r.id}</Link>
          </li>
        ))}
      </ul>

      {user && (
        <Link to="/raids/creer">Créer un nouveau raid</Link>
      )}
    </div>
  );
}

export default RaidList;
