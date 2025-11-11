package com.fatec.nexo.commom.valueobjects;

/**
 * Classe que possui os métodos relacionados a quantia de receitas e despesas
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.0
 */
public class Quantia {
    private double quantia;

    public Quantia() {
    }

    /**
     * Insere uma quantia, garantindo que tenha no máximo duas casas decimais
     * @param quantia
     * @return A quantia inserida
     * @throws IllegalArgumentException se a quantia tiver mais de duas casas decimais
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public double setQuantia(double quantia) {
        temDuasCasasDecimais(quantia);
        this.quantia = quantia;
        return this.quantia;
    }

    /**
     * Verifica se o valor de uma quantia possui no máximo duas casas decimais
     * @param valor O valor a ser verificado
     * @return true se o valor tiver no máximo duas casas decimais, e um IllegalArgumentException caso contrário
     * @throws IllegalArgumentException se o valor tiver mais de duas casas decimais
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
    */
    private boolean temDuasCasasDecimais(double valor) {
        String texto = String.valueOf(valor);
        int index = texto.indexOf(".");
        if (index < 0) return true;
        int casas = texto.length() - index - 1;
        if(casas > 2) throw new IllegalArgumentException("A quantia deve ter no máximo duas casas decimais.");
        return true;
    }
}
