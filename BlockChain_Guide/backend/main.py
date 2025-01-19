
import os
from uuid import uuid4
import json
from flask import Flask, jsonify, request
from flask_cors import CORS
from blockchain import Blockchain

app = Flask(__name__)
CORS(app)

#gerar um endereço unico e global para esse nó
node_identifier = str(uuid4()).replace('-', '')

#instanciar a blockchain
blockchain = Blockchain()


# /mine: Extraia um novo bloco e adicione-o ao blockchain.
#@app.route('/mine', methods=['GET'])
#def mine():
    # Obtenha o último bloco
#    last_block = blockchain.last_block
    
#    proof = blockchain.proof_of_work(last_block)

     # Crie uma nova transação para recompensar o minerador 
#    blockchain.new_transaction(
#        sender="0",
#        recipient=node_identifier,
#        amount=1,
#    )
#
#    # Adicione o novo bloco à cadeia
#    previous_hash = blockchain.hash(last_block)
#    block = blockchain.new_block(proof, previous_hash)
#
#    response = {
#        'message': "New Block Forged",
#        'index': block['index'],
#        'transactions': block['transactions'],
#        'proof': block['proof'],
#        'previous_hash': block['previous_hash'],
#    }
#    return jsonify(response), 200


# /transactions/new: rota para criar nova transação e adiciona para a lista de transacoes 
@app.route('/transactions/new', methods=['POST'])
def transaction():
    values = request.get_json()

    #verificar se os campos necessarios estao presentes no post
    required = ['sender', 'recipient', 'amount']
    if not all(k in values for k in required):
        return 'Missing values', 400

    #criar nova transacao
    index = blockchain.new_transaction(values['sender'], values['recipient'], values['amount'])
    response =  {'message': f'Transaction will be added to Block {index}'}

    return jsonify(response), 201


# /chain: retornar toda a blockchain
@app.route('/chain', methods=['GET'])
def chain():
    response = {
        'chain': blockchain.chain,
        'length': len(blockchain.chain)
    }
    return jsonify(response), 200

# registrar novos nós
@app.route('/nodes/register', methods=['POST'])
def register_nodes():
    values = request.get_json()

    nodes = values.get('nodes')
    if nodes is None:
        return "Error: Please supply a valid list of nodes", 400

    for node in nodes:
        blockchain.register_node(node)

    response = {
        'message': 'New nodes have been added',
        'total_nodes': list(blockchain.nodes),
    }
    return jsonify(response), 201

#resolve os problemas de conflitos dos nós
@app.route('/nodes/resolve', methods=['GET'])
def consensus():
    replaced = blockchain.resolve_conflicts()

    if replaced:
        response = {
            'message': 'Our chain was replaced',
            'new_chain': blockchain.chain
        }
    else:
        response = {
            'message': 'Our chain is authoritative',
            'chain': blockchain.chain
        }

    return jsonify(response), 200

if __name__ == '__main__':

    #porta = int(os.environ.get('PORT', 5000))

    blockchain.register_node('5001')
    blockchain.register_node('5002')
    blockchain.register_node('5003')
    app.run(host='0.0.0.0', port=5000)
