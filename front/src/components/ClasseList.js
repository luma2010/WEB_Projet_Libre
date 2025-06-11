// src/components/ClasseList.js
import React, { useEffect, useState } from "react";

function ClasseList() {
  const [classes, setClasses] = useState([]);

  useEffect(() => {
    fetch("http://localhost:7070/classes")
      .then((res) => res.json())
      .then((data) => setClasses(data))
      .catch((err) => console.error("Erreur fetch /classes :", err));
  }, []);

  return (
    <div>
      <h2>Liste des classes</h2>
      <div style={{ display: "flex", flexWrap: "wrap", gap: "1rem" }}>
        {classes.map((c) => (
          <div key={c.id} style={{ border: "1px solid #ccc", padding: "1rem" }}>
            <img
                src={`/images/${c.image}`}
                alt={c.nom}
                style={{ width: "100px" }}
            />
            <h3>{c.nom}</h3>
            <p>Role ID : {c.roleId}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ClasseList;
