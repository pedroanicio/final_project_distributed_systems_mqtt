import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Card, CardContent, Typography, Grid } from '@mui/material';

const Blockchain = () => {
    const [blocks, setBlocks] = useState([]);
  
    useEffect(() => {
      axios.get('http://localhost:5000/chain')
        .then(response => setBlocks(response.data.chain))
        .catch(err => console.error(err));
    }, []);
  
    return (
      <div>
        <Typography variant="h4" sx={{marginTop: 2 ,marginBottom: 2 }}>
          Blockchain
        </Typography>
        <Grid container spacing={0}>
          {blocks.map(block => (
            <Grid item xs={12} md={6} key={block.index}>
              <Card sx={{ marginBottom: 3, width: 550, height: 400, boxShadow: 8, borderRadius: 3 }}> 
                <CardContent>
                  <Typography variant="h6">Block #{block.index}</Typography>
                  <Typography variant="body2">Proof: {block.proof}</Typography>
                  <Typography variant="body2">Previous Hash: {block.previous_hash}</Typography>
                  <Typography variant="body2">
                    Transações: 
                  </Typography>
                  <ul>
                    {block.transactions.map((transaction, index) => (
                      <li key={index}>
                        <Typography variant="body2">De: {transaction.sender}</Typography>
                        <Typography variant="body2">Para: {transaction.recipient}</Typography>
                        <Typography variant="body2">Quantia: {transaction.amount}</Typography>
                      </li>
                    ))}
                  </ul>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      </div>
    );
  };
  
  export default Blockchain;