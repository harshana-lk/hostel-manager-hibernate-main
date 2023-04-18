package bio.harshana;

import bio.harshana.bo.BOFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import bio.harshana.bo.custom.UserBO;
import bio.harshana.dto.UserDTO;
import javafx.stage.StageStyle;

import java.util.List;

public class AppInitializer extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        createDemoUser();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml")));
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    private void createDemoUser() {
        UserBO user = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
        List<UserDTO> all = user.getAll();

        if (all.isEmpty()) {
            user.save(new UserDTO(1, "admin", "1234"));
        }
    }
}
