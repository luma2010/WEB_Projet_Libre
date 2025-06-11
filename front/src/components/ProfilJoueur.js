import { useParams } from "react-router-dom";
import { useEffect, useState, useContext } from "react";
import AuthContext from "../AuthContext.js";

function ProfilJoueur() {
  const { id } = useParams();
  const { user } = useContext(AuthContext);

  const [joueur, setJoueur] = useState(null);
  const [serveur, setServeur] = useState(null);
  const [classes, setClasses] = useState([]);
  const [allClasses, setAllClasses] = useState([]);

  const [liens, setLiens] = useState([]);
  const [newLienUrl, setNewLienUrl] = useState("");

  const [editId, setEditId] = useState(null);
  const [editNiveau, setEditNiveau] = useState(0);

  const [newClasseId, setNewClasseId] = useState("");
  const [newNiveau, setNewNiveau] = useState(0);

  const canEdit = user && user.id === Number(id);

  useEffect(() => {
    fetch(`http://localhost:7070/joueurs/${id}`)
      .then(res => res.json())
      .then(data => {
        setJoueur(data);
        if (data.idServer) {
          fetch(`http://localhost:7070/serveurs/${data.idServer}`)
            .then(res => res.json())
            .then(setServeur);
        }
      });

    fetch(`http://localhost:7070/joueurs/${id}/classes`)
      .then(res => res.json())
      .then(setClasses);

    fetch(`http://localhost:7070/classes`)
      .then(res => res.json())
      .then(setAllClasses);

    fetch(`http://localhost:7070/joueurs/${id}/liens`)
      .then(res => res.json())
      .then(setLiens);
  }, [id]);

  const saveEdit = async () => {
    await fetch(`http://localhost:7070/liaisons/${editId}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id: editId, niveau: editNiveau }),
    });
    const res = await fetch(`http://localhost:7070/joueurs/${id}/classes`);
    const data = await res.json();
    setClasses(data);
    setEditId(null);
  };

  const deleteClasse = async (classeId) => {
    await fetch(`http://localhost:7070/liaisons/${classeId}`, { method: "DELETE" });
    setClasses(classes.filter(c => c.id !== classeId));
  };

  const startEdit = (index) => {
    setEditId(classes[index].id);
    setEditNiveau(classes[index].niveau);
  };

  const lierClasse = async () => {
    if (!newClasseId) {
      alert("Choisissez une classe.");
      return;
    }
    await fetch("http://localhost:7070/liaisons", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        idJoueur: Number(id),
        idClasse: Number(newClasseId),
        niveau: Number(newNiveau),
      }),
    });
    const res = await fetch(`http://localhost:7070/joueurs/${id}/classes`);
    const data = await res.json();
    setClasses(data);
    setNewClasseId("");
    setNewNiveau(0);
  };

  const ajouterLien = async () => {
    if (!newLienUrl.trim()) {
      alert("Entrez une URL valide.");
      return;
    }
    await fetch("http://localhost:7070/liens", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        url: newLienUrl.trim(),
        idJoueur: Number(id),
      }),
    });
    const res = await fetch(`http://localhost:7070/joueurs/${id}/liens`);
    const data = await res.json();
    setLiens(data);
    setNewLienUrl("");
  };

  const supprimerLien = async (lienId) => {
    await fetch(`http://localhost:7070/liens/${lienId}`, { method: "DELETE" });
    setLiens(liens.filter(l => l.id !== lienId));
  };

  if (!joueur) return <div>Chargement...</div>;

  return (
    <div>
      <h2>Profil de {joueur.pseudo}</h2>
      <p><strong>Serveur :</strong> {serveur ? serveur.nom : "Chargement..."}</p>

      <h3>Classes liées</h3>
      <ul style={{ listStyle: "none", padding: 0 }}>
  {classes.map((c, index) => {

const classeComplete = allClasses.find(cl => cl.nom === c.nomClasse);
    console.log("Classe complète:", classeComplete);

    return (
      <li key={c.id} style={{ display: "flex", alignItems: "center", gap: "1rem", marginBottom: "0.5rem" }}>
        {classeComplete && (
          <img
            src={`/images/${classeComplete.image}`}
            alt={classeComplete.nom}
            style={{ width: "50px", height: "50px", objectFit: "contain" }}
          />
        )}
        <div>
          <strong>{c.nomClasse}</strong> - Niveau:{" "}
          {editId === c.id ? (
            <>
              <input
                type="number"
                value={editNiveau}
                min={0}
                onChange={e => setEditNiveau(Number(e.target.value))}
              />
              <button onClick={saveEdit}>Sauvegarder</button>
              <button onClick={() => setEditId(null)}>Annuler</button>
            </>
          ) : (
            <>
              {c.niveau}
              {canEdit && (
                <>
                  <button onClick={() => startEdit(index)}>Modifier</button>
                  <button onClick={() => deleteClasse(c.id)}>Supprimer</button>
                </>
              )}
            </>
          )}
        </div>
      </li>
    );
  })}
</ul>


      {canEdit && (
        <>
          <h4>Lier une nouvelle classe</h4>
          <select
            value={newClasseId}
            onChange={e => setNewClasseId(e.target.value)}
          >
            <option value="">-- Choisir une classe --</option>
            {allClasses.map(c => (
              <option key={c.id} value={c.id}>{c.nom}</option>
            ))}
          </select>
          <input
            type="number"
            min="0"
            value={newNiveau}
            onChange={e => setNewNiveau(Number(e.target.value))}
            placeholder="Niveau"
          />
          <button onClick={lierClasse}>Ajouter la classe</button>
        </>
      )}

      <h3>Liens externes</h3>
      <ul>
        {liens.map(lien => (
          <li key={lien.id}>
            <a href={lien.url} target="_blank" rel="noopener noreferrer">{lien.url}</a>
            {canEdit && <button onClick={() => supprimerLien(lien.id)}>Supprimer</button>}
          </li>
        ))}
      </ul>

      {canEdit && (
        <>
          <h4>Ajouter un lien externe</h4>
          <input
            type="text"
            value={newLienUrl}
            onChange={e => setNewLienUrl(e.target.value)}
            placeholder="URL"
          />
          <button onClick={ajouterLien}>Ajouter</button>
        </>
      )}
    </div>
  );
}

export default ProfilJoueur;
