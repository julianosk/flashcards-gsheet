import React from 'react';
import { Router, Route } from "react-router-dom";
import './App.css';
import { Login } from "./components/main/auth/Login";
import { createBrowserHistory } from "history";
import { Flashcards } from "./components/main/home/Flashcards";

const history = createBrowserHistory();

function App() {
  return (
    <Router history={history}>
      <div>
        <Route path="/" exact component={Flashcards}/>
        <Route path="/login" component={Login}/>
      </div>
    </Router>
  );
}

export default App;
