import turtle
import time
import paho.mqtt.client as mqtt

broker = "localhost"
port = 1883
topic = "game/movements"
screen_width, screen_height = 680, 360

player_id = input("Digite o nick do jogador: ")

# configuração da tela
wn = turtle.Screen()
wn.title("Move Game by @Garrocho")
wn.bgcolor("green")
wn.setup(width=1.0, height=1.0, startx=None, starty=None)
wn.tracer(0)  # wn.update() será chamado manualmente

# armazenar objeto de jogadores
players = {}

game_running = True  # Sinalizador 

# objeto jogador
def criar_jogador(player_id, color):
    player = turtle.Turtle()
    player.speed(0)
    player.shape("circle")
    player.color(color)
    player.penup()
    player.goto(0, 0)
    player.direction = "stop"
    players[player_id] = player

# criar jogador local
criar_jogador(player_id, "red")

# mover o jogador local e publicar a direção
def definir_direcao(direcao):
    players[player_id].direction = direcao
    publicar_movimento(direcao)

# movimentar jogaor
def mover():
    for pid, player in players.items():
        x, y = player.xcor(), player.ycor()
        if player.direction == "up" and y < screen_height / 2 - 10:
            player.sety(y + 2)
        elif player.direction == "down" and y > -screen_height / 2 + 10:
            player.sety(y - 2)
        elif player.direction == "left" and x > -screen_width / 2 + 10:
            player.setx(x - 2)
        elif player.direction == "right" and x < screen_width / 2 - 10:
            player.setx(x + 2)

# função de callback para mensagens recebidas via MQTT
def assinante(client, userdata, msg):
    try:
        message = msg.payload.decode()
        parts = message.split(":")
        
        if parts[1] == "exit":
            
            excluir_jogador(parts[0])  # parts[0] é o ID do jogador que saiu
            print(f"Jogador {parts[0]} saiu do jogo.")  
            return
        
        sender_id, direction, x, y = parts
        
        if sender_id not in players:
            # cria uma nova "bola" para um jogador que ainda não existe 
            criar_jogador(sender_id, "purple")
        
        # atualiza a direção e posição do jogador específico
        players[sender_id].direction = direction
        players[sender_id].goto(float(x), float(y))  
        
    except Exception as e:
        print("Erro ao processar a mensagem:", e)


# configuração do cliente MQTT
client = mqtt.Client()
client.on_message = assinante
client.connect(broker, port)
client.subscribe(topic)

def publicar_movimento(direction):
    x, y = players[player_id].xcor(), players[player_id].ycor()
    message = f"{player_id}:{direction}:{x}:{y}"
    client.publish(topic, message)

# teclas de controle
wn.listen()
wn.onkeypress(lambda: definir_direcao("up"), "w")
wn.onkeypress(lambda: definir_direcao("down"), "s")
wn.onkeypress(lambda: definir_direcao("left"), "a")
wn.onkeypress(lambda: definir_direcao("right"), "d")
wn.onkeypress(lambda: sair_do_jogo(), "Escape") 

# remover jogador e publicar sua saída
def excluir_jogador(player_id):
    if player_id in players:
        players[player_id].hideturtle()  # esconde o jogador
        del players[player_id]  # remove o jogador do dicionário
        message = f"{player_id}:exit"
        client.publish(topic, message)  # publica a saída do jogador

# sair do jogo
def sair_do_jogo():
    global game_running
    excluir_jogador(player_id)  
    game_running = False
    wn.bye()  

# loop principal 
while game_running:
    mover()  
    wn.update()  # atualiza a tela manualmente
    client.loop(timeout=0.01)  # executa o loop do MQTT na mesma thread
    time.sleep(0.01)  # intervalo de atualização do jogo
