package com.ifsp.PeGasus.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ifsp.PeGasus.Model.Categoria;
import com.ifsp.PeGasus.Repository.CategoriaRepository;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/categoria/formulario")
    public String formulariocategoria(Model model) {
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        model.addAttribute("listaCategorias", listaCategorias);
        return "categoria/formularioCategoria";
    }

    @PostMapping("/categoria/cadastro")
    public String savecategorias(@RequestParam String nome) {

        categoriaRepository.save(new Categoria(nome));
        return "redirect:/produto/lista";
    }

    @GetMapping("/categoria/lista")
    public String listcategorias(Model model) {
        List<Categoria> listacategorias = categoriaRepository.findAll();
        model.addAttribute("listacategorias", listacategorias);
        return "categoria/listaCategorias";
    }

    @GetMapping("/categoria/{id}/editar")
    public String editarcategoria(@PathVariable long id, Model model) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        model.addAttribute("categoria", categoria);
        return "categoria/formularioEditarCategoria";
    }

    @PostMapping("/categoria/atualizar")
    public String atualizarcategoria(@RequestParam long id, @RequestParam String nome) {

        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        categoria.setNome(nome);
        categoriaRepository.save(categoria);
        return "redirect:/categoria/lista";
    }

    @GetMapping("/categoria/{id}/excluircategoria")
    public String excluircategoria(@PathVariable long id) {
        categoriaRepository.deleteById(id);
        return "redirect:/categoria/lista";
    }
}
