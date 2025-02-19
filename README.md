# Final Report on Distributed Systems Project
## 1. Introduction
This report aims to describe the development and operation of a middleware for mediating communication between sensors, actuators, and controllers using MQTT and Cassandra.
The system was developed for a home automation environment, where sensors monitor machine conditions and actuators execute automatic commands.

## 2. System Architecture
The solution was designed to efficiently and securely mediate communication between different system components. The middleware utilizes an MQTT broker for message transmission and Cassandra for storing events and commands.

### 2.1 Mediation with Sensors, Actuators, and Controllers
Sensors: Publish events to MQTT topics;
Actuators: Subscribe to MQTT topics to receive commands and execute actions;
Controllers: Manage the information received from sensors, store events in the database, and make decisions based on this data.

### 2.2 Topic Publication by Sensors
Sensors capture environmental events and publish them to the MQTT broker. The middleware processes and forwards these messages to the responsible components, ensuring a continuous data flow.

### 2.3 Topic Subscription by Actuators
Actuators subscribe to MQTT topics to receive commands sent by controllers. This enables process automation based on the conditions recorded by the sensors.

### 2.4 Process Management by Controllers
Controllers receive data from sensors and process the information to make appropriate decisions, activating specific actuators for each situation.

### 2.5 System Monitoring and Control by Clients
Events and commands performed by sensors and actuators are stored in a shared database, allowing real-time monitoring of system operations.

### 2.6 Middleware as a Communication Intermediary
The middleware acts as an intermediary between clients and system processes. It ensures object-based communication, allowing clients to track events in real-time.

## 3. Availability and Fault Tolerance
### 3.1 Data Replication
Cassandra was chosen to ensure high data availability. It distributes replicated information across nodes, preventing data loss in case of a node failure.

### 3.2 Execution Continuity in Case of Controller Failure
The middleware ensures that even if a controller fails, the client continues to receive responses. This is achieved by running multiple instances of a controller simultaneously, allowing one to take over if another fails. This feature also prevents any single controller from becoming overloaded.

## 4. System Security
To prevent incorrect responses due to failure or intrusion, the middleware implements consistency checks that analyze data patterns. If inconsistent values are detected, the data is ignored.

## 5. How to Use
### Prerequisites
Before running the system, ensure you have:
* Docker and Docker Compose installed
* A stable internet connection to pull images
### Setup & Run
1. Clone the Repository:
```
git clone https://github.com/your-repo/security-system.git
cd security-system
```
2.  Start the System
Run the following command to start all services:
```
docker-compose up -d
```
This will initialize:
✅ An MQTT broker (Eclipse Mosquitto)
✅ A Cassandra database cluster (1 seed + 2 nodes)
✅ The backend controller (Spring Boot application)

3. Check Running Services
To verify that all services are up, run
```
docker ps
```
4. Configuration
* MQTT Broker: Available at tcp://localhost:1883
* Cassandra: Connect via cqlsh localhost 9042
* Backend API: Runs on http://localhost:8080 (Port range: 8080-8081)

5. Stopping the System
To stop all containers, run:
```
docker-compose down
```
### Accessing Services
#### Accessing Cassandra
1. Connect to the Cassandra database
```
docker exec -it cassandra cqlsh
```
2. List available keyspaces
```
DESCRIBE KEYSPACES;
```
3. Create a keyspace (if needed)
```
CREATE KEYSPACE security_system WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
```
4. Use the keyspace
```
USE security_system;
```
5. Create a sample table (if needed)
```
CREATE TABLE sensor_events (
  id UUID PRIMARY KEY,
  sensor_id TEXT,
  timestamp TIMESTAMP,
  event_type TEXT,
  value TEXT
);

CREATE TABLE actuator_commands (
  id UUID PRIMARY KEY,
  actuator_id TEXT,
  timestamp TIMESTAMP,
  command_type TEXT,
  parameters TEXT
);
```
6. Retrieve all records
```
SELECT * FROM sensor_events;
SELECT * FROM actuator_commands;
```
#### Using MQTT
1. Subscribe to a topic (listens for messages)
```
mosquitto_sub -h 127.0.0.1 -t "actuator/#"
```
2. Publish a message to a topic (examples)
```
mosquitto_pub -h 127.0.0.1 -t "sensor/sensorFumaca/smoke" -m "detected"
mosquitto_pub -h 127.0.0.1 -t "sensor/sensorMovimento/motion" -m "detected"
```
## 6. Conclusion
The developed middleware meets the requirements of a highly available and secure distributed system, ensuring reliable communication between sensors, actuators, and controllers. By utilizing MQTT and Cassandra, the system is scalable and robust, making it suitable for home automation applications.

