package Classes.Personagem;

import Classes.Feitico.LacoDeRepeticao;
import Classes.Feitico.LacoFor;
import Classes.Feitico.Magia;
import Classes.Feitico.TrechoDeCodigo;
import Classes.Personagem.Inimigo.Inimigo;

public class Player extends Personagem {

    private LacoDeRepeticao laco = null;

    public Player (int hpMaximo, int manaMaxima){
        super(hpMaximo, manaMaxima);
        this.hpAtual = hpMaximo;
        this.manaAtual = manaMaxima;
    }

    public void comprarFeitico(TrechoDeCodigo trecho) {
        // Caso o jogador compre um laço de repetição
        if (trecho instanceof LacoDeRepeticao) {
            if (this.laco == null) {
                if (manaAtual >= trecho.getCusto()) {
                    manaAtual -= trecho.getCusto();
                    setLaco((LacoDeRepeticao) trecho);

                }
            }
        }

        // Caso o jogador compre uma magia
        else if (trecho instanceof Magia) {
            if (this.laco == null) {
                return;
            }

            if (manaAtual >= trecho.getCusto()) {
                manaAtual -= trecho.getCusto();
                laco.setMagia((Magia) trecho);

            }
        }
    }

    public void atacar(Inimigo inimigo, int dano){
        inimigo.tomarDano(dano);
    }


    public LacoDeRepeticao getLaco() {
        return laco;
    }

    public void setLaco(LacoDeRepeticao laco) {
        this.laco = laco;
    }

    public void removerLaco(){
        laco = null;
    }
}
