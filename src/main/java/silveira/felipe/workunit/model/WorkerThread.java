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

package silveira.felipe.workunit.model;

import com.google.common.primitives.Longs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
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

            switch (this.workLoadType) {
                case "high":
                    highMemoryWork();
                    break;
                case "medium":
                    mediumMemoryWork();
                    break;
                default:
                    lightMemoryWork();
                    break;
            }
            System.gc();
            LOGGER.info("[Worker: {}] Work Done", Thread.currentThread().getName());
        } catch (RuntimeException e) {
            LOGGER.error("Error performing work: {}.", e.getMessage(), e);
        }
    }

    /**
     * This mocks a light work load.
     */
    public void lightMemoryWork() {
        ArrayList<Byte[]> byteArrayList = new ArrayList<>();
        Runtime runtime = Runtime.getRuntime();
        LOGGER.info("Starting Light Memory Work(), free memory={}MB: ", runtime.freeMemory() / 1000);

        for (int i = 0; i < 10;i++) {
            Byte[] bytes = new Byte[1048576];
            byteArrayList.add(bytes);
            LOGGER.info("Free memory={}MB, arraySize={}: ", runtime.freeMemory() / 1000, byteArrayList.size());
        }
        byteArrayList.clear();
    }

    /**
     * This mocks a medium work load.
     */
    public void mediumMemoryWork() {
        ArrayList<Byte[]> byteArrayList = new ArrayList<>();
        Runtime runtime = Runtime.getRuntime();
        LOGGER.info("Starting Medium Memory Work(), free memory={}MB: ", runtime.freeMemory() / 1000);

        for (int i = 0; i < 100;i++) {
            Byte[] bytes = new Byte[1048576];
            byteArrayList.add(bytes);
            LOGGER.info("Free memory={}MB, arraySize={}: ", runtime.freeMemory() / 1000, byteArrayList.size());
        }
        byteArrayList.clear();
    }

    /**
     * This mocks a high work load.
     */
    public void highMemoryWork() {
        ArrayList<Byte[]> byteArrayList = new ArrayList<>();
        Runtime runtime = Runtime.getRuntime();
        LOGGER.info("Starting High Memory Work(), free memory={}MB: ", runtime.freeMemory() / 1000);

        for (int i = 0; i < 250;i++) {
            Byte[] bytes = new Byte[1048576];
            byteArrayList.add(bytes);
            LOGGER.info("Free memory={}MB, arraySize={}: ", runtime.freeMemory() / 1000, byteArrayList.size());
        }
        byteArrayList.clear();
    }
}
