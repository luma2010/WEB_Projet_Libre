import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function JoueurList() {
  const [joueurs, setJoueurs] = useState([]);

  useEffect(() => {
    fetch("http://localhost:7070/joueurs")
      .then(res => res.json())
      .then(data => setJoueurs(data));
  }, []);

  return (
    <div>
      <h2>Joueurs</h2>
      <ul>
        {joueurs.map(j => (
          <li key={j.id}>
            <Link to={`/joueurs/${j.id}`}>{j.pseudo}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default JoueurList;
