import React from 'react';
import { Router, Route, Switch } from "react-router-dom";
import './App.css';
import { Login } from "./components/main/auth/Login";
import { createBrowserHistory } from "history";
import { Flashcards } from "./components/main/home/Flashcards";

const history = createBrowserHistory();

function App() {
  return (
    <Router history={history}>
      <Switch>
        <Route path="/" exact>
          Home
        </Route>
        <Route path="/flashcards" component={Flashcards}/>
        <Route path="/login" component={Login}/>
      </Switch>
    </Router>
  );
}

export default App;
