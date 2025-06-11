import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import JoueurForm from "./components/JoueurForm";
import JoueurList from "./components/JoueurList";
import ProfilJoueur from "./components/ProfilJoueur";
import LoginForm from "./components/LoginForm";
import AuthContext from "./AuthContext.js";
import AuthProvider from "./AuthProvider";
import RaidList from "./components/RaidList";
import RaidForm from "./components/RaidForm";
import RaidDetail from "./components/RaidDetail";



function App() {
  return (
    <AuthProvider>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/creer-joueur" element={<JoueurForm />} />
          <Route path="/joueurs" element={<JoueurList />} />
          <Route path="/joueurs/:id" element={<ProfilJoueur />} />
          <Route path="/login" element={<LoginForm />} />
            <Route path="/raids" element={<RaidList />} />
  <Route path="/raids/creer" element={<RaidForm />} />
  <Route path="/raids/:id" element={<RaidDetail />} />
  <Route path="/raids/:id/modifier" element={<RaidForm />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
