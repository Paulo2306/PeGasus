package com.ifsp.PeGasus.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import com.ifsp.PeGasus.Model.Categoria;
import com.ifsp.PeGasus.Model.Produto;
import com.ifsp.PeGasus.Model.Caracteristicas;
import com.ifsp.PeGasus.Repository.CategoriaRepository;
import com.ifsp.PeGasus.Repository.ProdutoRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;

    public DatabaseSeeder(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (produtoRepository.count() == 0) {
            seedDatabase();
        }
    }

    private void seedDatabase() {
        Categoria catHardware = criarCategoria("Hardware");
        Categoria catPerifericos = criarCategoria("Periféricos");
        Categoria catComputadores = criarCategoria("Computadores");
        Categoria catArmazenamento = criarCategoria("Armazenamento");

        byte[] imageBytes = loadPlaceholderImage();

        List<Produto> produtos = buildProdutos(catHardware, catPerifericos, catComputadores, catArmazenamento);

        for (Produto p : produtos) {
            p.setImagem(imageBytes);
            produtoRepository.save(p);
        }

        System.out.println("Banco de dados populado com 12 produtos e 4 categorias com sucesso!");
    }

    private Categoria criarCategoria(String nome) {
        return categoriaRepository.save(new Categoria(nome));
    }

    private byte[] loadPlaceholderImage() {
        try {
            ClassPathResource imgFile = new ClassPathResource("public/img/tech-placeholder.png");
            if (imgFile.exists()) {
                return StreamUtils.copyToByteArray(imgFile.getInputStream());
            }
            ClassPathResource fallbackFile = new ClassPathResource("public/img/PeGasus-Icon-Horse.png");
            if (fallbackFile.exists()) {
                return StreamUtils.copyToByteArray(fallbackFile.getInputStream());
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagem do seeder: " + e.getMessage());
        }
        return null;
    }

    private Produto criarProduto(String nome, String descricao, Float preco, Categoria categoria, String... caracteristicasChaveValor) {
        List<Caracteristicas> lista = new ArrayList<>();
        for (int i = 0; i < caracteristicasChaveValor.length; i += 2) {
            if (i + 1 < caracteristicasChaveValor.length) {
                lista.add(new Caracteristicas(caracteristicasChaveValor[i], caracteristicasChaveValor[i+1]));
            }
        }
        return new Produto(nome, descricao, preco, categoria, lista);
    }

    private List<Produto> buildProdutos(Categoria hardware, Categoria perifericos, Categoria computadores, Categoria armazenamento) {
        List<Produto> produtos = new ArrayList<>();

        produtos.add(criarProduto(
            "Placa de Vídeo RTX 4060",
            "Placa de vídeo NVIDIA GeForce RTX 4060 8GB GDDR6, ideal para jogos em Full HD com Ray Tracing e DLSS 3.",
            2399.00f,
            hardware,
            "Memória", "8GB GDDR6",
            "Interface", "128-bit",
            "Consumo", "115W"
        ));

        produtos.add(criarProduto(
            "Processador AMD Ryzen 5 5600X",
            "Processador AMD Ryzen 5 5600X, 6 núcleos e 12 threads, clock de 3.7GHz a 4.6GHz, com cooler Wraith Stealth.",
            949.00f,
            hardware,
            "Núcleos", "6",
            "Threads", "12",
            "Socket", "AM4"
        ));

        produtos.add(criarProduto(
            "Memória RAM Corsair Vengeance 16GB",
            "Módulo único de memória RAM Corsair Vengeance LPX de 16GB DDR4 com clock de 3200MHz, ideal para multitarefa.",
            299.00f,
            hardware,
            "Capacidade", "16GB",
            "Tipo", "DDR4",
            "Velocidade", "3200MHz"
        ));

        produtos.add(criarProduto(
            "Placa Mãe Asus TUF Gaming B550M-Plus",
            "Placa mãe AMD AM4 B550 micro ATX com PCIe 4.0, dual M.2, rede de 2.5 Gb, HDMI, DisplayPort, USB 3.2 Gen 2.",
            899.00f,
            hardware,
            "Socket", "AM4",
            "Formato", "Micro ATX",
            "Chipset", "B550"
        ));

        produtos.add(criarProduto(
            "Teclado Mecânico Redragon Kumara",
            "Teclado Gamer Mecânico Redragon Kumara K552, Switch Outemu Blue, iluminação LED vermelha, Layout ABNT2.",
            249.00f,
            perifericos,
            "Switch", "Outemu Blue",
            "Layout", "ABNT2",
            "Iluminação", "LED Vermelho"
        ));

        produtos.add(criarProduto(
            "Mouse Gamer Logitech G502 Hero",
            "Mouse Gamer Logitech G502 Hero com sensor de alta precisão HERO 25K, 11 botões programáveis e pesos ajustáveis.",
            349.00f,
            perifericos,
            "Sensor", "HERO 25K",
            "Botões", "11 programáveis",
            "Peso", "Ajustável"
        ));

        produtos.add(criarProduto(
            "Headset Gamer HyperX Cloud II",
            "Headset Gamer HyperX Cloud II com som surround virtual 7.1, driver de 53mm, microfone removível com cancelamento de ruído.",
            599.00f,
            perifericos,
            "Som", "7.1 Virtual",
            "Driver", "53mm",
            "Conexão", "USB / P3"
        ));

        produtos.add(criarProduto(
            "Monitor Gamer AOC Hero 24\" 144Hz",
            "Monitor Gamer AOC Hero 24 polegadas IPS, taxa de atualização de 144Hz, tempo de resposta de 1ms, FreeSync.",
            849.00f,
            perifericos,
            "Tela", "24 polegadas IPS",
            "Frequência", "144Hz",
            "Tempo de Resposta", "1ms"
        ));

        produtos.add(criarProduto(
            "PC Gamer Pegasus i5 / RTX 3060 / 16GB",
            "Computador Gamer montado com processador Intel Core i5, Placa de Vídeo RTX 3060, 16GB RAM, SSD 512GB, pronto para rodar tudo.",
            4299.00f,
            computadores,
            "Processador", "Intel Core i5",
            "Placa de Vídeo", "RTX 3060 12GB",
            "Armazenamento", "SSD 512GB NVMe"
        ));

        produtos.add(criarProduto(
            "Notebook Gamer Acer Nitro 5",
            "Notebook Gamer Acer Nitro 5 AN515-57-52LC, Intel Core i5, 8GB RAM, SSD 512GB, GTX 1650, Tela 15.6\" FHD 144Hz.",
            3999.00f,
            computadores,
            "Processador", "Intel Core i5-11400H",
            "Placa de Vídeo", "GTX 1650 4GB",
            "Tela", "15.6\" FHD 144Hz"
        ));

        produtos.add(criarProduto(
            "SSD Kingston NV2 1TB NVMe M.2",
            "SSD Kingston NV2 1TB NVMe M.2 2280 PCIe Gen 4.0 x4, leitura de até 3500MB/s e gravação de até 2100MB/s.",
            389.00f,
            armazenamento,
            "Capacidade", "1TB",
            "Interface", "PCIe 4.0 NVMe",
            "Leitura", "3500 MB/s"
        ));

        produtos.add(criarProduto(
            "HD Externo Seagate Expansion 2TB",
            "Disco Rígido Externo Portátil Seagate Expansion de 2TB, USB 3.0 para backup rápido e fácil armazenamento móvel.",
            459.00f,
            armazenamento,
            "Capacidade", "2TB",
            "Interface", "USB 3.0",
            "Alimentação", "USB"
        ));

        return produtos;
    }
}
