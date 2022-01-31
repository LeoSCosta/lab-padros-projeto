package one.digitalInnovation.labpadrosprojeto.service.imple;

import one.digitalInnovation.labpadrosprojeto.model.Cliente;
import one.digitalInnovation.labpadrosprojeto.model.ClienteRepository;
import one.digitalInnovation.labpadrosprojeto.model.Endereco;
import one.digitalInnovation.labpadrosprojeto.model.EnderecoRepository;
import one.digitalInnovation.labpadrosprojeto.service.ClienteService;
import one.digitalInnovation.labpadrosprojeto.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorID(Long id) {
        Optional<Cliente>cliente = clienteRepository.findById(id);
        return cliente.orElse(null);
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteCEP(cliente);
    }

    private void salvarClienteCEP(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep)
                .orElseGet(() -> {
                    Endereco novoEndereco = viaCepService.consultarCep(cep);
                    enderecoRepository.save(novoEndereco);
                    return novoEndereco;
                });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente>clientesalvo = clienteRepository.findById(id);
        if (clientesalvo.isPresent()){
            salvarClienteCEP(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
