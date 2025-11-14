package com.fatec.nexo.commom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.nexo.categoria.CategoriaModel;
import com.fatec.nexo.categoria.CategoriaService;
import com.fatec.nexo.despesas.DespesasModel;
import com.fatec.nexo.despesas.DespesasService;
import com.fatec.nexo.receitas.ReceitasModel;
import com.fatec.nexo.receitas.ReceitasService;
import com.fatec.nexo.saldo.SaldoModel;
import com.fatec.nexo.saldo.SaldoService;
import com.fatec.nexo.usuario.UsuarioModel;
import com.fatec.nexo.usuario.UsuarioService;
import com.fatec.nexo.usuario.exceptions.UsuarioNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;


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
    private CategoriaService categoriaService;

    public ViewController(UsuarioService usuarioService, SaldoService saldoService,
        DespesasService despesasService, ReceitasService receitasService, CategoriaService categoriaService) {
            this.usuarioService = usuarioService;
            this.saldoService = saldoService;
            this.despesasService = despesasService;
            this.receitasService = receitasService;
            this.categoriaService = categoriaService;
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String senha) {
    ModelAndView mv = new ModelAndView("consultora");
    try {
    UsuarioModel usuario = usuarioService.login(email, senha);
    SaldoModel saldo = saldoService.findLastSaldo(usuario);
    if (saldo == null) saldo = new SaldoModel();
    List<DespesasModel> despesas = despesasService.findByUsuario(usuario);
    if (despesas == null) despesas = java.util.Collections.emptyList();
    List<ReceitasModel> receitas = receitasService.findByUsuario(usuario);
    if (receitas == null) receitas = java.util.Collections.emptyList();
        mv.addObject("usuario", usuario);
        mv.addObject("saldo", saldo);
        mv.addObject("despesas", despesas);
        mv.addObject("receitas", receitas);
    } catch (UsuarioNotFoundException e) {
        mv.addObject("error", e.getMessage());
    }
    return mv;
   }

   @PostMapping("/cadastro")
   public ModelAndView cadastro(@ModelAttribute UsuarioModel usuario) {
    ModelAndView mv = new ModelAndView("consultora");
    try {
    UsuarioModel _usuario = usuarioService.create(usuario);
    SaldoModel saldo = saldoService.create(_usuario);
    List<DespesasModel> despesas = despesasService.findByUsuario(_usuario);
    if (despesas == null) despesas = new ArrayList<>();
    List<ReceitasModel> receitas = receitasService.findByUsuario(_usuario);
    if (receitas == null) receitas = new ArrayList<>();
        mv.addObject("usuario", _usuario);
        mv.addObject("saldo", saldo);
        mv.addObject("despesas", despesas);
        mv.addObject("receitas", receitas);
    } catch (Exception e) {
        mv.addObject("error", e.getMessage());
    }
    return mv;
   }

   @GetMapping("/logout")
   public String logout() {
       return "index";
   }

   @PostMapping("/despesas/adicionar")
   public ModelAndView adicionarDespesas(@ModelAttribute UsuarioModel usuario) {
       ModelAndView mv = new ModelAndView("adicionarDespesas");
       try {
           UsuarioModel _usuario = usuarioService.findById(usuario.getEmail(), usuario);
           mv.addObject("usuario", _usuario);
           mv.addObject("saldo", saldoService.findLastSaldo(_usuario));
       } catch (Exception e) {
           mv.addObject("error", e.getMessage());
       }
       return mv;
   }

   @PostMapping("/categoria/adicionar")
   public ModelAndView adicionarCategoria(@ModelAttribute UsuarioModel usuario) {
       ModelAndView mv = new ModelAndView("adicionarCategoria");
       try {
           UsuarioModel _usuario = usuarioService.findById(usuario.getEmail(), usuario);
           mv.addObject("usuario", _usuario);
           mv.addObject("saldo", saldoService.findLastSaldo(_usuario));
           mv.addObject("categoria", new CategoriaModel());
       } catch (Exception e) {
           mv.addObject("error", e.getMessage());
       }
       return mv;
   }

   @PostMapping("/categoria/criar")
   public ModelAndView criarCategoria(@ModelAttribute CategoriaModel categoria) {
       ModelAndView mv = new ModelAndView("consultora");
       try {
           categoriaService.create(categoria);
           mv.addObject("usuario", usuarioService.findById(categoria.getSaldo().getUsuario().getEmail(), categoria.getSaldo().getUsuario()));
           mv.addObject("saldo", saldoService.findLastSaldo(categoria.getSaldo().getUsuario()));
           mv.addObject("despesas", despesasService.findByUsuario(categoria.getSaldo().getUsuario()));
           mv.addObject("receitas", receitasService.findByUsuario(categoria.getSaldo().getUsuario()));
       } catch (Exception e) {
           mv.addObject("error", e.getMessage());
       }
       return mv;
   }
   
}
