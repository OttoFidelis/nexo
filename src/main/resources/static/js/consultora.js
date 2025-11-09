document.getElementById("logout").onclick = () => {
    window.location.href = "index.html";
};

// salário mensal fixo
let saldoMensal = 3500.00;

// exemplo de despesas
let despesas = JSON.parse(localStorage.getItem("despesas")) || [
    { descricao: "Água", valor: 120.00, dia: 5, mes: 11, ano: 2025 },
    { descricao: "Combustível", valor: 250.00, dia: 7, mes: 11, ano: 2025 },
    { descricao: "Farmácia", valor: 89.90, dia: 3, mes: 10, ano: 2025 },
    { descricao: "Manutenção do carro", valor: 450.00, dia: 2, mes: 11, ano: 2025 }
];

document.getElementById("saldo").textContent = `R$ ${saldoMensal.toFixed(2)}`;

// tabela de despesas
function atualizarTabela() {
    const tbody = document.getElementById("tabela-despesas");
    tbody.innerHTML = "";

    let total = 0;

    const filtroDia = document.getElementById("filtro-dia").value;
    const filtroMes = document.getElementById("filtro-mes").value;
    const filtroAno = document.getElementById("filtro-ano").value;

    despesas.forEach(d => {
        let exibir = true;

        if (filtroDia && Number(filtroDia) !== d.dia) exibir = false;
        if (filtroMes && Number(filtroMes) !== d.mes) exibir = false;
        if (filtroAno && Number(filtroAno) !== d.ano) exibir = false;

        if (exibir) {
            total += d.valor;

            let row = `
        <tr>
        <td>${d.dia}/${d.mes}/${d.ano}</td>
        </tr>`;

            tbody.innerHTML += row;
        }
    });

    document.getElementById("total").textContent = `R$ ${total.toFixed(2)}`;
    document.getElementById("restante").textContent = `R$ ${(saldoMensal - total).toFixed(2)}`;
}
