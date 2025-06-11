package com.example;

import com.example.dao.*;
import com.example.database.Database;
import com.example.model.*;
import com.example.dao.ClasseJoueurDao.ClasseAvecNiveau;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.model.RaidCreationRequest;





import io.javalin.Javalin;

import java.util.List;  

public class Main {
    public static void main(String[] args) throws Exception {
        Database.connect();

        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        }).start(7070);

        // CORS
        app.before(ctx -> {
            ctx.header("Access-Control-Allow-Origin", "*");
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Content-Type");
        });

        app.options("/*", ctx -> ctx.status(204));

        // === CLASSES ===

        app.get("/classes", ctx -> ctx.json(ClasseDao.getAll()));

        app.get("/classes/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Classe found = ClasseDao.getClasse(new Classe(id, null, null, 0));
                if (found != null) ctx.json(found);
                else ctx.status(404).json(new ErrorMessage("Classe non trouvée"));
            } catch (NumberFormatException e) {
                ctx.status(400).json(new ErrorMessage("ID invalide"));
            }
        });

        app.post("/classes", ctx -> {
            try {
                ClasseDao.insertClasse(ctx.bodyAsClass(Classe.class));
                ctx.status(201).json(new SuccessMessage("Classe ajoutée"));
            } catch (Exception e) {
                ctx.status(500).json(new ErrorMessage("Erreur lors de l'ajout"));
            }
        });

        app.put("/classes/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Classe c = ctx.bodyAsClass(Classe.class);
                c.id = id;
                ClasseDao.updateClasse(c);
                ctx.status(200).json(new SuccessMessage("Classe mise à jour"));
            } catch (Exception e) {
                ctx.status(500).json(new ErrorMessage("Erreur lors de la mise à jour"));
            }
        });

        app.delete("/classes/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                ClasseDao.deleteClasse(new Classe(id, null, null, 0));
                ctx.status(204);
            } catch (Exception e) {
                ctx.status(500).json(new ErrorMessage("Erreur lors de la suppression"));
            }
        });

        // === ROLES ===

        app.get("/roles", ctx -> ctx.json(RoleDao.getAll()));

        app.get("/roles/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Role r = RoleDao.getRole(new Role(id, null));
                if (r != null) ctx.json(r);
                else ctx.status(404).json(new ErrorMessage("Role non trouvé"));
            } catch (NumberFormatException e) {
                ctx.status(400).json(new ErrorMessage("ID invalide"));
            }
        });

        app.post("/roles", ctx -> {
            try {
                RoleDao.insertRole(ctx.bodyAsClass(Role.class));
                ctx.status(201).json(new SuccessMessage("Role ajouté"));
            } catch (Exception e) {
                ctx.status(500).json(new ErrorMessage("Erreur lors de l'ajout"));
            }
        });

        app.put("/roles/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Role r = ctx.bodyAsClass(Role.class);
                r.id = id;
                RoleDao.updateRole(r);
                ctx.status(200).json(new SuccessMessage("Role mis à jour"));
            } catch (Exception e) {
                ctx.status(500).json(new ErrorMessage("Erreur lors de la mise à jour"));
            }
        });

        app.delete("/roles/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                RoleDao.deleteRole(new Role(id, null));
                ctx.status(204);
            } catch (Exception e) {
                ctx.status(500).json(new ErrorMessage("Erreur lors de la suppression"));
            }
        });

        // === JOUEURS ===

        app.get("/joueurs", ctx -> ctx.json(JoueurDao.getAll()));

        app.get("/joueurs/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Joueur j = JoueurDao.getJoueur(new Joueur(id, null, null, 0));
                if (j != null) ctx.json(j);
                else ctx.status(404).json(new ErrorMessage("Joueur non trouvé"));
            } catch (NumberFormatException e) {
                ctx.status(400).json(new ErrorMessage("ID invalide"));
            }
        });

        app.post("/joueurs", ctx -> {
            try {
                Joueur j = ctx.bodyAsClass(Joueur.class);
                System.out.println("Reçu : " + j.pseudo + " / " + j.password + " / " + j.idServer);
                JoueurDao.insertJoueur(j);
                ctx.status(201).json(new SuccessMessage("Joueur ajouté"));
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(500).json(new ErrorMessage("Erreur lors de l'ajout"));
            }
        });


        app.put("/joueurs/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Joueur j = ctx.bodyAsClass(Joueur.class);
                j.id = id;
                JoueurDao.updateJoueur(j);
                ctx.status(200).json(new SuccessMessage("Joueur mis à jour"));
            } catch (Exception e) {
                ctx.status(500).json(new ErrorMessage("Erreur lors de la mise à jour"));
            }
        });

        app.delete("/joueurs/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                JoueurDao.deleteJoueur(new Joueur(id, null, null, 0));
                ctx.status(204);
            } catch (Exception e) {
                ctx.status(500).json(new ErrorMessage("Erreur lors de la suppression"));
            }
        });

        app.get("/joueurs/{id}/classes", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                List<ClasseJoueurDao.ClasseAvecNiveau> classes = ClasseJoueurDao.getClassesWithNiveauByJoueurId(id);
                ctx.json(classes);
            } catch (NumberFormatException e) {
                ctx.status(400).json(new ErrorMessage("ID invalide"));
            }
        });

        app.get("/joueurs/{id}/liens", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                List<Lien_Externe> liens = LienExterneDao.getByJoueurId(id);
                ctx.json(liens);
            } catch (NumberFormatException e) {
                ctx.status(400).json(new ErrorMessage("ID invalide"));
            }
        });

        // === CLASSE_JOUEUR (liaison) ===

        app.get("/liaisons", ctx -> ctx.json(ClasseJoueurDao.getAll()));

        app.get("/liaisons/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Classe_joueur cj = ClasseJoueurDao.getById(id);
                if (cj != null) ctx.json(cj);
                else ctx.status(404).json(new ErrorMessage("Liaison non trouvée"));
            } catch (NumberFormatException e) {
                ctx.status(400).json(new ErrorMessage("ID invalide"));
            }
        });

        app.post("/liaisons", ctx -> {
            try {
                Classe_joueur cj = ctx.bodyAsClass(Classe_joueur.class);
                System.out.println("Reçu : idJoueur=" + cj.idJoueur + ", idClasse=" + cj.idClasse + ", niveau=" + cj.niveau);
                ClasseJoueurDao.insert(cj);
                ctx.status(201).json(new SuccessMessage("Liaison ajoutée"));
            } catch (Exception e) {
                e.printStackTrace();  // Affiche l'erreur complète dans la console
                ctx.status(500).json(new ErrorMessage("Erreur lors de l'ajout"));
            }
        });

        app.delete("/liaisons/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                ClasseJoueurDao.delete(new Classe_joueur(id, 0, 0,0));
                ctx.status(204);
            } catch (Exception e) {
                ctx.status(500).json(new ErrorMessage("Erreur lors de la suppression"));
            }
        });
        app.put("/liaisons/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Classe_joueur updateData = ctx.bodyAsClass(Classe_joueur.class); 

                Classe_joueur existing = ClasseJoueurDao.getById(id);
                if (existing == null) {
                    ctx.status(404).json(new ErrorMessage("Liaison non trouvée"));
                    return;
                }

                existing.niveau = updateData.niveau;

                ClasseJoueurDao.update(existing);

                ctx.status(200).json(new SuccessMessage("Liaison mise à jour"));
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(500).json(new ErrorMessage("Erreur lors de la mise à jour"));
            }
        });



        // === SERVER ===


        app.get("/serveurs", ctx -> {
            ctx.json(ServeurDao.getAll());
        });

        app.get("/serveurs/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Serveur serveur = ServeurDao.getById(id);
                if (serveur != null) {
                    ctx.json(serveur);
                } else {
                    ctx.status(404).json(new ErrorMessage("Serveur non trouvé"));
                }
            } catch (NumberFormatException e) {
                ctx.status(400).json(new ErrorMessage("ID invalide"));
            }
        });

        // === LIEN_EXTERNE ===

        app.post("/liens", ctx -> {
            try {
                Lien_Externe lien = ctx.bodyAsClass(Lien_Externe.class);
                LienExterneDao.insert(lien);
                ctx.status(201).json(new SuccessMessage("Lien ajouté"));
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(500).json(new ErrorMessage("Erreur lors de l'ajout du lien"));
            }
        });

        app.delete("/liens/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                LienExterneDao.delete(id);
                ctx.status(204);
            } catch (Exception e) {
                ctx.status(500).json(new ErrorMessage("Erreur lors de la suppression"));
            }
        });

        // === RAIDS ===


        app.get("/raids", ctx -> {
            ctx.json(RaidDao.getAllRaids());
        });

        app.get("/raids/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Raid raid = RaidDao.getById(id);
            if (raid == null) {
                ctx.status(404).json(new ErrorMessage("Raid non trouvé"));
                return;
            }
            List<JoueurRaidInfo> joueurs = JoueurRaidDao.getByRaidIdWithNames(id);
            List<RaidRole> roles = RaidRoleDao.getByRaidId(id);
            ctx.json(Map.of(
                "raid", raid,
                "joueurs", joueurs,
                "roles", roles
            ));
        });

        app.post("/raids/{id}/joueurs", ctx -> {
            JoueurRaid jr = ctx.bodyAsClass(JoueurRaid.class);
            JoueurRaidDao.insert(jr);
            ctx.status(201).json(new SuccessMessage("Joueur ajouté au raid"));
        });

        app.delete("/raids/{raidId}/joueurs/{joueurRaidId}", ctx -> {
            int joueurRaidId = Integer.parseInt(ctx.pathParam("joueurRaidId"));
            JoueurRaidDao.delete(joueurRaidId);
            ctx.status(204);
        });

        app.put("/raids/{id}/roles", ctx -> {
            int raidId = Integer.parseInt(ctx.pathParam("id"));
            ObjectMapper mapper = new ObjectMapper();
            List<RaidRole> roles = mapper.readValue(ctx.body(), new TypeReference<List<RaidRole>>() {});
            RaidRoleDao.updateComposition(raidId, roles);
            ctx.status(200).json(new SuccessMessage("Composition mise à jour"));
        });

        app.delete("/raids/{id}", ctx -> {
            int raidId = Integer.parseInt(ctx.pathParam("id"));
            RaidDao.delete(raidId);
            ctx.status(204);
        });

        app.get("/combats", ctx -> {
            ctx.json(CombatDao.getAll());
        });

        app.get("/raidplans", ctx -> {
            String combatIdStr = ctx.queryParam("combatId");
            if (combatIdStr == null) {
                ctx.status(400).json(new ErrorMessage("combatId manquant"));
                return;
            }
            int combatId = Integer.parseInt(combatIdStr);
            ctx.json(RaidplanDao.getByCombatId(combatId));
        });

        app.post("/raids", ctx -> {
            RaidCreationRequest req = ctx.bodyAsClass(RaidCreationRequest.class);

            int leaderId = req.raid.idLeader;
            int classeLeaderId = req.classeLeaderId;

            List<ClasseJoueurDao.ClasseAvecNiveau> leaderClasses = ClasseJoueurDao.getClassesWithNiveauByJoueurId(leaderId);
boolean hasClasse = leaderClasses.stream()
    .anyMatch(c -> c.idClasse == classeLeaderId);
System.out.println("Classe leader demandée : " + classeLeaderId);
for (ClasseJoueurDao.ClasseAvecNiveau c : leaderClasses) {
    System.out.println("Classe du leader : idClasse = " + c.idClasse + ", nom = " + c.nomClasse + ", niveau = " + c.niveau);
}

if (!hasClasse) {
    ctx.status(400).json(new ErrorMessage("La classe choisie ne correspond pas aux classes du leader"));
    return;
}


            int raidId = RaidDao.insert(req.raid);

            JoueurRaid jr = new JoueurRaid(0, leaderId, raidId, classeLeaderId);
            JoueurRaidDao.insert(jr);

            for (RaidRole role : req.compositionRoles) {
                RaidRole rr = new RaidRole(0, raidId, role.idRole, role.nombreJoueur);
                RaidRoleDao.insert(rr);
            }

            ctx.status(201).json(new SuccessMessage("Raid créé avec succès"));
        });

        // === COMBAT ===

        app.get("/combats/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Combat combat = CombatDao.getById(id);
            if (combat == null) {
                ctx.status(404).json(new ErrorMessage("Combat non trouvé"));
            } else {
                ctx.json(combat);
            }
        });

        // === RAIDPLAN ===

        app.get("/raidplans/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Raidplan raidplan = RaidplanDao.getById(id);
            if (raidplan == null) {
                ctx.status(404).json(new ErrorMessage("Raidplan non trouvé"));
            } else {
                ctx.json(raidplan);
            }
        });

        // === LOGIN ===

        app.post("/login", ctx -> {
            try {
                LoginRequest loginRequest = ctx.bodyAsClass(LoginRequest.class);
                Joueur joueur = JoueurDao.getByPseudo(loginRequest.pseudo);
                if (joueur != null && joueur.password.equals(loginRequest.password)) {
                    joueur.password = null;  // optionnel, masque le password
                    ctx.json(joueur);
                } else {
                    ctx.status(401).json(new LoginResponse("Pseudo ou mot de passe incorrect"));
                }
            } catch (Exception e) {
                ctx.status(500).json(new LoginResponse("Erreur serveur"));
            }
        });



    }

    // Utilitaires de réponse
    public static class ErrorMessage {
        public String error;
        public ErrorMessage(String msg) {
            this.error = msg;
        }
    }

    public static class SuccessMessage {
        public String message;
        public SuccessMessage(String msg) {
            this.message = msg;
        }
    }

    public static class LoginRequest {
    public String pseudo;
    public String password;

    public LoginRequest() {}  
    }

    public static class LoginResponse {
        public String message;
        public LoginResponse(String message) {
            this.message = message;
        }
    }

}
