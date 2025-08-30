package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

// Classe filha que herda de Veículo
public class Moto extends Veiculo {
    // Atributos específicos de Moto
    private int cilindradas;
    private String tipoPartida;

    // Construtor
    public Moto(String placa, String modelo, String marca, int ano, double valorDiaria,
                int cilindradas, String tipoPartida) throws SQLException {
        super(placa, modelo, marca, ano, valorDiaria);
        this.cilindradas = cilindradas;
        this.tipoPartida = tipoPartida;
    }

    // Implementação do método abstrato (polimorfismo)
    @Override
    public String getTipo() {
        return "Moto";
    }

    // Getters específicos de Moto
    public int getCilindradas() {
        return cilindradas;
    }

    public String getTipoPartida() {
        return tipoPartida;
    }

    public void setCilindradas(int cilindradas) { this.cilindradas = cilindradas; }
    public void setTipoPartida(String tipoPartida) { this.tipoPartida = tipoPartida; }

    // Método toString específico para Moto
    @Override
    public String toString() {
        return super.toString() +
               "\nCilindradas: " + cilindradas +
               "\nTipo de Partida: " + tipoPartida;
    }
} 