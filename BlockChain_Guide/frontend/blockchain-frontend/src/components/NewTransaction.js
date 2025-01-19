import React, { useState } from 'react';
import axios from 'axios';
import { TextField, Button, Box, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';

const NewTransaction = () => {
  const [form, setForm] = useState({ sender: '', recipient: '', amount: '' });
  const [openDialog, setOpenDialog] = useState(false);
  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const ports = [5000, 5001, 5002, 5003];
    const requests = ports.map(port => 
      axios.get(`http://localhost:${port}/nodes/resolve`)
      .then(() => axios.post(`http://localhost:${port}/transactions/new`, form))
    );

    Promise.all(requests)
      .then(responses => {
        setMessage('Transação enviada com sucesso.'); // Mensagem de sucesso
        setOpenDialog(true); // Abre o Dialog
      })
      .catch(err => {
        setMessage('Erro ao enviar transação.'); // Mensagem de erro
        setOpenDialog(true); // Abre o Dialog
        console.error(err);
      });

  };

  const handleCloseDialog = () => {
    setOpenDialog(false); // Fecha o Dialog
  };

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
      <TextField
        label="Sender"
        name="sender"
        value={form.sender}
        onChange={handleChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="Recipient"
        name="recipient"
        value={form.recipient}
        onChange={handleChange}
        fullWidth
        margin="normal"
      />
      <TextField
        label="Amount"
        name="amount"
        type="number"
        value={form.amount}
        onChange={handleChange}
        fullWidth
        margin="normal"
      />
      <Button type="submit" variant="contained" sx={{ mt: 2 }}>
        Enviar Transação
      </Button>

      {/* Message Dialog */}
      <Dialog open={openDialog} onClose={handleCloseDialog}>
        <DialogTitle>Resultado da Transação</DialogTitle>
        <DialogContent>
          <p>{message}</p>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog} color="primary">
            Fechar
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default NewTransaction;
