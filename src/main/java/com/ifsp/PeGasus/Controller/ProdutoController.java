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
import com.ifsp.PeGasus.Model.Produto;
import com.ifsp.PeGasus.Repository.CategoriaRepository;
import com.ifsp.PeGasus.Repository.ProdutoRepository;


@Controller
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/produto/formulario")
    public String formularioProduto(Model model){
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        model.addAttribute("listaCategorias", listaCategorias);
        return "formularioProduto";
    }

    @PostMapping("/produto/cadastro")
    public String saveProdutos(Produto produto, @RequestParam long categoriaId) {
        
        Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
        produto.setCategoria(categoria);

        produto.getCaracteristicas().removeIf(c -> c.getNome() == null || c.getNome().trim().isEmpty());

        produtoRepository.save(produto);
        return "redirect:/produto/formulario";
    }

    @GetMapping("/produto/lista")
    public String listProdutos(Model model){
        List<Produto> listaProdutos = produtoRepository.findAll();
        model.addAttribute("listaProdutos",listaProdutos);
        return "listaProdutos";
    }

    @GetMapping("/produto/{id}")
    public String produto(Model model, @PathVariable long id){
        Produto produto = produtoRepository.findById(id).orElse(null);
        model.addAttribute("produto",produto);
        return "detalhesProduto";
    }


    @GetMapping("/produto/{id}/editar")
    public String editarProduto(@PathVariable long id, Model model){
        Produto produto = produtoRepository.findById(id).orElse(null);
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        model.addAttribute("produto", produto);
        model.addAttribute("listaCategorias", listaCategorias);
        return "formularioEditarProduto";
    }

    @PostMapping("/produto/atualizar")
    public String atualizarProduto(@RequestParam long id,
                                   @RequestParam String descricao,
                                   @RequestParam long preco,
                                   @RequestParam long categoriaId,
                                   @RequestParam String nome){

        Produto produto = produtoRepository.findById(id).orElse(null);
        Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);

        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setCategoria(categoria);
        produto.setNome(nome);
        produtoRepository.save(produto);
        
        return "redirect:/produto/lista";
    }

    @GetMapping("/produto/{id}/excluirProduto")
    public String excluirProduto(@PathVariable long id){
        produtoRepository.deleteById(id);
        return "redirect:/produto/lista";
    }

}
