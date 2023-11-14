package br.infnet;

import br.infnet.model.Episodio;
import br.infnet.model.PayloadPersonagemList;
import br.infnet.model.Personagem;
import br.infnet.util.EpisodioUtil;
import br.infnet.util.PersonagemUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class App {

    private static EpisodioUtil episodioUtil = new EpisodioUtil();
    private static PersonagemUtil personagemUtil = new PersonagemUtil();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("API de Rick N Morty");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        placeComponents(panel);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel label = new JLabel("Digite a Opcao Desejada");
        label.setBounds(10, 20, 200, 25);
        panel.add(label);

        String[] options = {"Buscar Episodio", "Buscar Avatar", "Personagem ID",
                "Personagem nome", "Buscar Avatar nome"};
        JComboBox<String> optionsComboBox = new JComboBox<>(options);
        optionsComboBox.setBounds(220, 20, 150, 25);
        panel.add(optionsComboBox);

        JTextField userInput = new JTextField();
        userInput.setBounds(10, 50, 360, 25);
        panel.add(userInput);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBounds(10, 80, 360, 250);
        panel.add(resultArea);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(10, 340, 100, 25);
        panel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) optionsComboBox.getSelectedItem();
                String resultText = handleOptionSelection(selectedOption, userInput.getText());
                resultArea.setText(resultText);
            }
        });
    }

    private static String handleOptionSelection(String selectedOption, String userInput) {
        switch (selectedOption) {
            case "Buscar Episodio": {
                int episodioId = Integer.parseInt(userInput);
                Episodio episodio = episodioUtil.getById(episodioId);
                List<Personagem> personagens = personagemUtil.getPersonagens(episodio.getCharacters(), 3);

                StringBuilder result = new StringBuilder();
                result.append("Nome: ").append(episodio.getName()).append("\n");
                result.append("Data que foi ao ar': ").append(episodio.getAirDate()).append("\n");
                result.append("Personagens: \n");

                for (Personagem personagem : personagens) {
                    result.append("Nome: ").append(personagem.getName()).append("\n");
                    result.append("Status: ").append(personagem.getStatus()).append("\n");
                    result.append("Origem: ").append(personagem.getOrigin().getName()).append("\n");
                    result.append("======================\n");
                }

                return result.toString();
            }
            case "Buscar Avatar": {
                int personagemId = Integer.parseInt(userInput);
                personagemUtil.getAvatar(personagemId);
                return "Imagem baixada";
            }
            case "Personagem ID": {
                int personagemId = Integer.parseInt(userInput);
                Personagem personagem = personagemUtil.getPersonagem(personagemId);

                StringBuilder result = new StringBuilder();
                result.append("Nome: ").append(personagem.getName()).append("\n");
                result.append("Status: ").append(personagem.getStatus()).append("\n");
                result.append("Origem: ").append(personagem.getOrigin().getName()).append("\n");
                result.append("======================\n");

                return result.toString();
            }
            case "Personagem nome": {
                PayloadPersonagemList payloadPersonagemList = personagemUtil.buscarPeloNome(userInput);
                List<Personagem> results = payloadPersonagemList.getResults();

                StringBuilder result = new StringBuilder();
                result.append("Total de Resultados: ").append(payloadPersonagemList.getInfo().getCount()).append("\n");

                for (Personagem personagem : results) {
                    result.append("Nome: ").append(personagem.getName()).append("\n");
                    result.append("Status: ").append(personagem.getStatus()).append("\n");
                    result.append("Origem: ").append(personagem.getOrigin().getName()).append("\n");
                    result.append("======================\n");
                }

                return result.toString();
            }
            case "Buscar Avatar nome": {
                personagemUtil.getAvatar(userInput);
                return "Imagem baixada";
            }
            default:
                return "";
        }
    }
}
