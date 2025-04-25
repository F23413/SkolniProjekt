import React from "react";
import { NavLink, useNavigate } from "react-router-dom";
import ApiService from "../../service/ApiService";

function Navbar(){
    const isAuthenticated = ApiService.isAuthenticated();
    const isAdmin = ApiService.isAdmin();
    const isUzivatel = ApiService.isUzivatel();
    const navigate = useNavigate();

    const handleLogout =()=>{
        const isLogout = window.confirm("Chcete se odhlásit?");
        if(isLogout){
            ApiService.logout();
            navigate('/home')
        }
    }

    return(
        <nav className="navbar">
            <div className="navbar-brand">
                <NavLink to="/home">Kvadrazič</NavLink>
            </div>
            <ul className="navbar-ul">
                <li><NavLink to="/home" activeClass="active">Domů</NavLink></li>
                <li><NavLink to="/filmy" activeClass="active">Filmy</NavLink></li>
                <li><NavLink to="/pujcene-filmy" activeClass="active">Moje půjčky</NavLink></li>

                { isUzivatel && <li><NavLink to="/profil" activeClass="active">Můj profil</NavLink></li>}
                { isAdmin && <li><NavLink to="/admin" activeClass="active">Admin</NavLink></li>}

                {!isAuthenticated && <li><NavLink to="/login" activeClass="active">Přihlásit se</NavLink></li>}
                {!isAuthenticated && <li><NavLink to="/registrace" activeClass="active">Registrace</NavLink></li>}

                {isAuthenticated && <li onClick={handleLogout}>Odhlásit se</li>}

            </ul>
        </nav>
    )

}

export default Navbar;