#!/bin/bash

nohup ~/kafka/bin/zookeeper-server-start.sh ~/kafka/config/zookeeper.properties &
nohup ~/kafka/bin/kafka-server-start.sh ~/kafka/config/server.properties &
~/kafka/bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic persons --replication-factor 1 --partitions 4
~/kafka/bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic ages --replication-factor 1 --partitions 4