package silveira.felipe.workunit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.primitives.Longs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.UUID;

/**
 * This class contains a representation of a WorkerThread.
 */
public class WorkerThread implements Runnable {

    /**
     * Logger object used to log messages.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerThread.class);

    /**
     * Work period in ms.
     */
    private static final int WORK_PERIOD = 180000;

    /**
     * Id of the WorkerThread.
     */
    @JsonProperty(required = true)
    private String workerThreadId;

    /**
     * WorkerThread constructor.
     */
    public WorkerThread() {
        this.workerThreadId = UUID.nameUUIDFromBytes(Longs.toByteArray(Instant.now().toEpochMilli())).toString();
    }

    /**
     * This method gets the workerThreadId.
     *
     * @return the workerThreadId.
     */
    public String getWorkerThreadId() {
        return workerThreadId;
    }

    @Override
    public void run() {
        LOGGER.info("[Worker: {}] Starting Work...", Thread.currentThread().getName());
        long startTime = Instant.now().toEpochMilli();
        long sleepTime = 100;
        try {
            while (Instant.now().toEpochMilli() < startTime + WORK_PERIOD) {
                if (Instant.now().toEpochMilli() % sleepTime == 0) {
                    Thread.sleep((long) Math.floor(WORK_PERIOD * 0.5));
                }
            }
            LOGGER.info("[Worker: {}] Work Done", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            LOGGER.error("Error performing work: {}.", e.getMessage(), e);
        }

    }
}
