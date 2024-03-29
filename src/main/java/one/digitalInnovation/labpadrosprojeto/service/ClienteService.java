package one.digitalInnovation.labpadrosprojeto.service;

import one.digitalInnovation.labpadrosprojeto.model.Cliente;

public interface ClienteService {
    Iterable<Cliente> buscarTodos();

    Cliente buscarPorID(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);
}
