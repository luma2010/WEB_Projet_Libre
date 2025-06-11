import { useParams } from "react-router-dom";
import { useEffect, useState, useContext } from "react";
import AuthContext from "../AuthContext.js";

function RaidDetail() {
  const { id } = useParams();
  const { user } = useContext(AuthContext);

  const [raid, setRaid] = useState(null);
  const [combat, setCombat] = useState(null);
  const [raidplan, setRaidplan] = useState(null);
  const [leader, setLeader] = useState(null);
  const [roles, setRoles] = useState([]);
  const [joueurs, setJoueurs] = useState([]);
  const [classes, setClasses] = useState([]);
  const [userClasses, setUserClasses] = useState([]); 
  const [selectedClasseId, setSelectedClasseId] = useState("");

  useEffect(() => {
    fetch(`http://localhost:7070/raids/${id}`)
      .then(res => res.json())
      .then(data => {
        setRaid(data.raid);
        setRoles(data.roles);
        setJoueurs(data.joueurs);
      });
  }, [id]);

  useEffect(() => {
    if (!raid) return;
    fetch(`http://localhost:7070/combats/${raid.idCombat}`)
      .then(res => res.json())
      .then(setCombat);
    fetch(`http://localhost:7070/raidplans/${raid.idRaidplan}`)
      .then(res => res.json())
      .then(setRaidplan);
    fetch(`http://localhost:7070/joueurs/${raid.idLeader}`)
      .then(res => res.json())
      .then(setLeader);
  }, [raid]);

  useEffect(() => {
    fetch("http://localhost:7070/classes")
      .then(res => res.json())
      .then(setClasses);
  }, []);

  useEffect(() => {
    if (!user) {
      setUserClasses([]);
      return;
    }
    fetch(`http://localhost:7070/joueurs/${user.id}/classes`)
      .then(res => res.json())
      .then(data => {
        console.log("User classes:", data);
        setUserClasses(data)
      })
      .catch(() => setUserClasses([]));
  }, [user]);

  const getClasseName = (id) => {
    const c = classes.find(cl => cl.id === id);
    return c ? c.nom : `Classe #${id}`;
  };

  const getRoleName = (id) => {
    switch(id) {
      case 1: return "Tank";
      case 2: return "Heal";
      case 3: return "DPS";
      default: return `Rôle #${id}`;
    }
  };

  const handleInscription = async () => {
    if (!selectedClasseId) {
      alert("Veuillez choisir une classe.");
      return;
    }
    if (!user) {
      alert("Vous devez être connecté pour vous inscrire.");
      return;
    }
    console.log("Classe sélectionnée :", selectedClasseId);

    const res = await fetch(`http://localhost:7070/raids/${raid.id}/joueurs`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        idJoueur: user.id,
        idRaid: raid.id,
        idClasse: Number(selectedClasseId),
      }),
    });

    if (res.ok) {
      alert("Inscription réussie !");
      const res2 = await fetch(`http://localhost:7070/raids/${raid.id}`);
      const data = await res2.json();
      setJoueurs(data.joueurs);
    } else {
      alert("Erreur lors de l’inscription.");
    }
  };

  if (!raid || !combat || !raidplan || !leader) return <div>Chargement...</div>;

  return (
    <div>
      <h2>Raid à {raid.heure}</h2>
      <p><strong>Combat :</strong> {combat.nom}</p>
      <p><strong>Plan :</strong> <a href={raidplan.url} target="_blank" rel="noreferrer">{raidplan.nom}</a></p>
      <p><strong>Leader :</strong> {leader.pseudo}</p>

      <h3>Composition cible</h3>
      <ul>
        {roles.map(role => (
          <li key={role.id}>
            {getRoleName(role.idRole)} : {role.nombreJoueur} joueur(s)
          </li>
        ))}
      </ul>

      <h3>Joueurs inscrits</h3>
<ul>
  {joueurs.map(j => {
    const classe = classes.find(c => c.nom === j.nomClasse);
    return (
      <li key={j.id}>
        Joueur : {j.pseudoJoueur}, Classe : {j.nomClasse}
        {classe && (
          <img
            src={`/images/${classe.image}`}
            alt={classe.nom}
            style={{ width: "40px", marginLeft: "10px", verticalAlign: "middle" }}
          />
        )}
      </li>
    );
  })}
</ul>


      {user && (
        <div>
          <h3>S’inscrire au raid</h3>
<select value={selectedClasseId} onChange={e => setSelectedClasseId(e.target.value)}>
  <option value="">-- Choisissez votre classe --</option>
  {userClasses.map(c => (
    <option key={c.id} value={c.id}>{c.nomClasse}</option>
  ))}
</select>


          <button onClick={handleInscription}>S’inscrire</button>
        </div>
      )}
      {user && user.id === raid.idLeader && (
  <button
    onClick={async () => {
      if(window.confirm("Êtes-vous sûr de vouloir supprimer ce raid ?")) {
        const res = await fetch(`http://localhost:7070/raids/${raid.id}`, {
          method: "DELETE",
        });
        if (res.ok) {
          alert("Raid supprimé avec succès !");
          window.location.href = "/";
        } else {
          alert("Erreur lors de la suppression.");
        }
      }
    }}
  >
    Supprimer le raid
  </button>
)}

    </div>
  );
}

export default RaidDetail;
