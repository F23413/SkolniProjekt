import React from "react";
import {useLocation, Navigate} from "react-router-dom";
import ApiService from "./ApiService";

// protected route: Zajišťuje, že pouze ověření nebo autorizovaní uživatelé mají přístup k určitým komponentám v rámci aplikace React.
export const ProtectedRoute = ({element: Component})=>{
    const lokace = useLocation();
    return ApiService.isAuthenticated()?(
        Component
    ):(
        <Navigate to="/login" replace state={{from: lokace}}/>
    );
};

export const AdminRoute = ({element: Component})=>{
    const lokace = useLocation();
    return ApiService.isAdmin()?(
        Component // pokud je admin, naviguje na komponent
    ):(
        <Navigate to="/login" replace state={{from: lokace}}/> // pokud není, naviguje nás na login stránku
    );
};