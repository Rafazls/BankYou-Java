package br.com.fiap.controller;

import br.com.fiap.masks.JsonOut;
import br.com.fiap.model.Account;
import br.com.fiap.model.Deposito;
import br.com.fiap.model.Pix;
import br.com.fiap.model.Saque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    private final List<Account> repository = new ArrayList<>();

    @GetMapping("/")
    public String infoProject() throws Exception {
        return JsonOut.createJson();
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        log.info("Registering client: {}", account);

        validateAccount(account);

        if (repository.stream().anyMatch(c -> c.getCPF().equals(account.getCPF()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já cadastrado");
        }
        account.setAtivo(true);
        repository.add(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable Long id) {
        log.info("Buscando usuário com ID: {}", id);
        return findAccountById(id);
    }

    @PostMapping("/deposito")
    public ResponseEntity<Account> deposito(@RequestBody Deposito deposit) {
        log.info("Realizando depósito de {} na conta com ID: {}", deposit.getValorDeposito(), deposit.getAccountId());
        Account account = findAccountById(deposit.getAccountId());
        if (deposit.getValorDeposito() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor do depósito deve ser positivo");
        }
        account.setSaldoInicial(account.getSaldoInicial() + deposit.getValorDeposito());
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PostMapping("/saque")
    public ResponseEntity<Account> saque(@RequestBody Saque saque) {
        log.info("Realizando saque de {} na conta com ID: {}", saque.getValorSaque(), saque.getAccountId());
        Account account = findAccountById(saque.getAccountId());
        if (saque.getValorSaque() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor do saque deve ser positivo");
        }
        if (account.getSaldoInicial() < saque.getValorSaque()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente para o saque");
        }
        account.setSaldoInicial(account.getSaldoInicial() - saque.getValorSaque());
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PostMapping("/pix")
    public ResponseEntity<Account> transferPix(@RequestBody Pix pix) {
        log.info("Realizando transferência PIX de {} da conta ID: {} para a conta ID: {}",
                pix.getValor(), pix.getContaOrigem(), pix.getContadestino());
        log.info("" + pix);

        Account sourceAccount = findAccountById(pix.getContaOrigem());
        Account destinationAccount = findAccountById(pix.getContadestino());

        if (pix.getValor() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor do PIX deve ser positivo");
        }
        if (sourceAccount.getSaldoInicial() < pix.getValor()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente na conta de origem");
        }

        if (sourceAccount.isAtivo() == false || destinationAccount.isAtivo() == false) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conta de origem ou destino inativa");
        }

        sourceAccount.setSaldoInicial(sourceAccount.getSaldoInicial() - pix.getValor());
        destinationAccount.setSaldoInicial(destinationAccount.getSaldoInicial() + pix.getValor());

        return ResponseEntity.status(HttpStatus.OK).body(sourceAccount);
    }

    @GetMapping("/cpf/{cpf}")
    public Account getByCpf(@PathVariable String cpf) {
        log.info("Procurando usuário por CPF: {}", cpf);
        return repository.stream()
                .filter(c -> c.getCPF().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @GetMapping
    public List<Account> listAll() {
        log.info("Listando todos os clientes");
        return repository;
    }

    public Account findAccountById(Long id) {
        return repository
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> update(@PathVariable Long id) {
        log.info("Atualizando cliente com ID: {}", id);
        Account accountToUpdate = findAccountById(id);
        accountToUpdate.setAtivo(false);
        return ResponseEntity.status(HttpStatus.OK).body(accountToUpdate);
    }

    private void validateAccount(Account account) {
        if (account.getName() == null || account.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do titular é obrigatório");
        }
        if (account.getCPF() == null || account.getCPF().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF do titular é obrigatório");
        }
        if (account.getDataAbertura() == null || LocalDate.parse(account.getDataAbertura()).isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de abertura não pode ser no futuro");
        }
        if (account.getSaldoInicial() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo inicial não pode ser negativo");
        }
        if (!List.of("corrente", "poupanca", "salario").contains(account.getTipoConta().toString().toLowerCase())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Tipo deve ser um tipo válido (corrente, poupança ou salário)");
        }
    }
}
