package app;

import clientews.ProdutoVO;
import clientews.ServicoGerenciadorEstoque;
import clientews.ServicoGerenciadorEstoque_Service;
import java.util.List;
import javax.swing.JOptionPane;

public class Cliente {

    private static ServicoGerenciadorEstoque_Service servico;
    private static ServicoGerenciadorEstoque porta;
    
    static {
        servico = new ServicoGerenciadorEstoque_Service();
        porta = servico.getServicoGerenciadorEstoquePort();
    }
    
    public static void main(String[] args) {
        String opcoesMenu = "[1] Adicionar Produto \n"
                + "[2] Remover Produto \n"
                + "[3] Adicionar Estoque \n"
                + "[4] Baixar Estoque \n"
                + "[5] Contar Produtos \n"
                + "[6] Contar Estoque Físico \n"
                + "[7] Listar Produtos \n"
                + "[8] Sair";
        int opcao, resp;
        
        do {
            opcao = Integer.parseInt(JOptionPane.showInputDialog(null, opcoesMenu));
            switch (opcao) {
                case 1:
                    novoProduto();
                    break;
                case 2:
                    removerProduto();
                    break;
                case 7:
                    listarProdutos();
                    break;
            }
        } while (opcao != 8);
    }
    
    private static void novoProduto () {
        ProdutoVO produtoVOTemp = null;
        int codigo;
        String nome;
        boolean sair = false;
        do {
            try {
                codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Forneça o código do produto:"));
                nome = JOptionPane.showInputDialog(null, "Forneça o nome do produto:");
                produtoVOTemp = new ProdutoVO();
                produtoVOTemp.setCodigo(codigo);
                produtoVOTemp.setNome(nome);
                porta.adicionarProduto(produtoVOTemp);
                sair = true;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao executar a operação!" + ex.getMessage());
            }
        } while (!sair);
    }

    private static void removerProduto() {
        ProdutoVO produtoVOTemp = null;
        int codigo;
        try {
            codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Forneça o código do produto!"));
            produtoVOTemp = porta.buscarProdutoPorCodigo(codigo);
            if (produtoVOTemp != null) {
                porta.removerProduto(produtoVOTemp);
            } else {
                JOptionPane.showMessageDialog(null, "Produto não localizado!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Dados inconsistentes");
        }
    }

    private static void listarProdutos() {
        List<ProdutoVO> listaProduto = porta.listaProduto();
        for (ProdutoVO produtoTemp : listaProduto) {
            System.out.println("-------------------------");
            System.out.println("Código: " + produtoTemp.getCodigo());
            System.out.println("Nome: " + produtoTemp.getNome());
            System.out.println("Estoque: " + produtoTemp.getEstoque());
        }
    }
}
