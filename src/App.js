import './App.css';
import HomePage from './component/home/HomePage.jsx'
import Footer from './component/common/Footer.jsx';
import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './component/common/Navbar.jsx';
import VsechnyFilmyPage from './component/pujcky_filmu/VsechnyFilmyPage.jsx';
import MojePujckyPage from './component/pujcky_filmu/MojePujckyPage.jsx';
import RegistracePage from './component/auth/RegistracePage.jsx';
import LoginPage from './component/auth/LoginPage.jsx';
import FilmuDetailyPage from './component/pujcky_filmu/FilmuDetailyPage.jsx';
import EditProfilPage from './component/profil/EditProfilPage.jsx';
import ProfilPage from './component/profil/ProfilPage.jsx';
import AdminPage from './component/admin/AdminPage.jsx';
import PridatFilmPage from './component/admin/PridatFilmPage.jsx';
import SpravaFilmuPage from './component/admin/SpravaFilmuPage.jsx';
import SpravaPujcekPage from './component/admin/SpravaPujcekPage.jsx';
import UpravaFilmuPage from './component/admin/UpravaFilmuPage.jsx';
import UpravaPujcekPage from './component/admin/UpravaPujcekPage.jsx';
import { ProtectedRoute, AdminRoute } from './service/guard';

function App() {
  return (
    <BrowserRouter>
    <div className="App">
      <Navbar/>
      <div className='content'>
        <Routes>
        <Route exact path='/home' element={<HomePage/>}/>        
        <Route exact path='/filmy' element={<VsechnyFilmyPage/>}/>
        <Route path='/pujcene-filmy' element={<MojePujckyPage/>}/>
        <Route path='/registrace' element={<RegistracePage/>}/>
        <Route path='/login' element={<LoginPage/>}/>

        {/* protected routes */}
        <Route path="/pujcky_filmu/detaily-pujceni-film/:idFilmu"
              element={<FilmuDetailyPage/>}
            />
            <Route path="/profil"
              element={<ProtectedRoute element={<ProfilPage />} />}
            />
            <Route path="/edit-profil"
              element={<ProtectedRoute element={<EditProfilPage />} />}
            />

            {/* Admin Routes */}
            <Route path="/admin"
              element={<AdminRoute element={<AdminPage />} />}
            />
            <Route path="/admin/sprava-filmu"
              element={<AdminRoute element={<SpravaFilmuPage />} />}
            />
            <Route path="/admin/edit-film/:idFilmu"
              element={<AdminRoute element={<UpravaFilmuPage />} />}
            />
            <Route path="/admin/pridat-film"
              element={<AdminRoute element={<PridatFilmPage />} />}
            />
            <Route path="/admin/sprava-pujcek"
              element={<AdminRoute element={<SpravaPujcekPage />} />}
            />
            <Route path="/admin/edit-pujcka/:kodPujcky"
              element={<AdminRoute element={<UpravaPujcekPage />} />}
            />

             {/* Fallback Route */}
             <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
      </div>
      <Footer/>
      </div>
    </BrowserRouter>
  );
}

export default App;
