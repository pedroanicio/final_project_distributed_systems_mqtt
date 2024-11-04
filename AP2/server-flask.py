#!/usr/bin/env python
# encoding: utf-8
import json
from flask import Flask, request, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app)


@app.route('/', methods=['OPTIONS'])
def options_records():
    return jsonify({
        'methods': ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS']
    }), 200

@app.route('/', methods=['GET'])
def query_records():
    name = request.args.get('name')
    matching_records = []
    with open('data.txt', 'r') as f:
        data = f.read()
        records = json.loads(data)
        for record in records:
            if record['name'] == str(name):
                matching_records.append(record)
    if matching_records:
        return jsonify(matching_records)
    else:
        return jsonify({'error': 'Dado não encontrado'}), 404
    
@app.route('/get-all', methods=['GET'])
def query_all_records():
    with open('data.txt', 'r') as f:
        data = f.read()
        records = json.loads(data)
        return jsonify(records)

@app.route('/', methods=['POST'])
def create_record():
    record = json.loads(request.data)
    with open('data.txt', 'r') as f:
        data = f.read()
    if not data:
        records = [record]
    else:
        records = json.loads(data)
        records.append(record)
    with open('data.txt', 'w') as f:
        f.write(json.dumps(records, indent=2))
    return jsonify(record)

@app.route('/', methods=['PUT'])
def update_record():
    record = json.loads(request.data)
    new_records = []
    with open('data.txt', 'r') as f:
        data = f.read()
        records = json.loads(data)
    for r in records:
        if r['name'] == record['name']:
            r['email'] = record['email']
        new_records.append(r)
    with open('data.txt', 'w') as f:
        f.write(json.dumps(new_records, indent=2))
    return jsonify(record)
    
@app.route('/', methods=['DELETE'])
def delete_record():
    record = json.loads(request.data)
    new_records = []
    with open('data.txt', 'r') as f:
        data = f.read()
        records = json.loads(data)
        for r in records:
            if r['name'] == record['name']:
                continue
            new_records.append(r)
    with open('data.txt', 'w') as f:
        f.write(json.dumps(new_records, indent=2))
    return jsonify(record)

if __name__ == '__main__':
    app.run(debug=True)