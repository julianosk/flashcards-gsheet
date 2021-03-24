import { typedAction } from "../helpers";
import { Dispatch, AnyAction } from 'redux';
import { RootState } from "../index";
import { IFlashcard } from "../../types";

// Actions
export const ACTION_TYPES = {
  SET_FLASHCARDS: "flashcards/SET",
  REVIEW_FLASHCARD: "flashcards/REVIEW",
}

const initialState = {
  flashcards: [] as IFlashcard[],
  loading: false
};

export type IFlashcardsState = Readonly<typeof initialState>

export function flashcardsReducer(
  state = initialState,
  action: AnyAction
): IFlashcardsState {
  switch (action.type) {
    case ACTION_TYPES.SET_FLASHCARDS:
      return {
        ...state,
        flashcards: action.payload
      };
    case ACTION_TYPES.REVIEW_FLASHCARD:
      return {
        ...state,
        flashcards: state.flashcards.map(flashcard => {
          if (flashcard.data.row === action.payload.data.row) {
            return { ...flashcard, level: action.payload.newLevel, reviewed: true };
          }
          return flashcard;
        })
      };
    default:
      return state;
  }
}

const setFlashcards = (flashcards: IFlashcard[]) => {
  return typedAction(ACTION_TYPES.SET_FLASHCARDS, flashcards);
};

export const reviewFlashcard = (row: number, newLevel: number) => {
  return typedAction(ACTION_TYPES.REVIEW_FLASHCARD, { row, newLevel });
};

export function loadFlashcards() {
  return (dispatch: Dispatch) => {
      dispatch(
        setFlashcards([
          {
            data: {
              row: 1,
              word: "word1",
              example: "Example",
              translation: "Translation",
              level: 2,
              last_seen: new Date(),
            },
            reviewed: false,
            visibility: {
              example: false,
              translation: false,
            },
          },
          {
            data: {
              row: 2,
              word: "word2",
              example: "Example2",
              translation: "Translation2",
              level: 2,
              last_seen: new Date(),
            },
            reviewed: false,
            visibility: {
              example: false,
              translation: false,
            },
          },
        ])
      );
  };
}
