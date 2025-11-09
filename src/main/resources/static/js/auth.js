document.getElementById("show-register").onclick = () => {
  document.getElementById("login-form").classList.add("hidden");
  document.getElementById("register-form").classList.remove("hidden");
  document.getElementById("form-title").innerText = "Registrar";
};

document.getElementById("show-login").onclick = () => {
  document.getElementById("register-form").classList.add("hidden");
  document.getElementById("login-form").classList.remove("hidden");
  document.getElementById("form-title").innerText = "Entrar";
};

const msg = document.getElementById("message");

// Substituir por chamada Java depois
function loginUser(email, senha) {
  // Simulação:
  return (email === localStorage.userEmail && senha === localStorage.userSenha);
}

// Substituir por chamada Java depois
function registerUser(nome, email, senha) {
  localStorage.userName = nome;
  localStorage.userEmail = email;
  localStorage.userSenha = senha;
  return true;
}

document.getElementById("login-form").onsubmit = (e) => {
  e.preventDefault();
  const email = login-email.value;
  const senha = login-password.value;

  if (loginUser(email, senha)) {
    window.location.href = "consultora.html";
  } else {
    msg.textContent = "Usuário não encontrado. Faça o cadastro.";
  }
};

document.getElementById("register-form").onsubmit = (e) => {
  e.preventDefault();
  registerUser(reg-name.value, reg-email.value, reg-password.value);
  window.location.href = "consultora.html";
};
