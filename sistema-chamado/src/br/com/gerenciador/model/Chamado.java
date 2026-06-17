package br.com.gerenciador.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Chamado {
    private int id;
    private String descricao;
    private LocalDate dataAbertura;
    private String responsavel; //Inicialmente null
    private StatusChamado status;

    //Base para novos chamados
    public Chamado(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
        this.dataAbertura = LocalDate.now(); //Define a data atual como abertura
        this.status = StatusChamado.NOVO; //Todo chamado nasce como Novo
        this.responsavel = null; //Sem responsável inicial
    }

    public Chamado(int id, String descricao, LocalDate dataAbertura){
        this.id = id;
        this.descricao = descricao;
        this.dataAbertura = dataAbertura;
        this.status = StatusChamado.NOVO;
        this.responsavel = null;
    }

    //Calculo de diferença de dias
    public long getDiasEmAberto() {
        return ChronoUnit.DAYS.between(dataAbertura, LocalDate.now());
    }

    //Getters e Setters
    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public LocalDate getDataAbertura() { return dataAbertura; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public StatusChamado getStatus() { return status; }
    public void setStatus(StatusChamado status) { this.status = status; }

    @Override
    public String toString() {
        String resp = (responsavel == null) ? "Não Atribuído" : responsavel;
        return String.format("ID: %d | %s | Aberto há %d | Status: %s | Resp: %s",
        id, descricao, getDiasEmAberto(), status, resp);
    }
}
