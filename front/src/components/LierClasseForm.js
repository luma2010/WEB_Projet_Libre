import { useEffect, useState } from "react";

function LierClasseForm() {
  const [joueurs, setJoueurs] = useState([]);
  const [classes, setClasses] = useState([]);
  const [idJoueur, setIdJoueur] = useState("");
  const [idClasse, setIdClasse] = useState("");
  const [niveau, setNiveau] = useState(0);  // nouveau état pour niveau

  useEffect(() => {
    fetch("http://localhost:7070/joueurs").then(res => res.json()).then(setJoueurs);
    fetch("http://localhost:7070/classes").then(res => res.json()).then(setClasses);
  }, []);

  const lier = async () => {
    if (idJoueur === "" || idClasse === "") {
      alert("Veuillez choisir un joueur et une classe.");
      return;
    }
    await fetch("http://localhost:7070/liaisons", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        idJoueur: Number(idJoueur),
        idClasse: Number(idClasse),
        niveau: Number(niveau)  
      }),
    });
    alert("Classe liée !");
  };

  return (
    <div>
      <h2>Lier une classe à un joueur</h2>
      <select onChange={e => setIdJoueur(e.target.value)} value={idJoueur}>
        <option value="">-- Choisir un joueur --</option>
        {joueurs.map(j => <option key={j.id} value={j.id}>{j.pseudo}</option>)}
      </select>
      <select onChange={e => setIdClasse(e.target.value)} value={idClasse}>
        <option value="">-- Choisir une classe --</option>
        {classes.map(c => <option key={c.id} value={c.id}>{c.nom}</option>)}
      </select>
      <input
        type="number"
        value={niveau}
        onChange={e => setNiveau(e.target.value)}
        min={0}
        placeholder="Niveau"
      />
      <button onClick={lier}>Lier</button>
    </div>
  );
}

export default LierClasseForm;
