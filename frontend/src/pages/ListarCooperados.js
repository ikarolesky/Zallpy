import React, { useEffect, useState } from "react";
import api from "../services/api";
import { Link } from "react-router-dom";

export default function ListarCooperados() {
  const [cooperados, setCooperados] = useState([]);
  const [filtros, setFiltros] = useState({ nome: "", cpfCnpj: "" });
  const [error, setError] = useState("");

  const carregarCooperados = async () => {
    try {
      const params = {};
      if (filtros.nome) params.nome = filtros.nome;
      if (filtros.cpfCnpj) params.cpfCnpj = filtros.cpfCnpj;
      const res = await api.get("", { params });
      setCooperados(res.data);
      setError("");
    } catch {
      setError("Erro ao buscar cooperados");
    }
  };

  useEffect(() => {
    carregarCooperados();
  }, []);

  const handleFiltroChange = (e) => {
    setFiltros({ ...filtros, [e.target.name]: e.target.value });
  };

  const handleBuscar = (e) => {
    e.preventDefault();
    carregarCooperados();
  };

  const handleRemover = async (id) => {
    if (!window.confirm("Confirma remoção?")) return;
    try {
      await api.delete(`/${id}`);
      carregarCooperados();
    } catch {
      alert("Erro ao remover cooperado");
    }
  };

  return (
    <div className="container">
      <h1>Cooperados</h1>

      <form onSubmit={handleBuscar}>
        <input
          type="text"
          placeholder="Nome"
          name="nome"
          value={filtros.nome}
          onChange={handleFiltroChange}
        />
        <input
          type="text"
          placeholder="CPF ou CNPJ"
          name="cpfCnpj"
          value={filtros.cpfCnpj}
          onChange={handleFiltroChange}
        />
        <button type="submit">Buscar</button>
        <Link to="/cadastro" style={{ marginLeft: 10 }}>Novo Cooperado</Link>
      </form>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <table border="1" cellPadding="10" style={{ marginTop: 20 }}>
        <thead>
          <tr>
            <th>Nome</th>
            <th>CPF/CNPJ</th>
            <th>Data Nascimento / Constituição</th>
            <th>Renda / Faturamento</th>
            <th>Telefone</th>
            <th>E-mail</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {cooperados.length === 0 ? (
            <tr>
              <td colSpan="7">Nenhum cooperado encontrado.</td>
            </tr>
          ) : (
            cooperados.map((c) => (
              <tr key={c.id}>
                <td>{c.nome}</td>
                <td>{c.cpfCnpj}</td>
                <td>{new Date(c.dataNascimentoConstituicao).toLocaleDateString()}</td>
                <td>{c.rendaFaturamento}</td>
                <td>{c.telefone}</td>
                <td>{c.email || "-"}</td>
                <td>
                  <Link to={`/editar/${c.id}`}>Editar</Link>{" | "}
                  <button onClick={() => handleRemover(c.id)}>Remover</button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}
