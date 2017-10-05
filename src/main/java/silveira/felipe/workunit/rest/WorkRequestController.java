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
     * @return true if the work request was acknowledged or false if it was rejected.
     */
    @RequestMapping(value = "/workRequest", method = GET)
    public ResponseEntity<Boolean> requestWorkers(
            @RequestHeader(value = "workers") @NotNull final int workers) {
        LOGGER.debug("requestWorkers method called with workers={}.", workers);
        return ResponseEntity
                .ok()
                .body(workerManager.requestWorkers(workers));
    }
}
