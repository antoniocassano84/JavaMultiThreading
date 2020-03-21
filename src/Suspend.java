// Suspending, resuming, and stopping a thread.

class MyThread11 implements Runnable {
  Thread thrd;
  boolean suspended;
  boolean stopped;

  MyThread11(String name) {
    thrd = new Thread(this, name);
    suspended = false;
    stopped = false;
  }

  // A factory method that creates and starts a thread.
  public static MyThread11 createAndStart(String name) {
    MyThread11 myThrd = new MyThread11(name);
    myThrd.thrd.start();  // start the thread
    return myThrd;
  }

  // Entry point of thread.
  public void run() {
    System.out.println(thrd.getName() + " starting.");
    try {
      for(int i=1; i<1000; i++) {
        System.out.print(i + " ");
        if((i%10)==0) {
          System.out.println();
          Thread.sleep(250);
        }
        // Use synchronized block to check suspended and stopped
        synchronized (this) {
          while(suspended) { wait(); }
          if(stopped) break;
        }
      }
    } catch (InterruptedException exc) { System.out.println(thrd.getName() + " interrupted."); }
    System.out.println(thrd.getName() + " exiting.");
  }  // end run()

  // Stop the thread
  synchronized void mystop() {
    stopped = true;
    // The following ensures that a suspended thread can be stopped
    suspended = false;
    notify();
  }

  // Suspend the thread
  synchronized void mysuspend() { suspended = true; }

  // Resume the thread
  synchronized void myresume() {
    suspended = false;
    notify();
  }
}

class Suspend {
  public static void main(String[] args) {
    MyThread11 mt1 = MyThread11.createAndStart("My Thread");

    try {
      Thread.sleep(1000); // let ob1 thread start the executing

      mt1.mysuspend();
      System.out.println("Suspending thread.");
      Thread.sleep(1000);

      mt1.myresume();
      System.out.println("Resuming thread");
      Thread.sleep(1000);

      mt1.mysuspend();
      System.out.println("Suspending thread.");
      Thread.sleep(1000);

      mt1.myresume();
      System.out.println("Resuming thread");
      Thread.sleep(1000);

      mt1.mysuspend();
      System.out.println("Stopping thread");
      mt1.mystop();
    } catch (InterruptedException exc) { System.out.println("Main thread interrupted."); }

    // wait for thread to finish
    try { mt1.thrd.join(); }
    catch (InterruptedException exc) { System.out.println("Main thread interrupted."); }
    System.out.println("Main thread exiting.");

  }
}
