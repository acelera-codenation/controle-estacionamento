package challenge;


import java.util.Objects;

public class Motorista {

    public static final int IDADE_MINIMA = 18;
    public static final int NUMERO_LIMITE_PONTOS = 20;

    private final String nome;

    private final int idade;

    private final int pontos;

    private final String habilitacao;

    private Motorista(String nome, int idade, int pontos, String habilitacao) {

        this.nome = nome;
        this.idade = idade;
        this.pontos = pontos;
        this.habilitacao = habilitacao;
    }

    public static MotoristaBuilder builder() {
        return new MotoristaBuilder();
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public int getPontos() {
        return pontos;
    }

    public String getHabilitacao() {
        return habilitacao;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Motorista motorista = (Motorista) o;
        return Objects.equals(habilitacao, motorista.habilitacao);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(habilitacao);
    }

    @Override
    public String toString() {
        return "Motorista{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                ", pontos=" + pontos +
                ", habilitacao='" + habilitacao + '\'' +
                '}';
    }

    public static class MotoristaBuilder {

        private String nome;

        private int idade;

        private int pontos;

        private String habilitacao;

        private MotoristaBuilder() {
        }

        public MotoristaBuilder withNome(String nome) {
            this.nome = nome;
            return this;
        }

        public MotoristaBuilder withIdade(int idade) {
            if (idade < 0) throw new IllegalArgumentException(Mensagem.IDADE_INVALIDA);
            this.idade = idade;
            return this;
        }

        public MotoristaBuilder withPontos(int pontos) {
            if (pontos < 0) throw new IllegalArgumentException(Mensagem.PONTUACAO_INVALIDA);
            this.pontos = pontos;
            return this;
        }

        public MotoristaBuilder withHabilitacao(String habilitacao) {
            this.habilitacao = habilitacao;
            return this;
        }

        public Boolean motoristaComIdadeSuficienteParaDirigir() {
            return idade >= IDADE_MINIMA;
        }

        public Boolean motoristaEstaComHabilitacaoValida() {
            return pontos <= NUMERO_LIMITE_PONTOS;
        }

        public Motorista build() {
            if (habilitacao.trim().isEmpty()) throw new NullPointerException(Mensagem.MOTORISTA_SEM_HABILITACAO);
            if (nome.trim().isEmpty()) throw new NullPointerException(Mensagem.NOME_MOTORISTA_NAO_INFORMADO);

            if (!motoristaEstaComHabilitacaoValida())
                throw new EstacionamentoException(Mensagem.MOTORISTA_COM_HABILITACAO_SUSPENSA);

            if (!motoristaComIdadeSuficienteParaDirigir())
                throw new EstacionamentoException(Mensagem.IDADE_INSUFICENTE_PARA_DIRIGIR);

            return new Motorista(nome, idade, pontos, habilitacao);
        }
    }
}
