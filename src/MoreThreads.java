// Create multiple threads.

class MyThread5 implements Runnable {
  Thread thrd;

  // Construct a new thread using this Runnable
  // and give it a name.
  MyThread5(String name) {
    thrd = new Thread(this, name);
  }

  // A factory method that creates and starts a thread.
  public static MyThread5 createAndStart(String name) {
    MyThread5 myThrd = new MyThread5(name);
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

class MoreThreads {
  public static void main(String[] args) {
    System.out.println("Main thread starting.");

    MyThread5 mt1 = MyThread5.createAndStart("Child #1");
    MyThread5 mt2 = MyThread5.createAndStart("Child #2");
    MyThread5 mt3 = MyThread5.createAndStart("Child #3");

    for(int i=0; i<50; i++) {
      System.out.print(".");
      try { Thread.sleep(100); }
      catch(InterruptedException exc) { System.out.println("Main thread interrupted."); }
    }
    System.out.println("Main thread ending.");
  }
}
