import React, { useState, useEffect } from "react";
import api from "../services/api";
import { useNavigate, useParams } from "react-router-dom";
import { cpf, cnpj } from "cpf-cnpj-validator";

export default function EditarCooperado() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [form, setForm] = useState({
    nome: "",
    cpfCnpj: "",
    dataNascimentoConstituicao: "",
    rendaFaturamento: "",
    telefone: "",
    email: "",
  });
  const [tipoPessoa, setTipoPessoa] = useState(null);
  const [errors, setErrors] = useState({});

  useEffect(() => {
    async function carregar() {
      try {
        const res = await api.get(`/${id}`);
        const data = res.data;
        setForm({
          nome: data.nome,
          cpfCnpj: data.cpfCnpj,
          dataNascimentoConstituicao: data.dataNascimentoConstituicao,
          rendaFaturamento: data.rendaFaturamento,
          telefone: data.telefone,
          email: data.email || "",
        });
      } catch {
        alert("Erro ao carregar cooperado");
        navigate("/");
      }
    }
    carregar();
  }, [id, navigate]);

  useEffect(() => {
    const onlyDigits = form.cpfCnpj.replace(/\D/g, "");
    if (onlyDigits.length === 11 && cpf.isValid(onlyDigits)) {
      setTipoPessoa("CPF");
    } else if (onlyDigits.length === 14 && cnpj.isValid(onlyDigits)) {
      setTipoPessoa("CNPJ");
    } else {
      setTipoPessoa(null);
    }
  }, [form.cpfCnpj]);

  const validar = () => {
    const newErrors = {};
    if (!form.nome.trim()) newErrors.nome = "Nome é obrigatório";

    if (!form.dataNascimentoConstituicao.trim())
      newErrors.dataNascimentoConstituicao =
        tipoPessoa === "CPF"
          ? "Data de nascimento é obrigatória"
          : "Data de constituição é obrigatória";

    if (!form.rendaFaturamento || Number(form.rendaFaturamento) <= 0)
      newErrors.rendaFaturamento =
        tipoPessoa === "CPF" ? "Renda é obrigatória e maior que zero" : "Faturamento é obrigatório e maior que zero";

    if (!form.telefone.trim())
      newErrors.telefone = "Telefone é obrigatório";
    else {
      const num = form.telefone.replace(/\D/g, "");
      if (num.length < 10 || num.length > 11)
        newErrors.telefone = "Telefone inválido";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    // CPF/CNPJ não pode ser editado
    if (e.target.name === "cpfCnpj") return;
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validar()) return;

    try {
      await api.put(`/${id}`, form);
      alert("Cooperado atualizado com sucesso!");
      navigate("/");
    } catch (error) {
      alert(error.response?.data || "Erro ao atualizar cooperado");
    }
  };

  return (
    <div className="container">
      <h2>Editar Cooperado</h2>
      <form onSubmit={handleSubmit} noValidate>
        <div>
          <label>Nome *</label>
          <input
            name="nome"
            value={form.nome}
            onChange={handleChange}
            required
          />
          {errors.nome && <p style={{ color: "red" }}>{errors.nome}</p>}
        </div>

        <div>
          <label>CPF ou CNPJ *</label>
          <input
            name="cpfCnpj"
            value={form.cpfCnpj}
            readOnly
            disabled
          />
        </div>

        <div>
          <label>
            {tipoPessoa === "CPF"
              ? "Data de Nascimento *"
              : tipoPessoa === "CNPJ"
              ? "Data de Constituição *"
              : "Data de Nascimento / Constituição *"}
          </label>
          <input
            name="dataNascimentoConstituicao"
            type="date"
            value={form.dataNascimentoConstituicao}
            onChange={handleChange}
            required
          />
          {errors.dataNascimentoConstituicao && (
            <p style={{ color: "red" }}>{errors.dataNascimentoConstituicao}</p>
          )}
        </div>

        <div>
          <label>
            {tipoPessoa === "CPF" ? "Renda *" : tipoPessoa === "CNPJ" ? "Faturamento *" : "Renda / Faturamento *"}
          </label>
          <input
            name="rendaFaturamento"
            type="number"
            min="0"
            step="0.01"
            value={form.rendaFaturamento}
            onChange={handleChange}
            required
          />
          {errors.rendaFaturamento && (
            <p style={{ color: "red" }}>{errors.rendaFaturamento}</p>
          )}
        </div>

        <div>
          <label>Telefone *</label>
          <input
            name="telefone"
            value={form.telefone}
            onChange={handleChange}
            placeholder="(99) 99999-9999"
            required
          />
          {errors.telefone && <p style={{ color: "red" }}>{errors.telefone}</p>}
        </div>

        <div>
          <label>E-mail</label>
          <input
            name="email"
            type="email"
            value={form.email}
            onChange={handleChange}
            placeholder="email@exemplo.com"
          />
        </div>

        <button type="submit">Atualizar</button>
      </form>
    </div>
  );
}
