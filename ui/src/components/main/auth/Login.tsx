import { RootState } from "../../../redux";
import { login } from "../../../redux/modules/user"
import React, { useState } from "react";
import { Redirect } from "react-router-dom";
import { Button, Container, Typography, CssBaseline, makeStyles } from "@material-ui/core";
import { useDispatch, useSelector } from 'react-redux';
import TextField from "@material-ui/core/TextField";

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

export function Login() {
  const username = useSelector((state: RootState) => state.user.username);
  const dispatch = useDispatch();
  const [inputUsername, setInputUsername] = useState("");
  const classes = useStyles();

  const tryLogin = (e: React.MouseEvent) => {
    e.preventDefault();
    dispatch(login(inputUsername));
  };

  if (username !== null) {
    return <Redirect to="/"/>;
  }

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline/>
      <div className={classes.paper}>
        <Typography component="h1" variant="h5">
          Flashcards GSheets
        </Typography>
        <form className={classes.form} noValidate>
          <TextField
            id="username"
            variant="outlined"
            margin="normal"
            label="Username"
            required
            fullWidth
            value={inputUsername}
            onChange={e => setInputUsername(e.target.value)}
            autoComplete="email"
          />
          <TextField
            id="password"
            variant="outlined"
            margin="normal"
            label="Password"
            fullWidth
            required
            autoComplete="password"
          />
          <Button
            className={classes.submit}
            fullWidth
            variant="contained"
            type="submit"
            color="primary"
            onClick={tryLogin}
          >
            Login
          </Button>

        </form>
      </div>
    </Container>
  )
}
