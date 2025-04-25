import logo from './logo.svg';
import './App.css';
import HomePage from './component/home/HomePage.jsx'
import Footer from './component/common/Footer.jsx';
import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './component/common/Navbar.jsx';

function App() {
  return (
    <BrowserRouter>
    <div className="App">
      <Navbar/>
      <div className='content'>
        <Routes>
        <Route exact path='/home' element={<HomePage></HomePage>}/>
        </Routes>
      </div>
      <Footer/>
      </div>
    </BrowserRouter>
  );
}

export default App;
