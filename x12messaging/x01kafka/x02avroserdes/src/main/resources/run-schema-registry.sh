#!/bin/bash

nohup ~/kafka/bin/zookeeper-server-start.sh ~/kafka/config/zookeeper.properties &
nohup ~/kafka/bin/kafka-server-start.sh ~/kafka/config/server.properties &
~/kafka/bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic persons-avro --replication-factor 1 --partitions 4
#~/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic persons

cd ~/confluent-5.4.1/
bin/schema-registry-start etc/schema-registry/schema-registry.properties