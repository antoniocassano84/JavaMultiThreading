// Create multiple threads.

class MyThread6 implements Runnable {
  Thread thrd;

  // Construct a new thread using this Runnable
  // and give it a name.
  MyThread6(String name) {
    thrd = new Thread(this, name);
  }

  // A factory method that creates and starts a thread.
  public static MyThread7 createAndStart(String name) {
    MyThread7 myThrd = new MyThread7(name);
    myThrd.thrd.start();  // start the Thread
    return  myThrd;
  }

  // Entry point of thread.
  public void run() {
    System.out.println(thrd.getName() + " starting.");
    try {
      for(int count =0; count < 10; count++) {
        Thread.sleep(400);
        System.out.println("In " + thrd.getName() + ", count is " + count);
      }
    } catch (InterruptedException exc) {
      System.out.println(thrd.getName() + " interrupted.");
    }
    System.out.println(thrd.getName() + " terminating.");
  }
}

// Use isAlive
class MoreThreads2 {
  public static void main(String[] args) {
    System.out.println("Main thread starting.");

    MyThread7 mt1 = MyThread7.createAndStart("Child #1");
    MyThread7 mt2 = MyThread7.createAndStart("Child #2");
    MyThread7 mt3 = MyThread7.createAndStart("Child #3");

    do {  // this waits until all threads terminate.
      System.out.print(".");
      try { Thread.sleep(100); }
      catch(InterruptedException exc) { System.out.println("Main thread interrupted."); }
    } while(mt1.thrd.isAlive() || mt2.thrd.isAlive() || mt3.thrd.isAlive());

    System.out.println("Main thread ending.");
  }
}
