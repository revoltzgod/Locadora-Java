package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

// Classe filha que herda de Veículo
public class Carro extends Veiculo {
    // Atributos específicos de Carro
    private int numeroPortas;
    private String tipoCambio;
    private boolean arCondicionado;

    // Construtor
    public Carro(String placa, String modelo, String marca, int ano, double valorDiaria,
                int numeroPortas, String tipoCambio, boolean arCondicionado) throws SQLException {
        super(placa, modelo, marca, ano, valorDiaria);
        this.numeroPortas = numeroPortas;
        this.tipoCambio = tipoCambio;
        this.arCondicionado = arCondicionado;
    }

    // Implementação do método abstrato (polimorfismo)
    @Override
    public String getTipo() {
        return "Carro";
    }

    // Getters específicos de Carro
    public int getNumeroPortas() {
        return numeroPortas;
    }

    public String getTipoCambio() {
        return tipoCambio;
    }

    public boolean hasArCondicionado() {
        return arCondicionado;
    }

    // Método toString específico para Carro
    @Override
    public String toString() {
        return super.toString() +
               "\nNúmero de Portas: " + numeroPortas +
               "\nTipo de Câmbio: " + tipoCambio +
               "\nAr Condicionado: " + (arCondicionado ? "Sim" : "Não");
    }

    public void setNumeroPortas(int numeroPortas) { this.numeroPortas = numeroPortas; }
    public void setTipoCambio(String tipoCambio) { this.tipoCambio = tipoCambio; }
    public void setArCondicionado(boolean arCondicionado) { this.arCondicionado = arCondicionado; }
} 