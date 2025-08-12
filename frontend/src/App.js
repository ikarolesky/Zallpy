import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ListarCooperados from "./pages/ListarCooperados";
import CadastroCooperado from "./pages/CadastroCooperado";
import EditarCooperado from "./pages/EditarCooperado";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<ListarCooperados />} />
        <Route path="/cadastro" element={<CadastroCooperado />} />
        <Route path="/editar/:id" element={<EditarCooperado />} />
      </Routes>
    </Router>
  );
}

export default App;
