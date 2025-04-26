import logo from './logo.svg';
import './App.css';
import HomePage from './component/home/HomePage.jsx'
import Footer from './component/common/Footer.jsx';
import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './component/common/Navbar.jsx';
import VsechnyFilmyPage from './component/pujcky_filmu/VsechnyFilmyPage.jsx';

function App() {
  return (
    <BrowserRouter>
    <div className="App">
      <Navbar/>
      <div className='content'>
        <Routes>
        <Route exact path='/home' element={<HomePage/>}/>        
        <Route exact path='/filmy' element={<VsechnyFilmyPage/>}/>

        </Routes>
      </div>
      <Footer/>
      </div>
    </BrowserRouter>
  );
}

export default App;
