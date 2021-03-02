import { typedAction } from "../helpers";
import { Dispatch } from 'redux';
import { RootState } from "../index";

// Actions
const SET_FLASHCARDS = "flashcards/SET"
const REVIEW_FLASHCARD = "flashcards/REVIEW"

const initialState: FlashcardsState = { flashcards: [], loading: false };

const setFlashcards = (flashcards: Flashcard[]) => {
    return typedAction(SET_FLASHCARDS, flashcards);
};

export const reviewFlashcard = (row: number, newLevel: number) => {
    return typedAction(REVIEW_FLASHCARD, { row, newLevel });
};

export function loadFlashcards() {
    return (dispatch: Dispatch, getState: () => RootState) => {
        setTimeout(() => {
            dispatch(
                setFlashcards([...getState().flashcards.flashcards])
            );
        }, 500);
    };
}

type FlashcardsAction = ReturnType<typeof setFlashcards | typeof reviewFlashcard>;

export function flashcardsReducer(
    state = initialState,
    action: FlashcardsAction
): FlashcardsState {
    switch (action.type) {
        case SET_FLASHCARDS:
            return { ...state, flashcards: action.payload };
        case REVIEW_FLASHCARD:
            return {
                ...state, flashcards: state.flashcards.map(flashcard => {
                    if (flashcard.row === action.payload.row) {
                        return { ...flashcard, level: action.payload.newLevel };
                    }
                    return flashcard;
                })
            };
        default:
            return state;
    }
}
