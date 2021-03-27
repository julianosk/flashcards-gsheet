import { typedAction } from "../helpers";
import { AnyAction, Dispatch } from 'redux';
import { IFlashcard, IFlashcardData } from "../../types";
import axios from "axios";

export const ACTION_TYPES = {
  SET_FLASHCARDS: "flashcards/SET",
  REVIEW_FLASHCARD: "flashcards/REVIEW",
}

const initialState = {
  list: [] as IFlashcard[],
  loading: false,
  current: 0,
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
        list: action.payload
      };
    case ACTION_TYPES.REVIEW_FLASHCARD:
      return {
        ...state,
        list: state.list.map(flashcard => {
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

// Actions
const apiUrl = 'api/flashcards';

const setFlashcards = (flashcards: IFlashcard[]) => {
  return typedAction(ACTION_TYPES.SET_FLASHCARDS, flashcards);
};

export const reviewFlashcard = (row: number, newLevel: number) => {
  return typedAction(ACTION_TYPES.REVIEW_FLASHCARD, { row, newLevel });
};

export function loadFlashcards() {
  return async (dispatch: Dispatch) => {
    try {
      const response = await axios.get(`${apiUrl}`);
      dispatch(setFlashcards(
        response.data.map((data: IFlashcardData) => {
            return <IFlashcard>{
              data: data,
              reviewed: false,
              visibility: {
                example: false,
                translation: false,
              }
            }
          }
        )));
    } catch (err) {
      console.error(err);
    }
  };
}
