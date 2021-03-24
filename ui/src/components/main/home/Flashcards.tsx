import { Button, Container, CssBaseline, makeStyles, Paper } from "@material-ui/core";
import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../redux";
import { loadFlashcards } from "../../../redux/modules/flashcards";
import { Flashcard } from "./Flashcard";

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
  buttons: {
    display: 'flex',
    justifyContent: 'flex-end',
  },
  button: {
    marginTop: theme.spacing(3),
    marginLeft: theme.spacing(1),
  },
}));

export function Flashcards() {
  const classes = useStyles();
  const dispatch = useDispatch();

  const [currentCard, setCurrentCard] = React.useState(0);

  const { list } = useSelector((state: RootState) => state.flashcards)

  useEffect(() => {
    dispatch(loadFlashcards());
  }, [dispatch]);

  const handleNext = () => {
    setCurrentCard(currentCard + 1);
  };

  const handlePrevious = () => {
    setCurrentCard(currentCard - 1);
  };

  const getCurrentFlashcard = () => {
    return list[currentCard];
  }

  return (
    <React.Fragment>
      <CssBaseline/>
      <Container component="main" className={classes.main} maxWidth="sm">
        <Paper className={classes.paper} variant="outlined">
          <Flashcard flashcard={getCurrentFlashcard()}/>
          <div className={classes.buttons}>
            {currentCard !== 0 && (
              <Button onClick={handlePrevious} className={classes.button}>
                Back
              </Button>
            )}
            <Button
              variant="contained"
              onClick={handleNext}
              className={classes.button}
            >
              {currentCard === list.length - 1 ? 'Finish' : 'Next'}
            </Button>
          </div>
        </Paper>
      </Container>
    </React.Fragment>
  );
}
