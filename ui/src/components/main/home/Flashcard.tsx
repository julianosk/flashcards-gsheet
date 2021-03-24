import { Box, Divider, makeStyles } from "@material-ui/core";
import React from "react";
import { IFlashcard } from "../../../types";

const useStyles = makeStyles((theme) => ({
  content: {
    textAlign: 'center',
    padding: theme.spacing(3),
  },
}));

interface Props {
  flashcard: IFlashcard;
}

export function Flashcard(props: Props) {
  const classes = useStyles();

  const { flashcard } = props;

  return (
    <React.Fragment>
      {flashcard && (
        <React.Fragment>
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
        </React.Fragment>
      )}
    </React.Fragment>
  );
}
