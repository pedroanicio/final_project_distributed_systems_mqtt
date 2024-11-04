import paho.mqtt.client as mqtt

# Configuração do MQTT
broker = "localhost"
port = 1883
topic = "game/movements"

# Função para tratar as mensagens recebidas
def on_message(client, userdata, msg):
    message = msg.payload.decode()
    player_id, direction = message.split(":")
    print(f"Jogador {player_id} foi para {direction}")

# Configurando o cliente MQTT
client = mqtt.Client("Player_Subscriber")
client.connect(broker, port)
client.subscribe(topic)
client.on_message = on_message

client.loop_forever()
