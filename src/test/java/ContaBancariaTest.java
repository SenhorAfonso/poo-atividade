import org.example.exceptions.*;
import org.example.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ContaBancariaTest {

    @Test
    public void TestEmpresaAbrirContaConjunta() throws Exception{
        Pessoa Joao = new Pessoa("123", "312312", TipoPessoa.PESSOA_FISICA, "João", 32, EstadoCivil.CASADO,0.0);
        Empresa joaoEmpresa = new Empresa("2", "Empresa da melhor que tem", Joao, 15000.0);

        try{
            ContaBancaria conta = joaoEmpresa.abrirConta(joaoEmpresa, TipoConta.CONTA_SALARIO);
        } catch (Exception e) {
            Assertions.assertEquals(new InvalidTypeOfBankAccount().getMessage(), e.getMessage());
        }
    }

    @Test
    public void TestContaConjuntaRequisitoUniaoEstavel() {

        Pessoa Maria = new Pessoa("321", "dsa78i", TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        Pessoa Joao = new Pessoa("123", "312312", TipoPessoa.PESSOA_FISICA, "João", 32, EstadoCivil.CASADO,0.0);

        try {
            ContaBancaria conta = Joao.abrirConta(Maria, TipoConta.CONTA_CONJUNTA);

        } catch (NoCivilUnionException e) {
            Assertions.assertEquals(new NoCivilUnionException().getMessage(), e.getMessage());

        } catch (Exception e) {
            System.out.println("Essa mensagem não deveria ter aparecido!");

        }

    }

    @Test
    public void TestContaConjuntoRequisitoCorretista() {

        Pessoa Joao = new Pessoa("123", "312312", TipoPessoa.PESSOA_FISICA, "João", 32, EstadoCivil.CASADO,0.0);

        try {
            ContaBancaria conta = Joao.abrirConta(null, TipoConta.CONTA_CONJUNTA);
        } catch (NoAccountHolderException e) {
            Assertions.assertEquals(new NoAccountHolderException().getMessage(), e.getMessage());
        } catch (Exception e) {
            System.out.println("Essa mensagem não deveria ter aparecido!");
        }

    }

    @Test
    public void TestMinimulValueForContaPoupanca() {

        Pessoa Joao = new Pessoa("123", "312312", TipoPessoa.PESSOA_FISICA, "João", 32, EstadoCivil.CASADO,0.0);

        try {
            ContaBancaria conta = Joao.abrirConta(null, TipoConta.CONTA_POUPANCA);
        } catch (NoMinimumValueException e) {
            Assertions.assertEquals(new NoMinimumValueException().getMessage(), e.getMessage());
        } catch (Exception e) {
            System.out.println("Essa mensagem não deveria ter aparecido!");
        }

    }

    @Test
    public void TestDifferentKindOfPersonForContaConjunta() {

        /**
         *
         * Impedir que pessoas de tipos diferentes (Jurídica e física) criem uma conta conjunta juntas.
         *
         */

        Pessoa Maria = new Pessoa("321", "dsa78i", TipoPessoa.PESSOA_JURIDICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        Pessoa Joao = new Pessoa("123", "312312", TipoPessoa.PESSOA_FISICA, "João", 32, EstadoCivil.CASADO,0.0);

        Joao.uniao(EstadoCivil.CASADO, Maria);

        try {
            ContaBancaria conta = Joao.abrirConta(Maria, TipoConta.CONTA_CONJUNTA);
        } catch (DifferentKindOfPersonException e) {
            Assertions.assertEquals(new DifferentKindOfPersonException().getMessage(), e.getMessage());
        } catch (Exception e) {
            System.out.println("Essa mensagem não deveria ter aparecido!");
        }

    }

    @Test
    public void TestIncomeTransferPersonToAccountWithSufficientBalance() {

        Pessoa Maria = new Pessoa("321", "dsa78i", TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        Pessoa Joao = new Pessoa("123", "312312", TipoPessoa.PESSOA_FISICA, "João", 32, EstadoCivil.CASADO,0.0);

        try{
            ContaBancaria contaJoao = Joao.abrirConta(null, TipoConta.CONTA_CORRENTE);
            Maria.RealizarDeposito(contaJoao, 100.0);

            Assertions.assertEquals(contaJoao.getSaldo(), 100.0);

        } catch (Exception e) {
            System.out.println("Essa mensagem não deveria ter aparecido!");
        }

    }

    @Test
    public void TestIncomeTransferPersonToAccountWithInsufficientBalance() {

        Pessoa Maria = new Pessoa("321", "dsa78i", TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        Pessoa Joao = new Pessoa("123", "312312", TipoPessoa.PESSOA_FISICA, "João", 32, EstadoCivil.CASADO,0.0);

        try{
            ContaBancaria contaJoao = Joao.abrirConta(null, TipoConta.CONTA_CORRENTE);
            Maria.RealizarDeposito(contaJoao, 400.0);

        } catch (NotEnoughIncomeException e) {
            Assertions.assertEquals(new NotEnoughIncomeException().getMessage(), e.getMessage());

        } catch (Exception e) {
            System.out.println("Essa mensagem não deveria ter aparecido!");

        }

    }

    @Test
    public void TestIncomeTransferAccountToAccountWithSufficientBalance() {

        Pessoa Maria = new Pessoa("321", "dsa78i", TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        Pessoa Joao = new Pessoa("123", "312312", TipoPessoa.PESSOA_FISICA, "João", 32, EstadoCivil.CASADO,0.0);

        try{
            ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);
            ContaBancaria contaJoao = Joao.abrirConta(null, TipoConta.CONTA_CORRENTE);
            Maria.RealizarDeposito(contaMaria, 350.0);
            Maria.RealizarDeposito(contaJoao, 350.0);

        } catch (NotEnoughIncomeException e) {
            Assertions.assertEquals(new NotEnoughIncomeException().getMessage(), e.getMessage());

        } catch (Exception e) {
            System.out.println("Essa mensagem não deveria ter aparecido!");

        }

    }

    @Test
    public void TestIncomeTransferAccountToAccountWithUnsufficientBalance() {

        Pessoa Maria = new Pessoa("321", "dsa78i", TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        Pessoa Joao = new Pessoa("123", "312312", TipoPessoa.PESSOA_FISICA, "João", 32, EstadoCivil.CASADO,0.0);

        try{
            ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);
            ContaBancaria contaJoao = Joao.abrirConta(null, TipoConta.CONTA_CORRENTE);
            Maria.RealizarDeposito(contaMaria, 350.0);
            Maria.RealizarDeposito(contaJoao, 450.0);

        } catch (NotEnoughIncomeException e) {
            Assertions.assertEquals(new NotEnoughIncomeException().getMessage(), e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Essa mensagem não deveria ter aparecido!");
        }

    }

    @Test
    public void TestApplicationWithValidValues() throws Exception {

        Pessoa Maria = new Pessoa("321", "dsa78i", TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);

        try {
            Assertions.assertEquals(24.0, Math.floor(contaMaria.aplicacao(20.0, 2, 0.10)));

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

    @Test
    public void TestApplicationWithNegativeMonth() throws Exception {

        Pessoa Maria = new Pessoa("321", null, TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);

        try {
            Assertions.assertEquals(22.0, contaMaria.aplicacao(20.0, -1, 0.10));

        } catch (NegativeMonthException e) {
            Assertions.assertEquals(new NegativeMonthException().getMessage(), e.getMessage());
            System.out.println(e.getMessage());

        }

    }

    @Test
    public void TestApplicationWithIncameZero() throws Exception {

        Pessoa Maria = new Pessoa("321", "dsa78i", TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);

        try {
            Assertions.assertEquals(0.0, contaMaria.aplicacao(0.0, 22, 0.10));

        } catch (NegativeMonthException e) {
            Assertions.assertEquals(new NegativeMonthException().getMessage(), e.getMessage());
            System.out.println(e.getMessage());

        }

    }

    @Test
    public void TestGetSaldo() {
        Pessoa Maria = new Pessoa("321", "dsa78i", TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);

        Assertions.assertEquals(350.0,  Maria.getSaldo());

    }

    @Test
    public void TestAutomaticLimitIncrease() throws Exception {
        Pessoa Maria = new Pessoa("321", "dsa78i", TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 100.0);
        Pessoa Joao = new Pessoa("123", "312312", TipoPessoa.PESSOA_FISICA, "João", 32, EstadoCivil.CASADO,0.0);

        ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_SALARIO);
        ContaBancaria contaJoao = Joao.abrirConta(null, TipoConta.CONTA_SALARIO);

        Maria.RealizarDeposito(contaMaria, 100.0);
        contaMaria.RealizarDeposito(contaJoao, 100.0);

        Assertions.assertEquals(510.0, contaMaria.getLimite());

    }

    @Test
    public void TestRequestedLimitIncreaseWithValidAmount() throws Exception {
        Pessoa Maria = new Pessoa("321", "dsa78i", TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 100.0);
        ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_SALARIO);

        contaMaria.solicitarAumentoLimite(550.0);
        Assertions.assertEquals(550.0, contaMaria.getLimite());

    }

    @Test
    public void TestRequestedLimitIncreaseWithInvalidAmount() throws Exception {
        Pessoa Maria = new Pessoa("321", "dsa78i", TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 100.0);
        ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_SALARIO);

        try {
            contaMaria.solicitarAumentoLimite(1000.0);
        } catch (LimitAboveMaximum e) {
            Assertions.assertEquals(new LimitAboveMaximum().getMessage(), e.getMessage());
        } catch (Exception e) {
            System.out.println("Essa mensagem não deveria ter aparecido!");
        }


    }

    @Test
    public void TestFinancingWithSufficientLimit() throws Exception {
        Pessoa Maria = new Pessoa("321", "dsa78i", TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 100.0);
        ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_SALARIO);

        Assertions.assertEquals(true, contaMaria.simularFinanciamento(4, 2000.0));

    }

    @Test
    public void TestFinancingWithUnsufficientLimit() throws Exception {
        Pessoa Maria = new Pessoa("321", "dsa78i", TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 100.0);
        ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_SALARIO);

        try {
            Assertions.assertEquals(true, contaMaria.simularFinanciamento(4, 2000.0));
        } catch (InsufficientLimitException e) {
            Assertions.assertEquals(new InsufficientLimitException().getMessage(), e.getMessage());
        } catch (Exception e) {
            System.out.println("Essa mensagem não deveria ter aparecido.");
        }


    }

    @Test
    public void TestMonthlyExtractWithValidMonthValue() throws Exception {
        Pessoa Maria = new Pessoa("321", null, TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);

        Maria.RealizarDeposito(contaMaria, 250.0);

        for(Transacao t : contaMaria.extratoMensal(LocalDateTime.now().getMonthValue())) {
            Assertions.assertEquals(250.0, t.getValor());
        }
    }

    @Test
    public void testMontlyExtractWithInvalidMonthValue() throws Exception {
        Pessoa Maria = new Pessoa("321", null, TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);

        Maria.RealizarDeposito(contaMaria, 250.0);

        try {
            for(Transacao t : contaMaria.extratoMensal(-1)) {
                Assertions.assertEquals(250.0, t.getValor());

            }

        } catch (InvalidMonthException e) {
            Assertions.assertEquals(new InvalidMonthException().getMessage(), e.getMessage());
        } catch (Exception e) {
            System.out.println("Essa mensagem não deveria ter aparecido!");
        }

    }

    @Test
    public void testMonthlyExtractWithValidMonthInterval() throws Exception {
        Pessoa Maria = new Pessoa("321", null, TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);

        Pessoa Joao = new Pessoa("321", null, TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        ContaBancaria contaJoao = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);

        Maria.RealizarDeposito(contaMaria, 250.0);
        Thread.sleep(1000); // inseri esse sleep porque se os dois depositos acontecerem no mesmo tempo ele sobrescreve a chave
        contaMaria.RealizarDeposito(contaJoao, 100.0);

        for(Transacao t : contaJoao.extratoMensal(1, 10)) {
            Assertions.assertEquals(100.0, t.getValor());
        }

    }

    @Test
    public void TestYearlyExtractWithValidYear() throws Exception {
        Pessoa Maria = new Pessoa("321", null, TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);

        Pessoa Joao = new Pessoa("321", null, TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        ContaBancaria contaJoao = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);

        Maria.RealizarDeposito(contaMaria, 250.0);
        Thread.sleep(1000); // inseri esse sleep porque se os dois depositos acontecerem no mesmo tempo ele sobrescreve a chave
        contaMaria.RealizarDeposito(contaJoao, 100.0);

        List<Transacao> transacoes_list = contaJoao.extratoAnual(2024);

        for (Transacao tr :
                transacoes_list) {
            Assertions.assertEquals("Entrada", tr.getTipo());
        }

    }

    @Test
    public void TestYearlyExtractWithInvalidYear() throws Exception {
        Pessoa Maria = new Pessoa("321", null, TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);

        Pessoa Joao = new Pessoa("321", null, TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        ContaBancaria contaJoao = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);

        Maria.RealizarDeposito(contaMaria, 250.0);
        Thread.sleep(1000); // inseri esse sleep porque se os dois depositos acontecerem no mesmo tempo ele sobrescreve a chave
        contaMaria.RealizarDeposito(contaJoao, 100.0);
        
        try {
            List<Transacao> transacoes_list = contaJoao.extratoAnual(-1);
        } catch (InvalidYearException e) {
            Assertions.assertEquals(new InvalidYearException().getMessage(), e.getMessage());
        }

    }

    @Test
    public void TestYearlyExtractWithValidYearInterval() throws Exception{
        Pessoa Maria = new Pessoa("321", null, TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        ContaBancaria contaMaria = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);

        Pessoa Joao = new Pessoa("321", null, TipoPessoa.PESSOA_FISICA, "Maria", 31, EstadoCivil.CASADO, 350.0);
        ContaBancaria contaJoao = Maria.abrirConta(null, TipoConta.CONTA_CORRENTE);

        Maria.RealizarDeposito(contaMaria, 250.0);
        Thread.sleep(1000); // inseri esse sleep porque se os dois depositos acontecerem no mesmo tempo ele sobrescreve a chave
        contaMaria.RealizarDeposito(contaJoao, 100.0);

        for (Transacao t : contaJoao.extratoAnual(1, 2024)) {
            Assertions.assertEquals(100.0, t.getValor());
        }
    }

}





















