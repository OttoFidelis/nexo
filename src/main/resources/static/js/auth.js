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

