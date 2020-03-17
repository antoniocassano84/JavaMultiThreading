// Use wait() and notify() to create a tickling clock.

class TickTock {
  String state;  // contains the state of the clock

  synchronized void tick(boolean running) {
    if(!running) {  // stop the clock
      state = "ticked";
      notify();  // notify  any waiting threads
      return;
    }

    System.out.print("Tick ");
    state = "ticked";  // set the current state to ticked
    notify();  // let tock() run
    try {
      while(!state.equals("tocked")) wait();  // wait for tock() to complete
    } catch (InterruptedException exc) { System.out.println("Thread interrupted."); }
  }

  synchronized void tock(boolean running) {
    if(!running) {  // stop the clock
      state = "tocked";
      notify();  // notify  any waiting threads
      return;
    }

    System.out.println("Tock");
    state = "tocked";  // set the current state to ticked
    notify();  // let tick() run
    try {
      while(!state.equals("ticked")) wait();  // wait for tick() to complete
    } catch (InterruptedException exc) { System.out.println("Thread interrupted."); }
  }
}

class MyThread10 implements Runnable {
  Thread thrd;
  TickTock ttOb;

  // Construct a new Thread.
  MyThread10(String name, TickTock tt) {
    thrd = new Thread(this, name);
    ttOb = tt;
  }

  // A factory method that creates and starts a thread.
  public static MyThread10 createAndStart(String name, TickTock tt) {
    MyThread10 myThrd = new MyThread10(name, tt);
    myThrd.thrd.start();  // start the thread.
    return myThrd;
  }

  // Entry point of thread
  public void run() {
    if(thrd.getName().compareTo("Tick") == 0) {
      for(int i=0; i<5; i++) ttOb.tick(true);
      ttOb.tick(false);
    } else {
      for(int i=0; i<5; i++) ttOb.tock(true);
      ttOb.tock(false);
    }
  }
}


class ThreadCom {
  public static void main(String[] args) {
    TickTock tt = new TickTock();
    MyThread10 mt1 = MyThread10.createAndStart("Tick", tt);
    MyThread10 mt2 = MyThread10.createAndStart("Tock", tt);

    try {
      mt1.thrd.join();
      mt2.thrd.join();
    } catch (InterruptedException exc) { System.out.println("Main thread interrupted."); }
  }
}
