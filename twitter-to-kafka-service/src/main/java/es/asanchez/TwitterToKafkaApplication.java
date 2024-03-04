package es.asanchez;

import es.asanchez.init.api.IStreamInitializer;
import es.asanchez.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class TwitterToKafkaApplication implements CommandLineRunner {

    private final StreamRunner streamRunner;
    private final IStreamInitializer streamInitializer;
    private final Logger log = LoggerFactory.getLogger(TwitterToKafkaApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
       log.info("TwitterToKafkaApplication is UP");
       streamInitializer.init();
       streamRunner.start();
    }
}
