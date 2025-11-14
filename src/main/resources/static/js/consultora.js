document.addEventListener('DOMContentLoaded', ()=>{
    const btn = document.getElementById('btn-add-despesa');
    const btnCategoria = document.getElementById('btn-add-categoria');
    const btnEnviar = document.getElementById('btn-enviar-escondido');
    const btnEnviar1 = document.getElementById('btn-enviar-escondido-1');

    btn.addEventListener('click', (e)=>{
        e.preventDefault();
        btnEnviar.click();
    })
    btnCategoria.addEventListener('click', (e)=>{
        e.preventDefault();
        btnEnviar1.click();
    })
})