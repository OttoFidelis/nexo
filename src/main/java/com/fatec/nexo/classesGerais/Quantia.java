package com.fatec.nexo.classesGerais;

public class Quantia {
    private double quantia;

    public Quantia() {
    }

    public double setQuantia(double quantia) {
        if (!temDuasCasasDecimais(quantia)) {
            throw new IllegalArgumentException("A quantia deve ter no máximo duas casas decimais.");
        }
        this.quantia = quantia;
        return this.quantia;
    }

    private boolean temDuasCasasDecimais(double valor) {
        String texto = String.valueOf(valor);
        int index = texto.indexOf(".");
        if (index < 0) return true;
        int casas = texto.length() - index - 1;
        return casas <= 2;
    }
}
