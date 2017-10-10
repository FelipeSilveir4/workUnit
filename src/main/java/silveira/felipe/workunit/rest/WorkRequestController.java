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

package silveira.felipe.workunit.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import silveira.felipe.workunit.WorkerManager;
import silveira.felipe.workunit.model.WorkReport;

import javax.validation.constraints.NotNull;

/**
 * This class is the controller that receives calls to start workers.
 */
@ComponentScan
@EnableAutoConfiguration
@RestController
@RequestMapping(value = "workunit/api/v0.1/worker")
public class WorkRequestController {
    /**
     * Logger object used to log messages.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkRequestController.class);

    /**
     * {@link WorkerManager}.
     */
    @Autowired
    private WorkerManager workerManager;

    /**
     * This method handles the health check calls.
     */
    @RequestMapping(value = "/health", method = GET)
    public ResponseEntity<String> workerHeathCheck() {
        LOGGER.debug("workerHeathCheck method called.");
        final String response = "Worker Unit V 1.0 - Workers are OK";
        return ResponseEntity
                .ok()
                .body(response);
    }

    /**
     * This method handles the request for a specific number of workers.
     *
     * @param workers the numbers of workers requested.
     * @param workLoadType work load type.
     * @return the Work report response.
     */
    @RequestMapping(value = "/workRequest", method = GET)
    public ResponseEntity<WorkReport> requestWorkers(
            @RequestHeader(value = "workers") @NotNull final int workers,
            @RequestHeader(value = "workLoadType") @NotNull final String workLoadType) {
        LOGGER.debug("requestWorkers method called with workers={}, workLoadType={}.", workers, workLoadType);
        return ResponseEntity
                .ok()
                .body(workerManager.requestWorkers(workers, workLoadType));
    }
}
