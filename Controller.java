package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    public Button btSearch;
    @FXML
    public Button btAdd;
    @FXML
    public Button btFix;
    @FXML
    public Button btDelete;
    @FXML
    public TextField tfSearchWord;
    @FXML
    public TextField tfAddWord;
    @FXML
    public TextField tfFix;
    @FXML
    public TextField tfDelete;
    @FXML
    public ListView<String> lvWords;
    @FXML
    public TextArea taMeaning;

    HashMap<String, String> dictionary = new HashMap<String, String>();

    HashMap<String, String> word = new HashMap<String, String>();

    ArrayList<String> list = new ArrayList  <String>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btSearch.setOnMouseClicked(event -> {
            this.insertFromFile();
            String searchedWord = tfSearchWord.getText();
            if (searchedWord != null && searchedWord.equals("") == false) {

                String wordMeaning = dictionary.get(searchedWord);
                taMeaning.setText(wordMeaning);
            }

            Set<String> keySet = dictionary.keySet();
            for (String key : keySet) {

                String[] array1 = searchedWord.split("");
                String[] array2 = key.split("");

                int dem = 0;
                for(int j = 0; j < array2.length; j++) {
                    for(int k = 0; k < array1.length; k++){
                        if (array1[k].equals(array2[j+k])) {
                            dem++;
                        }
                    }
                }

                if(dem == array1.length) {
                    word.put(key, dictionary.get(key));
                }
            }
            dictionary.clear();
            Set<String> keySet1 = word.keySet();
            for (String key : keySet1) {
                dictionary.put(key, word.get(key));
            }
            lvWords.getItems().clear();
            lvWords.getItems().addAll(dictionary.keySet());
        });

        btAdd.setOnMouseClicked(event -> {
            this.insertFromFile();
            String addWord = tfAddWord.getText();
            if (addWord != null && addWord.equals("") == false) {
                String[] WordArray = addWord.split(" : ");
                dictionary.put(WordArray[0],WordArray[1]);
                this.dictionaryExportToFile();
            }
            lvWords.getItems().clear();
            lvWords.getItems().addAll(dictionary.keySet());

        });

        btFix.setOnMouseClicked(event -> {
            String fixWord = tfFix.getText();
            if (fixWord != null && fixWord.equals("") == false) {
                String[] WordArray = fixWord.split(" : ");
                dictionary.put(WordArray[0],WordArray[1]);
                this.dictionaryExportToFile();
            }
            lvWords.getItems().clear();
            lvWords.getItems().addAll(dictionary.keySet());
        });

        btDelete.setOnMouseClicked(event -> {
            this.insertFromFile();
            String delete = tfDelete.getText();
            if (delete != null && delete.equals("") == false) {
                dictionary.remove(delete);
                this.dictionaryExportToFile();
            }
            lvWords.getItems().clear();
            lvWords.getItems().addAll(dictionary.keySet());
        });

        this.initializeWordList();

        lvWords.setOnMouseClicked(event -> {
            String searchWord = lvWords.getSelectionModel().getSelectedItem();
            if (searchWord != null && searchWord.equals("") == false) {
                String wordMeaning = dictionary.get(searchWord);
                taMeaning.setText(wordMeaning);
            }
        });

    }

    public  void insertFromFile() {
        try {
            String[] WordArray = new String[0];
            try {
                File f = new File("dictionaries.txt");
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while (true) {
                    line = br.readLine();
                    if (line == null) {
                        break;
                    } else {
                        WordArray = line.split(" : ");
                        for (int i = 0; i < WordArray.length; i++) {
                            list.add(WordArray[i]);
                        }
                    }
                }
                fr.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < list.size(); i++) {
                if (i % 2 == 0) {
                    dictionary.put(list.get(i), list.get(i + 1));
                }
            }
        } finally {

        }
    }



    public  void dictionaryExportToFile() {
        try {
            FileWriter fw = new FileWriter("dictionaries.txt");
            Set<String> keySet = dictionary.keySet();
            for (String key : keySet) {
                fw.write(key + " : " + dictionary.get(key) + "\n");
            }
            fw.close();
        } catch (Exception e) {
        }
    }

    public void initializeWordList() {
        this.insertFromFile();
        lvWords.getItems().addAll(dictionary.keySet());
    }

    public void dictionarySearcher() {
        String search;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the word : ");
        search = scanner.nextLine();


        try {
            File f = new File("dictionaries.txt");
            FileReader fr = new FileReader(f);

            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] WordArray = new String[10000];
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                } else {
                    WordArray = line.split(" : ");
                }

                for (int i = 0; i < WordArray.length; i++) {
                    String[] array1 = search.split("");
                    String[] array2 = WordArray[i].split("");

                    int dem = 0;
                    for (int j = 0; j < array1.length; j++) {
                        if (array1[j].equals(array2[j])) {
                            dem++;
                        }
                        if (dem == array1.length) {
                            System.out.println(WordArray[i] + " -> " + WordArray[i + 1]);
                        }
                    }

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}