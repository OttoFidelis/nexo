package com.fatec.nexo.commom;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.nexo.despesas.DespesasModel;
import com.fatec.nexo.despesas.DespesasService;
import com.fatec.nexo.receitas.ReceitasModel;
import com.fatec.nexo.receitas.ReceitasService;
import com.fatec.nexo.saldo.SaldoModel;
import com.fatec.nexo.saldo.SaldoService;
import com.fatec.nexo.usuario.UsuarioModel;
import com.fatec.nexo.usuario.UsuarioService;
import com.fatec.nexo.usuario.exceptions.UsuarioNotFoundException;

@Controller
@RequestMapping("/nexo")
/**
 * Controlador responsável pela exibição das páginas de usuários pelo thymeleaf
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
public class ViewController{
    private UsuarioService usuarioService;
    private SaldoService saldoService;
    private DespesasService despesasService;
    private ReceitasService receitasService;

    public ViewController(UsuarioService usuarioService, SaldoService saldoService) {
        this.usuarioService = usuarioService;
        this.saldoService = saldoService;
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String senha) {
    ModelAndView mv = new ModelAndView("consultora");
    try {
        UsuarioModel usuario = usuarioService.login(email, senha);
        SaldoModel saldo = saldoService.findLastSaldo(usuario);
        List<DespesasModel> despesas = despesasService.findByUsuario(usuario);
        List<ReceitasModel> receitas = receitasService.findByUsuario(usuario);
        mv.addObject("usuario", usuario);
        mv.addObject("saldo", saldo);
        mv.addObject("despesas", despesas);
        mv.addObject("receitas", receitas);
    } catch (UsuarioNotFoundException e) {
        mv.addObject("error", e.getMessage());
    }
    return mv;
   }
}
