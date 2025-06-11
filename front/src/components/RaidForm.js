import { useEffect, useState, useContext } from "react";
import AuthContext from "../AuthContext.js";

function RaidForm() {
  const { user } = useContext(AuthContext);

  const [combats, setCombats] = useState([]);
  const [raidplans, setRaidplans] = useState([]);
  const [roles, setRoles] = useState([]);
  const [leaderClasses, setLeaderClasses] = useState([]);

  const [selectedCombat, setSelectedCombat] = useState("");
  const [selectedRaidplan, setSelectedRaidplan] = useState("");
  const [selectedClasseLeader, setSelectedClasseLeader] = useState("");
  const [heure, setHeure] = useState("");

  const [compositionRoles, setCompositionRoles] = useState({});

  useEffect(() => {
    fetch("http://localhost:7070/combats")
      .then(res => res.json())
      .then(setCombats);

    fetch("http://localhost:7070/roles")
      .then(res => res.json())
      .then(data => {
        setRoles(data);
        const initComp = {};
        data.forEach(r => { initComp[r.id] = 0; });
        setCompositionRoles(initComp);
      });
  }, []);

  useEffect(() => {
    if (!user) return;
    fetch(`http://localhost:7070/joueurs/${user.id}/classes`)
      .then(res => res.json())
      .then(setLeaderClasses);
  }, [user]);

  useEffect(() => {
    if (!selectedCombat) {
      setRaidplans([]);
      setSelectedRaidplan("");
      return;
    }
    fetch(`http://localhost:7070/raidplans?combatId=${selectedCombat}`)
      .then(res => res.json())
      .then(setRaidplans);
  }, [selectedCombat]);

  const handleRoleChange = (roleId, value) => {
    setCompositionRoles(prev => ({
      ...prev,
      [roleId]: Number(value)
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!user) {
      alert("Vous devez être connecté pour créer un raid.");
      return;
    }

    const payload = {
      raid: {
        heure,
        idCombat: Number(selectedCombat),
        idRaidplan: Number(selectedRaidplan),
        idLeader: user.id
      },
      classeLeaderId: Number(selectedClasseLeader),
      compositionRoles: Object.entries(compositionRoles).map(([idRole, nombreJoueur]) => ({
        idRole: Number(idRole),
        nombreJoueur
      }))
    };

    const res = await fetch("http://localhost:7070/raids", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    if (res.ok) {
      alert("Raid créé avec succès !");
    } else {
      alert("Erreur lors de la création du raid");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Créer un raid</h2>

      <label>
        Heure du raid (ISO 8601):
        <input type="datetime-local" value={heure} onChange={e => setHeure(e.target.value)} required />
      </label>

      <label>
        Combat:
        <select value={selectedCombat} onChange={e => setSelectedCombat(e.target.value)} required>
          <option value="">-- Choisir un combat --</option>
          {combats.map(c => <option key={c.id} value={c.id}>{c.nom}</option>)}
        </select>
      </label>

      <label>
        Raidplan:
        <select value={selectedRaidplan} onChange={e => setSelectedRaidplan(e.target.value)} required disabled={!raidplans.length}>
          <option value="">-- Choisir un raidplan --</option>
          {raidplans.map(rp => <option key={rp.id} value={rp.id}>{rp.nom}</option>)}
        </select>
      </label>

      <label>
        Votre classe (leader):
        <select value={selectedClasseLeader} onChange={e => setSelectedClasseLeader(e.target.value)} required>
          <option value="">-- Choisir votre classe --</option>
          {leaderClasses.map(classe => (
            <option key={classe.id} value={classe.id}>{classe.nomClasse}</option>
          ))}
        </select>
      </label>

      <h3>Composition des rôles</h3>
      {roles.map(role => (
        <label key={role.id}>
          {role.nom} :
          <input
            type="number"
            min="0"
            value={compositionRoles[role.id] ?? 0}
            onChange={e => handleRoleChange(role.id, e.target.value)}
          />
        </label>
      ))}

      <button type="submit">Créer le raid</button>
    </form>
  );
}

export default RaidForm;
