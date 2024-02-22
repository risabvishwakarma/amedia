package com.amaytechnologies.alpha;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;


public class HelloController {
    MediaPlayer mediaPlayer;
    @FXML
    private MediaView mediaView;
    @FXML
    private Button playButton;

    @FXML
    private Slider slider;

    @FXML
    void onClickOpenSongs(ActionEvent event) {
        try {
            System.out.println("open song clicked");

            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);

            Media m = new Media(file.toURI().toURL().toString());





            mediaPlayer = new MediaPlayer(m);

            mediaPlayer.setOnReady(()->{
                slider.setMin(0);
                slider.setMax(mediaPlayer.getMedia().getDuration().toSeconds());
                slider.setValue(0);
            });
            //slider listener

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                    Duration d=mediaPlayer.getCurrentTime();
                    slider.setValue(d.toSeconds());
//                    slider.setMin(d.toSeconds());
                }
            });

            slider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    if(slider.isPressed()) {
                        double d = slider.getValue();
                        mediaPlayer.seek(new Duration(d * 1000));
                    }

                }
            });

            mediaView.setMediaPlayer(mediaPlayer);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    void onClickPlay(ActionEvent event) {

        try {
            MediaPlayer.Status status = mediaPlayer.getStatus();

            if (status == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                playButton.setText("Play");
//            playButton.setGraphic(new ImageView(new Image(new FileInputStream("icons/play.png"))));
            } else {
                mediaPlayer.play();
                playButton.setText("Pause");
//            playButton.setGraphic(new ImageView(new Image(new FileInputStream("icons/pause.png"))));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

