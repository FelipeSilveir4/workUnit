/**
 * MIT License
 * <p>
 * Copyright (c) 2017.  Felipe Silveira
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package silveira.felipe.workunit.model;

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
    private static final int WORK_PERIOD = 1000;

    /**
     * Id of the WorkerThread.
     */
    private String workerThreadId;

    /**
     * Work load type.
     */
    private String workLoadType;

    /**
     * WorkerThread constructor.
     */
    public WorkerThread(String workLoadType) {
        this.workerThreadId = UUID.nameUUIDFromBytes(Longs.toByteArray(Instant.now().toEpochMilli())).toString();
        this.workLoadType = workLoadType;
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
        try {
            int workLoadTimeMutiplier = 1;
            switch (this.workLoadType) {
                case "high":
                    workLoadTimeMutiplier = 6;
                    break;
                case "medium":
                    workLoadTimeMutiplier = 4;
                    break;
                default:
                    workLoadTimeMutiplier = 2;
                    break;
            }
            long startTime = Instant.now().toEpochMilli();
            long sleepTime = 4000;
            while (Instant.now().toEpochMilli() < startTime + WORK_PERIOD * workLoadTimeMutiplier) {
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
