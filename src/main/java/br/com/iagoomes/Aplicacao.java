package br.com.iagoomes;

import br.com.iagoomes.modelo.Tabuleiro;
import br.com.iagoomes.visao.TabuleiroConsole;

public class Aplicacao {
    public static void main(String[] args) {

        Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
        new TabuleiroConsole(tabuleiro);

    }
}
