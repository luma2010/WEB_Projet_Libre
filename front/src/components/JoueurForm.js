import { useEffect, useState } from "react";

function JoueurForm() {
  const [pseudo, setPseudo] = useState("");
  const [password, setPassword] = useState("");
  const [idServeur, setIdServeur] = useState("");
  const [serveurs, setServeurs] = useState([]);

  // Charger les serveurs depuis l'API
  useEffect(() => {
    fetch("http://localhost:7070/serveurs")
      .then((res) => res.json())
      .then((data) => setServeurs(data))
      .catch((err) => console.error("Erreur chargement serveurs :", err));
  }, []);

  const handleSubmit = async () => {
    if (!pseudo || !password || !idServeur) {
      alert("Veuillez remplir tous les champs.");
      return;
    }

    await fetch("http://localhost:7070/joueurs", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ pseudo, password, idServer: parseInt(idServeur) }),
    });

    alert("Joueur créé !");
    setPseudo("");
    setPassword("");
    setIdServeur("");
  };

  return (
    <div>
      <h2>Créer un joueur</h2>
      <input
        placeholder="Pseudo"
        value={pseudo}
        onChange={(e) => setPseudo(e.target.value)}
      />
      <input
        placeholder="Mot de passe"
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <select value={idServeur} onChange={(e) => setIdServeur(e.target.value)}>
        <option value="">-- Choisir un serveur --</option>
        {serveurs.map((serveur) => (
          <option key={serveur.id} value={serveur.id}>
            {serveur.nom}
          </option>
        ))}
      </select>
      <button onClick={handleSubmit}>Créer</button>
    </div>
  );
}

export default JoueurForm;
