package ThreadTestJava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
   public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ThreadTestJava.fxml"));
        primaryStage.setTitle("ThreadTestApp");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        //launch(args);  //java fx

        TaskToDo task = new TaskToDo("Apu");
        Thread t1 = new Thread(task);
        t1.start();

        TaskToDo task2 = new TaskToDo("Homer");
        Thread t2 = new Thread(task2);
        t2.start();

        DoThingsOnMainThread();

       }

    static void DoThingsOnMainThread ( )
    {
        for (int i = 0; i < 10; i++)
        {
            //Console.foregroundColor = ConsoleColor.Green;
            System.out.println ( "Greetings from Main thread!" );
            try {
                Thread.sleep(500);
            }
            catch (Exception e)
            {
                System.out.println("Something went wrong at the end!");
            }
        }
        System.out.println ( "Main thread terminated!" );
        System.out.println ( "---------------------------------" );
    }
}
