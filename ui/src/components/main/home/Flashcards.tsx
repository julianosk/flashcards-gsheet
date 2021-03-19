import { Container, CssBaseline, Divider, Paper, makeStyles, Box } from "@material-ui/core";
import React from "react";

const useStyles = makeStyles((theme) => ({
  main: {
    marginBottom: theme.spacing(4),
  },
  paper: {
    marginTop: theme.spacing(3),
    marginBottom: theme.spacing(3),
    padding: theme.spacing(2),
    [theme.breakpoints.up('md')]: {
      marginTop: theme.spacing(6),
      marginBottom: theme.spacing(6),
      padding: theme.spacing(3),
    },
  },
  content: {
    textAlign: 'center',
    padding: theme.spacing(3),
  }
}));

export function Flashcards() {
  const classes = useStyles();
  return (
    <React.Fragment>
      <CssBaseline/>
      <Container
        component="main"
        className={classes.main}
        maxWidth="sm"
      >
        <Paper
          className={classes.paper}
          variant="outlined"
        >
          <Box
            className={classes.content}
          >
            Dutch
          </Box>
            <Divider/>
          <Box
            className={classes.content}
          >
            Translation
          </Box>
        </Paper>
      </Container>
    </React.Fragment>
  );
}
