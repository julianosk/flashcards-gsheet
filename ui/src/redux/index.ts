import { combineReducers } from 'redux';
import { userReducer } from './modules/user';
import { flashcardsReducer } from "./modules/flashcards";

export const rootReducer = combineReducers({
    user: userReducer,
    flashcards: flashcardsReducer
});

export type RootState = ReturnType<typeof rootReducer>;
