import paho.mqtt.client as paho
import time
import random

# Configurações do MQTT
broker = "localhost"
port = 1883
topic = "game/movements"

# Define o ID do jogador (insira um número único para cada jogador)
player_id = input("Digite o ID do jogador: ")

# Função de callback para confirmação de publicação
def on_publish(client, userdata, result):
    print(f"Dispositivo {player_id}: Dados publicados.")
    pass

# Configuração do cliente MQTT
client = paho.Client(f"Player_{player_id}", protocol=paho.MQTTv311)
client.on_publish = on_publish
client.connect(broker, port)

# Publica movimentos aleatórios como simulação
for i in range(20):
    direction = random.choice(["up", "down", "left", "right"])
    message = f"{player_id}:{direction}"
    time.sleep(random.randint(1, 5))

    # Publicando a mensagem
    ret = client.publish(topic, message)
print("Parou...")
