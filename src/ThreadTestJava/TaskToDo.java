package ThreadTestJava;

/**
 * Created by TSFANA on 2016-11-10.
 */
public class TaskToDo implements Runnable
{
    //TO Do
    //Try to understand what happens in each method and
    //write an explanation to each method as comments
    String threadName = "";

    public  TaskToDo (String name)
    {
        threadName = name;
    }

    public void run()
    {

        UpdateList ();

      }

      void Sleep(int milliSecons  )
      {
          try {
              // thread to sleep for x milliseconds
              Thread.sleep(milliSecons);
          } catch (Exception e) {
              System.out.println(e);
          }
      }

        void UpdateList ( )
        {
            for (int i = 0; i < 10; i++)
            {
                //Console.foregroundColor = ConsoleColor.Blue;
                System.out.println ( threadName + " is working!"  );
                Sleep ( 500 );
            }
            System.out.println ( threadName + " terminated!" );
            System.out.println ( "=======================================" );

        }


        void UpdateList2 ( )
        {
            for (int i = 0; i < 10; i++)
            {
                //Console.foregroundColor = ConsoleColor.Red;
                System.out.println ( threadName + " is working!" );
                Sleep ( 300 );
            }
            System.out.println ( threadName + " terminated!" );
            System.out.println ( "+++++++++++++++++++++++++++++" );


        }
    }

