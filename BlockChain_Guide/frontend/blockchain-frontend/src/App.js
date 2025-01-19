import React from 'react';
import { Container } from '@mui/material';
import Navbar from './components/Navbar';
import Blockchain from './components/Blockchain';
import NewTransaction from './components/NewTransaction';

function App() {
  return (
    <>
      <Navbar />
      <Container sx={{ mt: 4 }}>
        <NewTransaction />
        <Blockchain />
      </Container>
    </>
  );
}

export default App;
