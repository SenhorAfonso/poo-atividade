package org.example.services;
import org.example.exceptions.DifferentKindOfPersonException;
import org.example.exceptions.NoAccountHolderException;
import org.example.exceptions.NoCivilUnionException;

public class ContaConjunta extends ContaBancaria implements ContaBancariaInterface{

    private Pessoa titular;
    private Pessoa corretista;
    private Double saldo = 0.0;
    private TipoConta tipoConta = TipoConta.CONTA_CONJUNTA;

    public ContaConjunta(Pessoa titular, Pessoa corretista) throws Exception {
        super(titular, corretista);

        if(corretista == null) {
            throw new NoAccountHolderException();

        } else if(corretista.getConjuge() == null) {
            throw new NoCivilUnionException();

        } else if (corretista.getTipoPessoa() != titular.getTipoPessoa()) {
            throw new DifferentKindOfPersonException();

        } else {
            this.corretista = corretista;

        }

    }

}
