package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.entities.Product;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<Product> list = new ArrayList<>(); //Crio uma lista que vai receber objetos do tipo produto

        System.out.println("Enter file path: "); //Peço para o usuário entrar com o caminho
        String sourceFileStr = sc.nextLine(); //Recebo o caminho

        File sourceFile = new File(sourceFileStr); //Crio um objeto do tipo file que recebe o caminho para conseguir trabalhar os arquivos
        String sourceFolderStr = sourceFile.getParent();

        boolean success = new File(sourceFolderStr + "/out").mkdir(); //Cria o diretorio a partir do caminho colocado
        System.out.println("Directory created successfully: " + success); //Retorna true caso o diretório tenha sido criado e false caso contrário

        String targetFileStr = sourceFolderStr + "/out/summary.csv"; //Cria o arquivo summary.csv no caminho digitado

        try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) { //Instancia o bufferedReader a partir de um FileReader
            String itemCsv = br.readLine(); //readline lê linha por linha

            while (itemCsv != null) { //Coloca a condição que enquanto a linha não for nula o programa continuará rodando

                String[] fields = itemCsv.split(","); //Coloca os itens da linha na lista fields quebrando a linha em três valores
                String name = fields[0]; //Envia para a variavel name o indice 0 que contém o nome
                double price = Double.parseDouble(fields[1]); //Envia para a variavel price o indice 1 que contém o preço
                int quantity = Integer.parseInt(fields[2]);  //Envia para a variavel quantity o indice 2 que contém o valor

                list.add(new Product(name, price, quantity)); //Instancia os objetos 

                itemCsv = br.readLine(); //Após printar pula pra outra linha
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))) { //Caso tudo tenha ocorrido bem no primeiro try, entra no segundo para escrever no arquivo summary.csv
                for (Product item: list){ //percorre a lista
                    bw.write(item.getName() + "," + String.format("%.2f", item.totalValue())); //pra cada item da lista, escreve no summary.csv
                    bw.newLine(); //Pula uma linha
                }
                System.out.println(targetFileStr + " CREATED"); //Printa caso tudo tenha ocorrido bem
            }
            catch (IOException e) { //Chama a excessao caso dê erro no segundo try
                System.out.println("Error writing file: " + e.getMessage()); //Printa a mensagem de erro
            }
        }
        catch (IOException e){ //Chama a excessao caso o arquivo não exista ou o caminho esteja errado
            System.out.println("Error writing file: " + e.getMessage()); //Printa a mensagem de erro
        }

        sc.close();
    }
}
