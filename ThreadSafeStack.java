public class BlockingStack<E> {

  private final Stack<E> stack;
  private final int capacity;
  private final ReentrantLock lock;
  private final Condition notEmpty;
  private final Condition notFull;

  public BlockingStack(int capacity) {
    this(capacity, false);
  }

  public BlockingStack(int capacity, boolean fair) {
    if (capacity <= 0)
      throw new IllegalArgumentException();
    this.capacity = capacity;
    stack = new Stack<>();
    lock = new ReentrantLock(fair);
    notEmpty = lock.newCondition();
    notFull = lock.newCondition();
  }

  public void push(E e) throws InterruptedException {
    if (e == null)
      throw new NullPointerException();
    lock.lockInterruptibly();
    try {
      while (stack.size() == capacity) {
        notFull.await();
      }
      stack.push(e);
      notEmpty.signalAll();
    } catch (InterruptedException error) {
      notFull.signal();
      throw error;
    } finally {
      lock.unlock();
    }
  }

  public E pop() throws InterruptedException {
    lock.lockInterruptibly();
    try {
      while (stack.size() == 0) {
        notEmpty.await();
      }
      E item = stack.pop();
      notFull.signalAll();
      return item;
    } catch (InterruptedException error) {
      notEmpty.signal();
      throw error;
    } finally {
      lock.unlock();
    }
  }
}
