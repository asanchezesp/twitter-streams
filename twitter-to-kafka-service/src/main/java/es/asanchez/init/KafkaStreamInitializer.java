package es.asanchez.init;

import es.asanchez.app.config.KafkaConfigData;
import es.asanchez.init.api.IStreamInitializer;
import es.asanchez.kafka.admin.client.KafkaAdminClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaStreamInitializer implements IStreamInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaStreamInitializer.class);

    private final KafkaConfigData kafkaConfigData;
    private final KafkaAdminClient kafkaAdminClient;
    @Override
    public void init() {
        kafkaAdminClient.createTopics();
        kafkaAdminClient.checkSchemaRegistry();
        LOG.info("Topics with names {} are ready for operations",kafkaConfigData.getTopicNamesToCreate().toArray());
    }
}
