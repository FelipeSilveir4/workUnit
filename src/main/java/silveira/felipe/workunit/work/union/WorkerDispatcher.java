package silveira.felipe.workunit.work.union;

import static java.util.concurrent.TimeUnit.MINUTES;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import silveira.felipe.workunit.model.WorkerThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class WorkerDispatcher {

    /**
     * Logger object used to log messages.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerDispatcher.class);

    /**
     * Max number of workers.
     */
    private static final int MAX_WORKERS = 10;

    /**
     * Static reference to a WorkerDispatcher instance.
     */
    private static WorkerDispatcher instance = null;


    /**
     * Workers executors.
     */
    private ThreadPoolExecutor workerExecutor = new ThreadPoolExecutor(MAX_WORKERS,
            MAX_WORKERS, 1, MINUTES, new ArrayBlockingQueue<Runnable>(MAX_WORKERS, true));

    /**
     * Private constructor.
     */
    private WorkerDispatcher() {
    }

    /**
     * Get a default WorkerDispatcher instance.
     *
     * @return a WorkerDispatcher instance.
     */
    public static WorkerDispatcher getInstance() {
        if (instance == null) {
            instance = new WorkerDispatcher();
        }
        return instance;
    }

    /**
     * Assign work to workers.
     *
     * @param workersRequestNumber number of workers requested.
     */
    public synchronized Boolean assignWork(int workersRequestNumber) {
        Runnable worker = new WorkerThread();
        if (workerExecutor.getMaximumPoolSize() - workerExecutor.getActiveCount() >= workersRequestNumber) {
            LOGGER.info("{} available.", workersRequestNumber);
            for (int i = 0; i < workersRequestNumber; i++) {
                LOGGER.info("Worker #{} assigned.", i);
                workerExecutor.execute(worker);
            }
            return true;
        }
        LOGGER.info("Request rejected: No workers available.");
        return false;
    }

    public void shutdown() {
        workerExecutor.shutdown();
    }

}
