package com.ifsp.PeGasus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.PeGasus.Model.Categoria;
import com.ifsp.PeGasus.Model.Produto;
import com.ifsp.PeGasus.Repository.CategoriaRepository;
import com.ifsp.PeGasus.Repository.ProdutoRepository;

import java.util.List;

@Controller
public class DashboardController {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String exibeDashboard(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        
        int size = 10;
        
        String searchTerm = (q != null && !q.trim().isEmpty()) ? q.trim() : null;
        Long selectedCategory = (categoriaId != null && categoriaId > 0) ? categoriaId : null;
        
        Page<Produto> productPage = produtoRepository.findByNomeAndCategoria(searchTerm, selectedCategory, PageRequest.of(page, size));
        List<Categoria> listacategorias = categoriaRepository.findAll();
        
        model.addAttribute("produtos", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("q", q);
        model.addAttribute("categoriaId", categoriaId);
        model.addAttribute("categorias", listacategorias);
        
        return "dashboard-gerente/dashboard";
    }
}
