package ru.alexsumin.oddoreven.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import ru.alexsumin.oddoreven.model.Game;
import ru.alexsumin.oddoreven.model.LuckyPlayer;
import ru.alexsumin.oddoreven.model.SimplePlayer;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;

public class Controller {


    private static final String BOTH_RANDOM = "Оба игрока делают случайный выбор";
    private static final String ONE_RANDOM = "Один игрок делает осознанный выбор";
    private static final String BOTH_LUCKY = "Оба игрока делают осознанный выбор";
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private NumberAxis xAxis, yAxis;
    @FXML
    private LineChart<Short, Float> chart;
    @FXML
    private TextArea textArea;
    @FXML
    private Spinner spinner;
    private FileChooser fileChooser = new FileChooser();
    private ContextMenu chartContextMenu;
    private FileChooser.ExtensionFilter filter1 = new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt");
    private FileChooser.ExtensionFilter filter2 = new FileChooser.ExtensionFilter("Файлы PNG", "*.png");

    @FXML
    private void initialize() {
        spinner.setEditable(false);
        textArea.setEditable(false);
        chart.setAnimated(false);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(1);
        chart.setCreateSymbols(false);
        xAxis.setLabel("Количество игр");
        yAxis.setLabel("Сумма на каждом шаге");
        chart.setTitle("Соотношение побед");
        chart.setLegendVisible(false);
        initContextMenuChart();


        choiceBox.getItems().addAll(FXCollections.observableArrayList(BOTH_RANDOM, ONE_RANDOM, BOTH_LUCKY));
        choiceBox.setValue(choiceBox.getItems().get(0));


    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void help() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Как работать с программой");
        alert.setHeaderText("Программа предназначена для моделирования серии игр чёт/нечёт." +
                "\n\n" +
                "1. Задайте количество игр.\n" +
                "2. Установите выбор игроков.\n" +
                "3. Нажмите \"Рассчитать\". Программа выполнит рассчёт и выведет результаты\n" +
                "4. При необходимости - сохраните результаты работы программы.");

        alert.showAndWait();
    }

    @FXML
    private void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("\tПрограмма позволяет выполнить моделирование серии игр\n \tчёт/нечёт," +
                "и визуализировать результаты рассчётов.\n" +
                "\tАвторы: студенты группы 444 Кривобокова А., Шарипова М., Сумин А.\n" +
                "\t\t\t\t\t\t СПбГТИ(ТУ) 2017");
        alert.showAndWait();
    }

    @FXML
    private void calculate() {
        chart.getData().clear();
        short number = Short.parseShort(String.valueOf(spinner.getValue()));
        Game game = new Game(number, textArea);

        switch (choiceBox.getValue()) {
            case BOTH_RANDOM: {
                game.setPlayer1(new SimplePlayer());
                game.setPlayer2(new LuckyPlayer());
                System.out.println("тупые игроки играют");
                break;
            }
            case ONE_RANDOM: {
                game.setPlayer1(new LuckyPlayer());
                game.setPlayer2(new SimplePlayer());
                System.out.println("первый умный");
                break;
            }
            case BOTH_LUCKY: {
                game.setPlayer1(new LuckyPlayer());
                game.setPlayer2(new LuckyPlayer());
                System.out.println("оба умные");
                break;
            }
        }

        game.play();
        ObservableList<Pair> gameResult = FXCollections.observableArrayList(new ArrayList<>(game.getResults()));
        drawChart(gameResult);

    }


    private void drawChart(ObservableList<Pair> list) {
        XYChart.Series<Short, Float> series = new XYChart.Series();
        list.stream().forEach(o -> series.getData().add(new XYChart.Data<>((short) o.getKey(), (float) o.getValue())));
        short bound = (short) list.size();
        xAxis.setUpperBound(bound);
        xAxis.setTickUnit(bound / 17);
        chart.getData().addAll(series);
    }

    @FXML
    private void clearAll() {
        chart.getData().clear();
        clearLog();
    }

    @FXML
    private void clearLog() {
        textArea.clear();
    }

    @FXML
    private void saveLog() {
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(filter1);
        File file = fileChooser.showSaveDialog(null);
        if (file == null) return;
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(textArea.getText());
            bw.close();
        } catch (Exception e1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Что-то пошло не так при сохранении данных в файл.");
            alert.showAndWait();
        }
    }

    private void initContextMenuChart() {

        MenuItem saveChart = new MenuItem("Сохранить как изображение");
        MenuItem clearChart = new MenuItem("Очистить");

        chartContextMenu = new ContextMenu(saveChart, clearChart);

        chart.setOnContextMenuRequested(event -> chartContextMenu.show(chart, event.getScreenX(), event.getScreenY()));

        clearChart.setOnAction(e -> chart.getData().clear());

        saveChart.setOnAction(e -> {
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(filter2);
            File file = fileChooser.showSaveDialog(null);

            if (file == null) return;

            WritableImage image = chart.snapshot(new SnapshotParameters(), null);

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Что-то пошло не так при сохранении файла.");
                alert.showAndWait();
            }
        });
    }

}
