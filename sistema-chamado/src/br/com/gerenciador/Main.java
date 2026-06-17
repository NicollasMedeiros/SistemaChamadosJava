package br.com.gerenciador;

import br.com.gerenciador.service.ChamadoService;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ChamadoService service = new ChamadoService();

        // Cadastrando chamados (Simulando cenários)
        System.out.println("Cadastrando chamados no sistema...");
        service.cadastrarChamado("Acesso negado ao ERP"); // Criado hoje (0 dias)
        service.cadastrarChamado("Impressora desconfigurada"); // Criado hoje (0 dias)

        // Simulando chamados para testar o filtro e os em atraso
        service.cadastrarChamadoAntigo("Troca de mouse na recepção", LocalDate.now().minusDays(3));
        service.cadastrarChamadoAntigo("Servidor de arquivos indisponível", LocalDate.now().minusDays(10));
        service.cadastrarChamadoAntigo("Configuração de VPN no Linux", LocalDate.now().minusDays(7));

        // Encaminhado para os responsáveis
        System.out.println("\nEncaminhando chamados...");
        service.encaminharChamado(1, "Ana Silva"); //ERP vai para Ana
        service.encaminharChamado(3, "Carlos Silva"); //Mouse vai para Carlos

        // O chamado 4 (Servidor - 10 dias) e chamado 5 (VPN - 7 dias) devem ir para ATRASADO
        // pois têm mais de 5 dias e continuam sem responsável.

        // Relatório final
        System.out.println("\n--- PAINEL DE CONTROLE DE CHAMADOS DE TI ---");
        service.exibirRelatorioFiltro();
    }
}