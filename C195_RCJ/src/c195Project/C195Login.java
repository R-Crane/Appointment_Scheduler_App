package c195Project;
/**
to make this work on the VM - make sure that the paths are set correctly - assign the FX lib folder in proj structure
assign the path variable to the sam lib folder in FX folder and assign the path in the vm options under settings or may not work

 THE LAMBDAS FOR THIS PROJECT CAN BE FOUND IN MAIN MENU CONTROLLER - #1 IS FOR THE MONTH TOTAL APPOINTMENTS AND #2 IS FOR
 THE WEEK TOTAL APPOINTMENTS
 */
import c195Project.util.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class C195Login extends Application {

    /**
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        primaryStage.setTitle("C195 Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        {
            JDBC.getConnection();
            launch(args);
            JDBC.closeConnection();
        }
    }
}
