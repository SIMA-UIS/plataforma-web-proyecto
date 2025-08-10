package com.proyectogrado.plataforma;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration
@ActiveProfiles("test")
public class EmbedMongoConfig
{
    private MongodExecutable mongodExecutable;

    @Bean
    public MongoClient mongoClient() throws Exception
    {

        // Lee la URI desde variable de entorno, si no existe, usa localhost
        String mongoUri = System.getenv("MONGODB_URI");
        if (mongoUri != null && !mongoUri.isEmpty()) {
            // Si estamos en Docker o variable definida → usar conexión externa
            return MongoClients.create(mongoUri);
        }

        int port = 27017;
        ImmutableMongodConfig mongodConfig = MongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

        mongodExecutable = MongodStarter.getDefaultInstance().prepare(mongodConfig);
        mongodExecutable.start();

        return MongoClients.create("mongodb://localhost:" + port);
    }

    @PreDestroy
    public void shutdown()
    {
        if(mongodExecutable != null)
        {
            mongodExecutable.stop();
        }
    }
}
