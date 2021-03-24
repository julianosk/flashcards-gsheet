import { Container, CssBaseline, Divider, Paper, makeStyles, Box } from "@material-ui/core";
import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../redux";
import { loadFlashcards } from "../../../redux/modules/flashcards";

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
  const dispatch = useDispatch();

  const flashcard = useSelector((state: RootState) => state.flashcards.list[state.flashcards.current])

  useEffect(() => {
    dispatch(loadFlashcards());
  });

  return (
    <React.Fragment>
      <CssBaseline/>
      <Container component="main" className={classes.main} maxWidth="sm">
        <Paper className={classes.paper} variant="outlined">
          {flashcard && (
            <>
              <Box className={classes.content}>
                {flashcard.data.word}
              </Box>
              <Divider/>
              <Box className={classes.content}>
                {flashcard.data.example}
              </Box>
              <Divider/>
              <Box className={classes.content}>
                {flashcard.data.translation}
              </Box>
            </>
          )}
        </Paper>
      </Container>
    </React.Fragment>
  );
}
