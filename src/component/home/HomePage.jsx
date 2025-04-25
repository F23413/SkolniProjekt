import React, {useState} from "react";

const HomePage =()=>{
    return(
        <div className="home">
            {/* Záhlaví a banner*/}
            <section>
                <header className="header-banner">
                    <img src="./assets/obrazky/YdFsXZgsagfMECEWk7qRTQ.jpg" alt="uvodni obrazek" className="header-obr"/>
                    <div className="overlay"></div>
                    <div className="animace-textu overlay-content">
                        <h1>
                            Vítejte na stránkách serveru <span className="kvadrazic-barva">Kvadrazič</span>
                        </h1><br></br>
                        <h3>Kde půjčování je snadné sample text</h3>
                    </div>
                </header>
            </section>
            {/* Hledání filmů k půjčení */}
            <h4><a className="view-filmy-home" href="/filmy">Všechny filmy</a></h4>
            <h2 className="home-services">Služby na stránkách serveru <span className="kvadrazic-barva">Kvadrazič</span></h2>
        </div>
    )
}