package br.com.fiap.masks;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

public class JsonOut {
    public static String createJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Criando um nó JSON com um array
        ObjectNode jsonObject = mapper.createObjectNode();
        jsonObject.put("Nome do Projeto", "BankYou API");
        jsonObject.set("Integrantes", mapper.valueToTree(List.of("Gulherme Alves Pedroso", "Rfael Souza Bezerra")));

        // Convertendo para JSON e imprimindo
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    }
}
