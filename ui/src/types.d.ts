export interface IUserState {
  username: string | null;
}

export interface IFlashcardData {
  row: number;
  word: string;
  translation: string;
  example: string;
  level: number;
  last_seen: Date;
}

export interface IFlashcard {
  data: IFlashcardData;
  reviewed: boolean;
  visibility: IFlashcardVisibility;
}

export interface IFlashcardVisibility {
  example: boolean;
  translation: boolean;
}
