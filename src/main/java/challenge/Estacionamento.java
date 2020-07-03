package challenge;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class Estacionamento {

    public static final int IDADE_PREFERENCIAL = 55;
    private static final Integer CAPACIDADE = 10;
    private final Queue<Carro> carros = new LinkedList<>();

    public void estacionar(Carro carro) {

        if (carrosEstacionados() < CAPACIDADE)
            carros.add(carro);
        else if (vagasEsgotadas())
            throw new EstacionamentoException(Mensagem.ESTACIONAMENTO_LOTADO);
        else if (carrosEstacionados() == CAPACIDADE) {
            liberarVaga();
            carros.add(carro);
        }
    }

    private void liberarVaga() {
        carros.stream()
                .filter(car -> car.getMotorista().getIdade() <= IDADE_PREFERENCIAL)
                .findFirst()
                .ifPresent(carros::remove);
    }

    public int carrosEstacionados() {
        return this.carros.size();
    }

    public boolean carroEstacionado(Carro carro) {
        return carros.contains(carro);
    }

    public boolean vagasEsgotadas() {
        Integer vagasPreferenciais =
                carros.stream()
                        .filter(carro -> carro.getMotorista().getIdade() >= IDADE_PREFERENCIAL)
                        .collect(Collectors.toList()).size();

        return (CAPACIDADE - vagasPreferenciais) == 0;
    }
}
