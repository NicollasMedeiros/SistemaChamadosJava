package br.com.gerenciador.service;

import br.com.gerenciador.model.Chamado;
import br.com.gerenciador.model.StatusChamado;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ChamadoService {
    private List<Chamado> chamados = new ArrayList<>();
    private int proximoId = 1;

    //Define o limite de dias para um chamado ser considerado atrasado se ainda estiver NOVO
    private static final int LIMITE_DIAS_ATRASO = 5;

    //Cadastro de novos chamados
    public void cadastrarChamado(String descricao){
        Chamado novo = new Chamado(proximoId++, descricao);
        chamados.add(novo);
    }

    //Cadastro auxiliar para testes com datas retrotivas
    public void cadastrarChamadoAntigo(String descricao, java.time.LocalDate data){
        Chamado antigo = new Chamado(proximoId++, descricao, data);
        atualizarStatusPorTempo(antigo);
        chamados.add(antigo);
    }

    //Ele checa se esta sem responsável e se estourou os dias limite
    private void atualizarStatusPorTempo(Chamado chamado){
        if (chamado.getResponsavel() == null && chamado.getDiasEmAberto() > LIMITE_DIAS_ATRASO){
            chamado.setStatus(StatusChamado.ATRASADO);
        }
    }

    //Permite encaminhar o chamado
    public boolean encaminharChamado(int id, String tecnico){
        for (Chamado c : chamados) {
            if (c.getId() == id){
                c.setResponsavel(tecnico);
                c.setStatus(StatusChamado.EM_ATENDIMENTO); //Muda o status ao receber um responsável
                return true;
            }
        }
        return false; //Retorna falso se o ID não existir
    }

    //Retorna a lista de chamados organizada por dias em aberto
    public List<Chamado> ListarTodosChamados() {
        for (Chamado c : chamados) {
            atualizarStatusPorTempo(c); //Atualiza os atrasos antes de ordenar
        }
        List<Chamado> ordenados = new ArrayList<>(chamados);
        ordenados.sort(Comparator.comparingLong(Chamado::getDiasEmAberto));
        return ordenados;
    }

    //Exibe os chamados separados por categoria
    public void exibirRelatorioFiltro(){
        List<Chamado> todos =  ListarTodosChamados();

        System.out.println("\n--- CHAMADOS NOVOS (SEM RESPONSÁVEL) ---");
        todos.stream().filter(c -> c.getStatus() == StatusChamado.NOVO).forEach(System.out::println);

        System.out.println("\n--- CHAMADOS EM ATENDIMENTO ---");
        todos.stream().filter(c -> c.getStatus() == StatusChamado.EM_ATENDIMENTO).forEach(System.out::println);

        System.out.println("\n--- CHAMADOS EM ATRASO ---");
        todos.stream().filter(c -> c.getStatus() == StatusChamado.ATRASADO).forEach(System.out::println);
    }
}
