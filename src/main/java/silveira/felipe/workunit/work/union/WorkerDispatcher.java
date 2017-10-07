/**
 * MIT License
 *
 * Copyright (c) 2017.  Felipe Silveira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package silveira.felipe.workunit.work.union;

import static java.util.concurrent.TimeUnit.MINUTES;

import com.google.common.primitives.Longs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import silveira.felipe.workunit.model.WorkReport;
import silveira.felipe.workunit.model.WorkerThread;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
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
     * Id of the workers Union.
     */
    private static final String WORK_UNION_ID = UUID.nameUUIDFromBytes(Longs.toByteArray(Instant.now()
            .toEpochMilli())).toString();

    /**
     * Workers executors.
     */
    private ThreadPoolExecutor workerExecutor = new ThreadPoolExecutor(MAX_WORKERS,
            MAX_WORKERS, 1, MINUTES, new ArrayBlockingQueue<>(MAX_WORKERS, true));

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
     * @return the workers report.
     */
    public synchronized WorkReport assignWork(int workersRequestNumber) {
        Runnable worker = new WorkerThread();
        if (workerExecutor.getMaximumPoolSize() - workerExecutor.getActiveCount() >= workersRequestNumber) {
            LOGGER.info("{} available.", workersRequestNumber);
            for (int i = 0; i < workersRequestNumber; i++) {
                LOGGER.info("Worker #{} assigned.", i);
                workerExecutor.execute(worker);
            }
            return new WorkReport(WORK_UNION_ID, true);
        }
        LOGGER.info("Request rejected: No workers available.");
        return new WorkReport(WORK_UNION_ID, false);
    }

    public void shutdown() {
        workerExecutor.shutdown();
    }

}
