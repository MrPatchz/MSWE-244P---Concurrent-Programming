import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Producer implements Runnable {
    private BlockingQueue<Message> queue;
    private boolean running = true;
    private int id;
    public Producer(BlockingQueue q, int n) {
	queue = q;
	id = n;
    }

    public void stop() {
	running = false;
    }

    public void run() {
		AtomicInteger count = new AtomicInteger(0);
			while (running) {
				int n = RandomUtils.randomInteger();
				try {
						Thread.sleep(n);
						Message msg = new Message("message-" + n);
						queue.put(msg); // Put the message in the queue
						count.incrementAndGet();
						RandomUtils.print("Produced " + msg.get(), id);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// Put the stop message in the queue
			Message msg = new Message("stop");
		try {
			queue.put(msg); // Put this final message in the queue
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		RandomUtils.print("Messages sent: " + count, id);
	}
}
