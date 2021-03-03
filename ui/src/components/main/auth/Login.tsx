import { RootState } from "../../../redux";
import { login } from "../../../redux/modules/user"
import React, { useState } from "react";
import { Redirect } from "react-router-dom";
import { Container } from "@material-ui/core";
import { useDispatch, useSelector } from 'react-redux';
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";

// function Login({ login, username }) {
export function Login() {
  const username = useSelector((state: RootState) => state.user.username);
  const dispatch = useDispatch();
  const [inputUsername, setInputUsername] = useState("");

  const tryLogin = (e: React.MouseEvent) => {
    e.preventDefault();
    dispatch(login(inputUsername));
  };

  if (username !== null) {
    return <Redirect to="/" />;
  }

  return (
    <Container component="main" maxWidth="xs">
      <form>
        <TextField
          id="username"
          required
          value={inputUsername}
          onChange={e => setInputUsername(e.target.value)}/>
        <TextField
          id="password"
          required/>
        <Button
          variant="contained"
          type="submit"
          onClick={tryLogin}>
          Login
        </Button>

      </form>
    </Container>
  )
}
//
// const mapStateToProps = (state: RootState) => ({
//   username: state.user.username
// });
//
// const mapDispatchToProps = { login };
// export default connect(mapStateToProps, mapDispatchToProps)(Login);
