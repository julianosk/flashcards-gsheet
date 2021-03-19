export interface IUserState {
  username: string | null;
}

export interface IFlashcard {
  row: number;
  word: string;
  translation: string;
  example: string;
  level: number;
  reviewed: boolean;
  last_seen: Date;
  visibility: IFlashcardVisibility;
}

export interface IFlashcardVisibility {
  example: boolean;
  translation: boolean;
}

export interface IFlashcardsState {
  flashcards: IFlashcard[];
  loading: boolean;
}
