import hashlib #criar hashes para cada bloco
import json # codificar e decodificar blocos quando carrega-los ou armazena-los em um file
from time import time
from urllib.parse import urlparse


import requests #timestamp

class Blockchain:
    def __init__(self):
        self.chain = []
        self.current_transactions = []  # Inicializa a lista de transações como vazia
        self.nodes = set()
        # Cria o bloco gênesis
        self.new_block(proof=100, previous_hash='1')

    def new_block(self, proof, previous_hash=None):
        # cria um novo bloco na blockchain
        block = {
            'index': len(self.chain) + 1,
            #'timestamp': time(),
            'transactions': self.current_transactions,
            'proof': proof,
            'previous_hash': previous_hash or self.hash(self.chain[-1])
        }
        
        #resetar lista atual de transacoes
        self.current_transactions = []
        
        #adicionar novo bloco na chain
        self.chain.append(block)
        
        return block
    
    def new_transaction(self, sender, recipient, amount):
        # Adds a new transaction to the list of transactions to be included in the next block.

        self.current_transactions.append({
            'sender': sender,
            'recipient':recipient,
            'amount': amount
        })
        
        # Verifica se o número de transações é igual a 3
        if len(self.current_transactions) == 3:
            self.auto_mine()

        return self.last_block['index'] + 1
    
    def auto_mine(self):
        last_block = self.last_block
        proof = self.proof_of_work(last_block)
        previous_hash = self.hash(last_block)
        block = self.new_block(proof, previous_hash)


    @staticmethod
    def hash(block):
        # Cria um hash SHA-256 de um bloco
        block_string = json.dumps(block, sort_keys=True).encode()
        return hashlib.sha256(block_string).hexdigest()
    
    @property
    def last_block(self):
        # retorna o ultimo bloco da chain
        return self.chain[-1]

    #o metodo proof_of_work toma a última prova como entrada e tenta encontrar 
    # uma nova prova incrementando um contador até que uma prova 
    # válida seja encontrada (uma prova que tenha quatro zeros à esquerda em seu hash).
    def proof_of_work(self, last_block):
        # alogoritmo pow que sera usado para criar novos blocos
        last_proof = last_block['proof']
        last_hash = self.hash(last_block)  # Hash do bloco anterior

        proof = 0
        while self.valid_proof(last_proof, proof, last_hash) is False:
            proof += 1
        
        return proof
    
    @staticmethod
    def valid_proof(last_proof, proof, last_hash):
        #valida a prova

        #last_proof: <int> Previous Proof
        #proof: <int> curent proof
        #last_hash: <str> hash of prevoius block
        #return: <bool> true if correct,false if not
        
        guess = f'{last_proof}{proof}{last_hash}'.encode()
        guess_hash = hashlib.sha256(guess).hexdigest()
        return guess_hash[:4] == "0000"  

    def valid_chain(self, chain):
        last_block = chain[0]
        current_index = 1
        while current_index < len(chain):
            block = chain[current_index]
            print(f"Validando bloco {block['index']}:")
            print(f"- Hash anterior esperado: {block['previous_hash']}")
            print(f"- Hash anterior calculado: {self.hash(last_block)}")

            # Verificar o hash do bloco anterior
            if block['previous_hash'] != self.hash(last_block):
                print(f"Bloco {block['index']} inválido: hash anterior incorreto.")
                return False

            # Verificar se a prova de trabalho é válida
            if not self.valid_proof(last_block['proof'], block['proof'], self.hash(last_block)):
                print(f"Bloco {block['index']} inválido: prova de trabalho incorreta.")
                return False

            last_block = block
            current_index += 1

        print("Cadeia válida.")
        return True

    
    def resolve_conflicts(self):

        # implementada a logica do consenso 50% + 1

        neighbours = self.nodes
        new_chain = None
        chain_votes = {}

        for node in neighbours:
            try:
                url = f'http://{node}/chain'  # Corrige para garantir a URL correta
                print(f"Consultando o nó: {url}")
                response = requests.get(url)

                if response.status_code == 200:
                    chain = response.json().get('chain')

                    if self.valid_chain(chain):
                        chain_hash = self.hash_chain(chain)
                        if chain_hash not in chain_votes:
                            chain_votes[chain_hash] = {'votes': 0, 'chain': chain}
                        chain_votes[chain_hash]['votes'] += 1
            
            except requests.exceptions.RequestException as e:
                print(f"Erro ao conectar ao nó {node}: {e}")
        
        #determinar cadeia com mais votos
        max_votes = 0
        winning_chain = None
        for chain_hash, data in chain_votes.items():
            if data['votes'] > max_votes:
                max_votes = data['votes']
                winning_chain = data['chain']
        
        #verificar se a cadeia vencedoa tem mais de 50% de votos
        if max_votes > len(neighbours) / 2:
            print("Substituindo nossa cadeia pela nova.")
            self.chain = winning_chain
            return True

        print("Mantendo nossa cadeia atual.")
        return False
        
    def hash_chain(self, chain):
        # Cria um hash SHA-256 de uma cadeia
        chain_string = json.dumps(chain, sort_keys=True).encode()
        return hashlib.sha256(chain_string).hexdigest()


    def register_node(self, address):
        """
        Adiciona um novo nó à lista de nós.
        :param address: Endereço do nó. Exemplo: 'http://192.168.0.1:5000'
        """

        parsed_url = urlparse(address)
        if parsed_url.netloc:  # Verifica se o endereço tem um host e porta válidos
            self.nodes.add(parsed_url.netloc)
        elif parsed_url.path:  # Caso o endereço seja um caminho válido
            self.nodes.add(parsed_url.path)
        else:
            raise ValueError('Endereço inválido')
    


    """
    deprecated
    def resolve_conflicts(self):
        #resolve conflitos substituindo a cadeia pela mais longa da rede.

        neighbours = self.nodes
        new_chain = None

        for node in neighbours:
            try:
                url = f'http://localhost:{node}/chain'  # Corrige para garantir a URL correta
                print(f"Consultando o nó: {url}")
                response = requests.get(url)

                if response.status_code == 200:
                    print(f"Resposta recebida do nó {node}: {response.json()}")
                    length = response.json().get('length')
                    chain = response.json().get('chain')

                    if length > max_length and self.valid_chain(chain):
                        print(f"Nova cadeia válida encontrada no nó {node}, com comprimento {length}")
                        max_length = length
                        new_chain = chain

            except requests.exceptions.RequestException as e:
                print(f"Erro ao conectar ao nó {node}: {e}")

        if new_chain:
            print("Substituindo nossa cadeia pela nova.")
            self.chain = new_chain
            return True

        print("Mantendo nossa cadeia atual.")
        return False
        """