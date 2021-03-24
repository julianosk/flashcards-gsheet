import { Box, Divider, makeStyles } from "@material-ui/core";
import React from "react";

const useStyles = makeStyles((theme) => ({
  content: {
    textAlign: 'center',
    padding: theme.spacing(3),
  },
}));

export function Flashcard(props: any) {
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
