package br.com.iagoomes.modelo;

import br.com.iagoomes.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    private final int linha;
    private final int coluna;

    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    private List<Campo> vizinhos = new ArrayList<Campo>();

    Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    boolean adicionarVizinho(Campo candidatoVizinho) {
        boolean linhaDiferente = linha != candidatoVizinho.linha;
        boolean colunaDiferente = coluna != candidatoVizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - candidatoVizinho.linha);
        int deltaColuna = Math.abs(coluna - candidatoVizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(candidatoVizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(candidatoVizinho);
            return true;
        } else {
            return false;
        }
    }

    void alternarMacacao() {
        if (!aberto) {
            marcado = !marcado;
        }
    }

    boolean abrir() {

        if (!aberto && !marcado) {
            aberto = true;

            if (minado) {
                throw new ExplosaoException();
            }

            if (vizinhaçaSegura()) {
                vizinhos.forEach(v -> v.abrir());
            }

            return true;

        } else {
            return false;
        }

    }

    boolean vizinhaçaSegura() {
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    void minar() {
        minado = true;
    }

    public boolean isMinado() {
        return minado;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public boolean isAberto() {
        return aberto;
    }

    public boolean isFechado() {
        return !isAberto();
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    boolean objetivoAlcacado() {
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    long minasNaVizinhanca() {
        return vizinhos.stream().filter(v -> v.minado).count();
    }

    void reiniciar() {
        aberto = false;
        minado = false;
        marcado = false;
    }

    public String toString() {
        if (marcado) {
            return "x";
        } else if (aberto && minado) {
            return "*";
        } else if (aberto && minasNaVizinhanca() > 0) {
            return Long.toString(minasNaVizinhanca());
        } else if (aberto) {
            return " ";
        } else {
            return "?";
        }

    }
}
